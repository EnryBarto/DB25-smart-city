package it.unibo.smartcity.model.api;

import java.util.Date;
import java.util.Optional;

public interface ManutenzioneLinea {

    String getCodiceLinea();

    Date getDataInizio();

    Date getDataFine();

    String getNome();

    String getDescrizione();

    Optional<String> getPIva();

}