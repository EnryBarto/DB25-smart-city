package it.unibo.smartcity.model.api;

import java.time.LocalTime;

public interface OrarioLinea {

    int getCodiceOrario();

    LocalTime getOraPartenza();

    String getGiornoSettimanale();

    String getCodiceLinea();

}