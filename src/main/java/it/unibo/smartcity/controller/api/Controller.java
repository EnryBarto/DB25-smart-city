package it.unibo.smartcity.controller.api;

import it.unibo.smartcity.view.api.View;
import it.unibo.smartcity.view.api.View.SignupData;

public interface Controller {

    void attachView(View v);

    void showMainMenu();

    void updateLinesList();

    void signup(SignupData data);

    void updateTimetableLinesList();

    void showTimetable(String linea);

    void updateHubsList();

}