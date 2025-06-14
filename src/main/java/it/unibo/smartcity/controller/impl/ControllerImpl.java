package it.unibo.smartcity.controller.impl;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.mindrot.jbcrypt.BCrypt;

import com.google.common.base.Preconditions;

import it.unibo.smartcity.controller.api.Controller;
import it.unibo.smartcity.data.DAOException;
import it.unibo.smartcity.data.DAOUtils;
import it.unibo.smartcity.data.InfoLinea;
import it.unibo.smartcity.data.ListHubMobilita;
import it.unibo.smartcity.data.ListVariazioniServizi;
import it.unibo.smartcity.model.api.Dipendente;
import it.unibo.smartcity.model.api.Dipendente.Ruolo;
import it.unibo.smartcity.model.api.Fermata;
import it.unibo.smartcity.model.api.HubMobilita;
import it.unibo.smartcity.model.api.Linea;
import it.unibo.smartcity.model.api.ManutenzioneLinea;
import it.unibo.smartcity.model.api.Tragitto;
import it.unibo.smartcity.model.api.Tratta;
import it.unibo.smartcity.model.api.Utente;
import it.unibo.smartcity.model.impl.AziendaImpl;
import it.unibo.smartcity.model.impl.DipendenteImpl;
import it.unibo.smartcity.model.impl.FermataImpl;
import it.unibo.smartcity.model.impl.HubMobilitaImpl;
import it.unibo.smartcity.model.impl.LineaImpl;
import it.unibo.smartcity.model.impl.ManutenzioneLineaImpl;
import it.unibo.smartcity.model.impl.ManutenzioneMezzoImpl;
import it.unibo.smartcity.model.impl.MezzoImpl;
import it.unibo.smartcity.model.impl.TragittoImpl;
import it.unibo.smartcity.model.impl.MezzoImpl.MezzoConNome;
import it.unibo.smartcity.model.impl.TrattaImpl;
import it.unibo.smartcity.model.impl.UtenteImpl;
import it.unibo.smartcity.view.api.View;

public class ControllerImpl implements Controller {

    private final static UserLevel DEFAULT_LEVEL = UserLevel.NOT_LOGGED;
    private final Set<View> views = new HashSet<>();
    private Connection connection;
    private UserLevel currentUserLevel = UserLevel.NOT_LOGGED;
    private Utente user;

