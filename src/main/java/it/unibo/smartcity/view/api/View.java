package it.unibo.smartcity.view.api;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import it.unibo.smartcity.data.InfoLinea;
import it.unibo.smartcity.data.ListHubMobilita;
import it.unibo.smartcity.model.api.Dipendente;
import it.unibo.smartcity.model.api.Fermata;
import it.unibo.smartcity.model.api.Linea;
import it.unibo.smartcity.model.api.OrarioLinea;
import it.unibo.smartcity.model.api.Utente;
import it.unibo.smartcity.controller.api.Controller.UserLevel;

public interface View {

    void showMainMenu();

    void updateOrariLavoro(Map<Date, OrarioLinea> orariLavoro);

    void updateLinesList(List<InfoLinea> linee);

    void updateTimetableLinesList(List<Linea> list);

    void showLineTimetable(String codLinea);

    void updateHubsList(Set<ListHubMobilita> set);

    void userLevelChanged(UserLevel newLevel);

    void showLoginUser(String username);

    void showError(String title, String message);

    void updateUserInfo(Utente user, UserLevel userLevel);

    void updateEmployeesList(List<Dipendente> employees, List<Utente> notEmployeed);

    void updateFermateList(List<Fermata> fermate);
}