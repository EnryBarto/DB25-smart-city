package it.unibo.smartcity.model.api;

import java.util.Optional;

public interface Persona {

    String getCognome();

    String getNome();

    String getDocumento();

    Optional<String> getCodiceFiscale();

}