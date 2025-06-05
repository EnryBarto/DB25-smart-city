package it.unibo.smartcity.controller.api;

import java.util.Set;

public enum SmartCityEvent {

    MAIN_MENU;

    public Set<SmartCityEvent> getNextPossibleEvents() {
        return switch (this) {
            case MAIN_MENU -> Set.of();
        };
    }

}
