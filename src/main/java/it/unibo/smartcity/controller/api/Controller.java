package it.unibo.smartcity.controller.api;

import java.sql.Date;
import java.util.List;

import it.unibo.smartcity.model.api.Dipendente;
import it.unibo.smartcity.model.api.Dipendente.Ruolo;
import it.unibo.smartcity.model.api.Fermata;
import it.unibo.smartcity.model.api.HubMobilita;
import it.unibo.smartcity.model.api.Linea;
import it.unibo.smartcity.model.api.ManutenzioneLinea;
import it.unibo.smartcity.model.api.Tratta;
import it.unibo.smartcity.model.api.Utente;
import it.unibo.smartcity.model.impl.HubMobilitaImpl;
import it.unibo.smartcity.model.impl.ManutenzioneLineaImpl;
import it.unibo.smartcity.model.impl.ManutenzioneMezzoImpl;
import it.unibo.smartcity.model.impl.TrattaImpl;
import it.unibo.smartcity.model.impl.MezzoImpl.MezzoConNome;
import it.unibo.smartcity.view.api.View;

public interface Controller {

    public enum UserLevel {
        NOT_LOGGED("Non autenticato"),
        USER("Utente base"),
        ADMIN("Amministrativo"),
        DRIVER("Autista"),
        CONTROLLER("Controllore");

        private final String toString;

        private UserLevel(final String nome) {
            this.toString = nome;
        }

        @Override
        public String toString() {
            return this.toString;
        }
    }

    void attachView(View v);

    void showMainMenu();

    void updateLinesList();

    void updateOrariLavoro();

    void signup(Utente user, String ruolo);

    void updateTimetableLinesList();

    void showTimetable(String linea);

    void updateHubsList();

    void login(String username, String password);

    void logout();

    void showLoginUser(String username);

    void showMessage(String title, String message);

    void updateUserInfo();

    void updateEmployeesList();

    void addEmployee(Utente utente, Ruolo ruolo);

    void removeEmployee(Dipendente dipendente);

    void addFermata(Fermata fermata);

    void removeFermata(Fermata f);

    void updateFermateList();

    void removeHub(HubMobilita h);

    void addHub(HubMobilitaImpl hub);

    void addTratta(TrattaImpl tratta);

    void removeTratta(Tratta t);

    void updateTratte();

    void updateManutGravose();

    void addManutenzioneMezzo(ManutenzioneMezzoImpl manut);

    List<MezzoConNome> getMezzi();

    List<ManutenzioneMezzoImpl> getManutenzioniMezzi();

    void removeManutMezzo(String getnImmatricolazione, Date dataInzio);

    void updateManutMezziPanel();

    void updateManutLineePanel();

    List<ManutenzioneLinea> getManutLinee();

    void addManutenzioneLinea(ManutenzioneLineaImpl manut);

    void removeManutLinea(String codiceLinea, Date dataInizio);

    void updateAziendeNoManut();

    void updateManutPerMezzo();

    List<ManutenzioneMezzoImpl> getManutPerMezzo(String nImmatricolazione);

	void addServiceVariation(ManutenzioneLinea selectedManutenzione, Linea selectedLinea);

    void updateListsManagementLinee();

    void updateTratteListPerLinea(String codiceLinea);

    void addTrattaLinea(String codLinea, Tratta tratta);
}