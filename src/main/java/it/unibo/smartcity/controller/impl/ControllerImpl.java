package it.unibo.smartcity.controller.impl;

import java.sql.Connection;
import java.util.HashSet;
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
import it.unibo.smartcity.model.api.Dipendente;
import it.unibo.smartcity.model.api.Utente;
import it.unibo.smartcity.model.impl.DipendenteImpl;
import it.unibo.smartcity.model.impl.LineaImpl;
import it.unibo.smartcity.model.impl.UtenteImpl;
import it.unibo.smartcity.view.api.View;

public class ControllerImpl implements Controller {

    private final static UserLevel DEFAULT_LEVEL = UserLevel.NOT_LOGGED;
    private final Set<View> views = new HashSet<>();
    private Connection connection;
    private UserLevel currentUserLevel = UserLevel.NOT_LOGGED;

    public ControllerImpl () {
        var fake = new JFrame();
        fake.setVisible(true);
        // Crea i campi di input
        javax.swing.JTextField dbField = new javax.swing.JTextField(20);
        javax.swing.JTextField userField = new javax.swing.JTextField(20);
        javax.swing.JPasswordField passField = new javax.swing.JPasswordField(20);

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
    public void signup(final Utente user) {
        Preconditions.checkNotNull(user);
        UtenteImpl.DAO.insert(connection, user);
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
    }

    @Override
    public void login(String username, String password) {
        var utente = UtenteImpl.DAO.byUser(connection, username);
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
            views.forEach(v -> v.userLevelChanged(this.currentUserLevel));
        } else {
            this.showError("Errore Login", "Username o Password errati");
        }
    }

    @Override
    public void logout() {
        Preconditions.checkState(this.currentUserLevel != UserLevel.NOT_LOGGED, "Non sei loggato");
        this.currentUserLevel = UserLevel.NOT_LOGGED;
        views.forEach(v -> v.userLevelChanged(this.currentUserLevel));
    }

    @Override
    public void showLoginUser(String username) {
        views.forEach(v -> v.showLoginUser(username));
    }

    @Override
    public void showError(String title, String message) {
        views.forEach(v -> v.showError(title, message));
    }

}
