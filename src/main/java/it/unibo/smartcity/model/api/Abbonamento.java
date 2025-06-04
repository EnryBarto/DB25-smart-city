package it.unibo.smartcity.model.api;

import java.util.Date;

public interface Abbonamento {

    Date getDataInizio();

    int getCodiceAbbonamento();

    Date getDataAcquisto();

    int getNumGiorni();

    String getUsername();

}