    public ControllerImpl () {
        var fake = new JFrame();
        fake.setVisible(true);
        // Crea i campi di input
        javax.swing.JTextField dbField = new javax.swing.JTextField(20);
        javax.swing.JTextField userField = new javax.swing.JTextField(20);
        javax.swing.JPasswordField passField = new javax.swing.JPasswordField(20);
        dbField.setText("smart_city");
        userField.setText("root");
        Object[] message = {
            "Database:", dbField,
            "Utente:", userField,
            "Password:", passField
        };

        int option = javax.swing.JOptionPane.showConfirmDialog(
            fake, message, "Parametri connessione DB", javax.swing.JOptionPane.OK_CANCEL_OPTION);

        String dbName, user, pass;
        if (option == javax.swing.JOptionPane.OK_OPTION) {
            dbName = dbField.getText();
            user = userField.getText();
            pass = new String(passField.getPassword());
        } else {
            // Default o chiudi applicazione
            dbName = "";
            user = "";
            pass = "";
            System.exit(0);
        }
        try {
            this.connection = DAOUtils.localMySQLConnection(dbName, user, pass);
        } catch (DAOException e) {
            JOptionPane.showMessageDialog(fake, "Connessione non riuscita!\n" + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        } finally {
            fake.setVisible(false);
        }
    }

    @Override
    public void attachView(final View v) {
        Preconditions.checkNotNull(v);
        v.userLevelChanged(DEFAULT_LEVEL);
        views.add(v);
    }

    @Override
    public void showMainMenu() {
        views.forEach(View::showMainMenu);
    }

    @Override
    public void updateLinesList() {
        views.forEach(v -> v.updateLinesList(InfoLinea.DAO.list(connection)));
    }

    @Override
    public void updateOrariLavoro() {
        views.forEach(v -> v.updateOrariLavoro(DipendenteImpl.DAO.listOrari(connection, user.getUsername())));
    }

    @Override
    public void signup(final Utente user, final String ruolo) {
        Preconditions.checkNotNull(user);
        Preconditions.checkNotNull(ruolo);
        if (ruolo.equalsIgnoreCase("utente")) {
            UtenteImpl.DAO.insert(connection, user);
        } else {
            DipendenteImpl.DAO.insert(connection, user, Ruolo.valueOf(ruolo.toUpperCase()));
        }

    }

    @Override
    public void updateTimetableLinesList() {
        views.forEach(v -> v.updateTimetableLinesList(LineaImpl.DAO.list(connection)));
    }

    @Override
    public void showTimetable(String linea) {
        views.forEach(v -> v.showLineTimetable(linea));
    }

    @Override
    public void updateHubsList() {
        views.forEach(v -> v.updateHubsList(ListHubMobilita.DAO.get(connection)));
        views.forEach(v -> v.updateHubs(HubMobilitaImpl.DAO.list(connection).stream().toList()));
    }

    @Override
    public void login(String username, String password) {
        var utente = UtenteImpl.DAO.byUser(connection, username);
        checkState(this.currentUserLevel == UserLevel.NOT_LOGGED, "Devi essere disconnesso per registrarti");
        if (utente != null && BCrypt.checkpw(password, utente.getPassword())) {
            var ruolo = DipendenteImpl.DAO.getRuolo(connection, username);
            if (ruolo.isEmpty()) {
                this.currentUserLevel = UserLevel.USER;
            } else {
                this.currentUserLevel = switch (ruolo.get()) {
                    case Dipendente.Ruolo.AMMINISTRATIVO -> UserLevel.ADMIN;
                    case Dipendente.Ruolo.AUTISTA -> UserLevel.DRIVER;
                    case Dipendente.Ruolo.CONTROLLORE -> UserLevel.CONTROLLER;
                };
            }
            this.user = utente;
            views.forEach(v -> v.userLevelChanged(this.currentUserLevel));
        } else {
            this.showMessage("Errore Login", "Username o Password errati");
        }
    }

    @Override
    public void logout() {
        Preconditions.checkState(this.currentUserLevel != UserLevel.NOT_LOGGED, "Non sei loggato");
        this.currentUserLevel = UserLevel.NOT_LOGGED;
        this.user = null;
        views.forEach(v -> v.userLevelChanged(this.currentUserLevel));
    }

    @Override
    public void showLoginUser(String username) {
        views.forEach(v -> v.showLoginUser(username));
    }

    @Override
    public void showMessage(String title, String message) {
        views.forEach(v -> v.showMessage(title, message));
    }

    @Override
    public void updateUserInfo() {
        views.forEach(v -> v.updateUserInfo(this.user, this.currentUserLevel));
    }

    @Override
    public void updateEmployeesList() {
        views.forEach(v -> v.updateEmployeesList(DipendenteImpl.DAO.list(connection), UtenteImpl.DAO.listNotEmployeed(connection)));
    }

    @Override
    public void updateFermateList() {
        views.forEach(v -> v.updateFermateList(FermataImpl.DAO.list(connection).stream().toList()));
    }

    @Override
    public void addEmployee(Utente utente, Ruolo ruolo) {
        checkState(this.currentUserLevel == UserLevel.ADMIN);
        DipendenteImpl.DAO.insert(connection, utente, ruolo);
        this.updateEmployeesList();
    }

    @Override
    public void removeEmployee(Dipendente dipendente) {
        checkState(this.currentUserLevel == UserLevel.ADMIN);
        DipendenteImpl.DAO.remove(connection, dipendente);
        this.updateEmployeesList();
    }

    @Override
    public void addFermata(Fermata fermata) {
        checkState(this.currentUserLevel == UserLevel.ADMIN);
        FermataImpl.DAO.insert(connection, fermata);
        this.updateFermateList();
    }

    @Override
    public void removeFermata(Fermata f) {
        checkState(this.currentUserLevel == UserLevel.ADMIN);
        FermataImpl.DAO.delete(connection, f.getCodiceFermata());
        this.updateFermateList();
    }

    @Override
    public void removeHub(HubMobilita h) {
        checkState(this.currentUserLevel == UserLevel.ADMIN);
        HubMobilitaImpl.DAO.delete(connection, h);
        this.updateHubsList();
    }

    @Override
    public void addHub(HubMobilitaImpl hub) {
        checkState(this.currentUserLevel == UserLevel.ADMIN);
        HubMobilitaImpl.DAO.insert(connection, hub);
        this.updateHubsList();
    }

    @Override
    public void addTratta(TrattaImpl tratta) {
        checkState(this.currentUserLevel == UserLevel.ADMIN);
        TrattaImpl.DAO.insert(connection, tratta);
        this.updateFermateList();
        this.updateTratte();
    }

    @Override
    public void removeTratta(Tratta tratta) {
        checkState(this.currentUserLevel == UserLevel.ADMIN);
        TrattaImpl.DAO.delete(connection, tratta);
        this.updateTratte();
    }

    @Override
    public void updateTratte() {
        views.forEach(v -> v.updateTratte(TrattaImpl.DAO.list(connection)));
    }

    @Override
    public void updateManutGravose() {
        views.forEach(v -> v.updateManutGravose(ManutenzioneLineaImpl.DAO.estrazManutPiuGravose(connection)));
    }

    @Override
    public void addManutenzioneMezzo(ManutenzioneMezzoImpl manut) {
        checkState(this.currentUserLevel == UserLevel.ADMIN);
        checkNotNull(manut, "Manutenzione cannot be null");
        ManutenzioneMezzoImpl.DAO.insert(connection, manut);
    }

    @Override
    public ArrayList<MezzoConNome> getMezzi() {
        checkState(this.currentUserLevel == UserLevel.ADMIN || this.currentUserLevel == UserLevel.DRIVER);
        return MezzoImpl.DAO.list(connection);
    }

    @Override
    public List<ManutenzioneMezzoImpl> getManutenzioniMezzi() {
        checkState(this.currentUserLevel == UserLevel.ADMIN);
        return ManutenzioneMezzoImpl.DAO.list(connection);
    }

    @Override
    public void removeManutMezzo(String nImmatricolazione, Date dataInzio) {
        ManutenzioneMezzoImpl.DAO.remove(connection, nImmatricolazione, dataInzio);
    }

    @Override
    public void updateManutMezziPanel() {
        views.forEach(v -> v.updateManutMezziPanel(ManutenzioneMezzoImpl.DAO.list(connection)));
    }

    @Override
    public void updateManutLineePanel() {
        List<ManutenzioneLinea> manutLinee = ManutenzioneLineaImpl.DAO.list(connection);
        views.forEach(v -> v.updateManutLineePanel(manutLinee));
    }

    @Override
    public List<ManutenzioneLinea> getManutLinee() {
        checkState(this.currentUserLevel == UserLevel.ADMIN);
        return ManutenzioneLineaImpl.DAO.list(connection);
    }

    @Override
    public void addManutenzioneLinea(ManutenzioneLineaImpl manut) {
        checkNotNull(manut, "Manutenzione cannot be null");
        checkState(this.currentUserLevel == UserLevel.ADMIN);
        ManutenzioneLineaImpl.DAO.insert(connection, manut);
    }

    @Override
    public void removeManutLinea(String codiceLinea, Date dataInizio) {
        checkNotNull(codiceLinea, "Codice linea cannot be null");
        checkNotNull(dataInizio, "Data inizio cannot be null");
        checkState(this.currentUserLevel == UserLevel.ADMIN);
        ManutenzioneLineaImpl.DAO.remove(connection, codiceLinea, dataInizio);
    }

    @Override
    public void updateAziendeNoManut() {
        views.forEach(v -> v.updateAziendeNoManut(AziendaImpl.DAO.extracAziendeNoManut(connection)));
    }

    @Override
    public void updateManutPerMezzo() {
        views.forEach(v -> v.updateManutPerMezzo(MezzoImpl.DAO.list(connection)));
    }

    @Override
    public List<ManutenzioneMezzoImpl> getManutPerMezzo(String nImmatricolazione) {
        return ManutenzioneMezzoImpl.DAO.listByMezzo(connection, nImmatricolazione);
    }

    @Override
    public void addServiceVariation(ManutenzioneLinea selectedManutenzione, Linea selectedLinea) {
        checkNotNull(selectedManutenzione, "Manutenzione cannot be null");
        checkNotNull(selectedLinea, "Linea cannot be null");
        checkState(this.currentUserLevel == UserLevel.ADMIN);
        ListVariazioniServizi.DAO.insert(selectedManutenzione, selectedLinea, connection);
    }

    @Override
    public void updateListsManagementLinee() {
        views.forEach(v -> v.updateListsManagementLinee(
            LineaImpl.DAO.list(connection),
            TragittoImpl.DAO.listUltimiTragitti(connection)
        ));
    }

    @Override
    public void updateTratteListPerLinea(String codiceLinea) {
        views.forEach(v -> v.updateTratteListPerLinea(TrattaImpl.DAO.listByCodicePartenza(connection, codiceLinea)));
    }

    @Override
    public void addTragitto(String codLinea, Tratta tratta) {
        checkNotNull(tratta);
        checkNotNull(codLinea);
        checkArgument(!codLinea.isBlank());
        TragittoImpl.DAO.insert(connection, codLinea, tratta);
        this.updateListsManagementLinee();
    }

    @Override
    public void removeTragitto(Tragitto tragitto) {
        checkNotNull(tragitto);
        TragittoImpl.DAO.remove(connection, tragitto);
        this.updateListsManagementLinee();
    }


}
