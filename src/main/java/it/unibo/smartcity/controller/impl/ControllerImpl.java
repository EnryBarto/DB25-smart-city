package it.unibo.smartcity.controller.impl;

import java.sql.Connection;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.google.common.base.Preconditions;

import it.unibo.smartcity.controller.api.Controller;
import it.unibo.smartcity.data.DAOException;
import it.unibo.smartcity.data.DAOUtils;
import it.unibo.smartcity.data.InfoLinea;
import it.unibo.smartcity.model.impl.LineaImpl;
import it.unibo.smartcity.model.impl.UtenteImpl;
import it.unibo.smartcity.view.api.View;
import it.unibo.smartcity.view.api.View.SignupData;

public class ControllerImpl implements Controller {

    private final Set<View> views = new HashSet<>();
    private Connection connection;

    public ControllerImpl () {
        var fake = new JFrame();
        fake.setVisible(true);
        // Crea i campi di input
        javax.swing.JTextField dbField = new javax.swing.JTextField(20);
        javax.swing.JTextField userField = new javax.swing.JTextField(20);
        javax.swing.JPasswordField passField = new javax.swing.JPasswordField(20);

        Object[] message = {
            "Database:", dbField,
            "Utente:", userField,
            "Password:", passField
        };

        int option = javax.swing.JOptionPane.showConfirmDialog(
            fake, message, "Parametri connessione DB", javax.swing.JOptionPane.OK_CANCEL_OPTION);

        String dbName, user, pass;
        if (option == javax.swing.JOptionPane.OK_OPTION) {
            dbName = dbField.getText();
            user = userField.getText();
            pass = new String(passField.getPassword());
        } else {
            // Default o chiudi applicazione
            dbName = "";
            user = "";
            pass = "";
            System.exit(0);
        }
        try {
            this.connection = DAOUtils.localMySQLConnection(dbName, user, pass);
        } catch (DAOException e) {
            JOptionPane.showMessageDialog(fake, "Connessione non riuscita!\n" + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        } finally {
            fake.setVisible(false);
        }
    }

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
