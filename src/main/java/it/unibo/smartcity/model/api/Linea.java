package it.unibo.smartcity.model.api;

import java.util.Date;
import java.util.Optional;

public interface Linea {

    String getCodiceLinea();

    int getTempoPercorrenza();

    Optional<Date> getInizioValidita();

    Optional<Date> getFineValidita();

    boolean getAttiva();

    int getCodiceTipoMezzo();

    void setAttiva(boolean attiva);

    void setFineValidita(Date fineValidita);

}