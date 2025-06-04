package it.unibo.smartcity.model.api;

import java.util.Date;

public interface ManutenzioneMezzo {

    String getnImmatricolazione();

    Date getDataInzio();

    Date getDataFine();

    String getNome();

    String getDescrizione();

    String getpIva();

}