package it.unibo.smartcity.model.impl;

import it.unibo.smartcity.model.api.Controllo;

public record ControlloImpl (String username, int codiceCorsa) implements Controllo {

}