package it.unibo.smartcity.model.api;

import it.unibo.smartcity.model.impl.Indirizzo;

public interface Fermata {

    int getCodiceFermata();

    String getNome();

    Indirizzo getIndirizzo();

    String getLongitudine();

    String getLatitudine();

}