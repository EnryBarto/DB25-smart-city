package it.unibo.smartcity.view.api;

import java.util.List;
import java.util.Set;

import it.unibo.smartcity.data.InfoLinea;
import it.unibo.smartcity.data.ListHubMobilita;
import it.unibo.smartcity.model.api.Dipendente;
import it.unibo.smartcity.model.api.Linea;
import it.unibo.smartcity.model.api.Utente;
import it.unibo.smartcity.controller.api.Controller.UserLevel;

public interface View {

    public record  SignupData(
        String name,
        String surname,
        String document,
        String cf,
        String phone,
        String email,
        String username,
        String password
    ) {}

    void showMainMenu();

    void updateLinesList(List<InfoLinea> linee);

    void updateTimetableLinesList(List<Linea> list);

    void showLineTimetable(String codLinea);

    void updateHubsList(Set<ListHubMobilita> set);

    void showLoginError();

    void userLevelChanged(UserLevel newLevel);

    void updateUserInfo(Utente user, UserLevel userLevel);

    void updateEmployeesList(List<Dipendente> employees, List<Utente> notEmployeed);

}