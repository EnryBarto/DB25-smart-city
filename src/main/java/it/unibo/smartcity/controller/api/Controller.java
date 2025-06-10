package it.unibo.smartcity.controller.api;

import java.util.ArrayList;
import java.util.Date;

import it.unibo.smartcity.model.api.Dipendente;
import it.unibo.smartcity.model.api.Dipendente.Ruolo;
import it.unibo.smartcity.model.api.Fermata;
import it.unibo.smartcity.model.api.HubMobilita;
import it.unibo.smartcity.model.api.Tratta;
import it.unibo.smartcity.model.api.Utente;
import it.unibo.smartcity.model.impl.HubMobilitaImpl;
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

    void showError(String title, String message);

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

    void addManutenzione(ManutenzioneMezzoImpl manut);

    ArrayList<MezzoConNome> getMezzi();

    ArrayList<ManutenzioneMezzoImpl> getManutenzioniMezzi();

    void removeManutMezzo(String getnImmatricolazione, Date dataInzio);

}