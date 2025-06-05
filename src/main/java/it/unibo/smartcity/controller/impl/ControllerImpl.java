package it.unibo.smartcity.controller.impl;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.google.common.base.Preconditions;

import it.unibo.smartcity.controller.api.Controller;
import it.unibo.smartcity.controller.api.SmartCityEvent;
import it.unibo.smartcity.model.impl.UtenteImpl;
import it.unibo.smartcity.view.api.View;
import it.unibo.smartcity.view.api.View.SignupData;

public class ControllerImpl implements Controller {

    private final Set<View> views = new HashSet<>();
    private Set<SmartCityEvent> nextEvents = Set.of(SmartCityEvent.MAIN_MENU);

    @Override
    public void attachView(final View v) {
        Preconditions.checkNotNull(v);
        views.add(v);
    }

    @Override
    public void handleEvent(final SmartCityEvent e, final Optional<?> data) {
        Preconditions.checkState(this.nextEvents.contains(e), "Invalid event received: " + e.toString());
        switch (e) {
            case MAIN_MENU -> views.forEach(View::showMainMenu);
            case SIGNUP -> {
                Preconditions.checkArgument(data.get() instanceof SignupData, "Data must be of type SignupData");
                final SignupData signupData = (SignupData) data.orElseThrow();
                // call the model to save user data
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
        this.nextEvents = e.getNextPossibleEvents();
    }

}
