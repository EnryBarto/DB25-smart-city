package it.unibo.smartcity.controller.api;

import java.util.Set;

public enum SmartCityEvent {

    MAIN_MENU,
    SIGNUP;

    public Set<SmartCityEvent> getNextPossibleEvents() {
        return switch (this) {
            case MAIN_MENU -> Set.of(SIGNUP);
            case SIGNUP -> Set.of(MAIN_MENU);
            default -> throw new IllegalStateException("Invalid Event received: " + this);
        };
    }

}
