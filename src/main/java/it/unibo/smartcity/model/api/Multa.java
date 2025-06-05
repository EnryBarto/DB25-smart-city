package it.unibo.smartcity.model.api;

import java.util.Date;
import java.util.Optional;

public interface Multa {

    void setDataPagamento(Date data);

    int getCodice();

    Date getDataOraEmissione();

    double getImporto();

    Optional<Date> getDataPagamento();

    int getCodiceCausale();

    int getCodiceCorsa();

    String getDocumentoIntestatario();

    String getUsernameControllore();

}