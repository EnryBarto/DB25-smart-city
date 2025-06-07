package it.unibo.smartcity.controller.impl;

import java.sql.Connection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.google.common.base.Preconditions;

import it.unibo.smartcity.controller.api.Controller;
import it.unibo.smartcity.controller.api.SmartCityEvent;
import it.unibo.smartcity.data.DAOUtils;
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
    public void handleEvent(final SmartCityEvent e, final Optional<?> data) {
        switch (e) {
            case MAIN_MENU -> views.forEach(View::showMainMenu);
            case SHOW_LINES -> {
                var linee = LineaImpl.DAO.list(connection);
                views.forEach(v -> v.showLines(linee));
            }
            case SIGNUP -> {
                Preconditions.checkArgument(data.get() instanceof SignupData, "Data must be of type SignupData");
                final SignupData signupData = (SignupData) data.orElseThrow();
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
            default -> throw new IllegalStateException("Invalid Event received");
        }
    }

}
