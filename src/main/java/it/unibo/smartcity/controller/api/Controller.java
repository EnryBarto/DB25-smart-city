package it.unibo.smartcity.controller.api;

import it.unibo.smartcity.model.api.Dipendente;
import it.unibo.smartcity.model.api.Dipendente.Ruolo;
import it.unibo.smartcity.model.api.Utente;
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

    void signup(Utente user);

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
}