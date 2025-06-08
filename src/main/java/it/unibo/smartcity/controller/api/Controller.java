package it.unibo.smartcity.controller.api;

import it.unibo.smartcity.view.api.View;
import it.unibo.smartcity.view.api.View.SignupData;

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

    void signup(SignupData data);

    void updateTimetableLinesList();

    void showTimetable(String linea);

    void updateHubsList();

    void login(String username, String password);

    void logout();

}