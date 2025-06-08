package it.unibo.smartcity.model.api;

import java.util.Optional;

public interface HubMobilita {

    int getCodiceHub();

    String getLongitudine();

    String getLatitudine();

    String getNome();

    String getIndirizzo();

    Optional<Integer> getCodiceFermata();

}