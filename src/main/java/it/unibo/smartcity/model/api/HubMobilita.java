package it.unibo.smartcity.model.api;

import java.util.Optional;

import it.unibo.smartcity.model.impl.Indirizzo;

public interface HubMobilita {

    int getCodiceHub();

    String getLongitudine();

    String getLatitudine();

    String getNome();

    Indirizzo getIndirizzo();

    Optional<Integer> getCodiceFermata();

}