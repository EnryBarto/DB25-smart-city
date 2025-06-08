package it.unibo.smartcity.controller.impl;

import java.sql.Connection;
import java.util.HashSet;
import java.util.Set;

import com.google.common.base.Preconditions;

import it.unibo.smartcity.controller.api.Controller;
import it.unibo.smartcity.data.DAOUtils;
import it.unibo.smartcity.data.InfoLinea;
import it.unibo.smartcity.model.impl.LineaImpl;
import it.unibo.smartcity.model.impl.UtenteImpl;
import it.unibo.smartcity.view.api.View;
import it.unibo.smartcity.view.api.View.SignupData;

public class ControllerImpl implements Controller {

    private final Set<View> views = new HashSet<>();
    private final Connection connection = DAOUtils.localMySQLConnection(ConnectionInfo.DB_NAME, ConnectionInfo.USER, ConnectionInfo.PASSWORD);

    @Override
    public void attachView(final View v) {
        Preconditions.checkNotNull(v);
        views.add(v);
    }

    @Override
    public void showMainMenu() {
        views.forEach(View::showMainMenu);
    }

    @Override
    public void updateLinesList() {
        views.forEach(v -> v.updateLinesList(InfoLinea.DAO.list(connection)));
    }

    @Override
    public void signup(SignupData signupData) {
        Preconditions.checkNotNull(signupData);
        // call the model to save user data
        //TODO: get the connection to the database
        UtenteImpl.DAO.insert(null, new UtenteImpl(
            signupData.name(),
            signupData.surname(),
            signupData.document(),
            signupData.cf(),
            signupData.username(),
            signupData.email(),
            signupData.phone(),
            signupData.password()
        ));
    }

    @Override
    public void updateTimetableLinesList() {
        views.forEach(v -> v.updateTimetableLinesList(LineaImpl.DAO.list(connection)));
    }

    @Override
    public void showTimetable(String linea) {
        views.forEach(v -> v.showLineTimetable(linea));
    }

}
