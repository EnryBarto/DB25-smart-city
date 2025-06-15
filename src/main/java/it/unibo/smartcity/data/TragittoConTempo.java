package it.unibo.smartcity.data;

import it.unibo.smartcity.model.api.Fermata;
import it.unibo.smartcity.model.api.Tragitto;

public record TragittoConTempo (Tragitto tragitto, int tempoPercorrenza, Fermata partenza, Fermata arrivo) {

}
