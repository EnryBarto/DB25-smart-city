package it.unibo.smartcity.view.api;

import java.util.List;
import java.util.Set;

import it.unibo.smartcity.data.InfoLinea;
import it.unibo.smartcity.data.ListHubMobilita;
import it.unibo.smartcity.model.api.Linea;
import it.unibo.smartcity.controller.api.Controller.UserLevel;

public interface View {

    void showMainMenu();

    void updateLinesList(List<InfoLinea> linee);

    void updateTimetableLinesList(List<Linea> list);

    void showLineTimetable(String codLinea);

    void updateHubsList(Set<ListHubMobilita> set);

    void userLevelChanged(UserLevel newLevel);

    void showLoginUser(String username);

    void showError(String title, String message);

}