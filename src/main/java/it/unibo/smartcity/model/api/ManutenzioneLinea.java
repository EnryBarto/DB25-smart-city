package it.unibo.smartcity.model.api;

import java.util.Date;

public interface ManutenzioneLinea {

    String getCodiceLinea();

    Date getDataInizio();

    Date getDataFine();

    String getNome();

    String getDescrizione();

    String getPIva();

}