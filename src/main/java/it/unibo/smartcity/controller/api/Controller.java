package it.unibo.smartcity.controller.api;

import it.unibo.smartcity.model.api.Utente;
import it.unibo.smartcity.view.api.View;

public interface Controller {

    public enum UserLevel {
        NOT_LOGGED,
        USER,
        ADMIN,
        DRIVER,
        CONTROLLER;
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

}