package it.unibo.smartcity.view.api;

import java.util.List;
import java.util.Set;

import javax.swing.JOptionPane;

import it.unibo.smartcity.data.InfoLinea;
import it.unibo.smartcity.data.ListHubMobilita;
import it.unibo.smartcity.model.api.Linea;
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

    static void showErrorDialog(String errorMessage) {
        JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
    }

    void showError(String errorMessage);

    void showMainMenu();

    void updateLinesList(List<InfoLinea> linee);

    void updateTimetableLinesList(List<Linea> list);

    void showLineTimetable(String codLinea);

    void updateHubsList(Set<ListHubMobilita> set);

    void userLevelChanged(UserLevel newLevel);

}