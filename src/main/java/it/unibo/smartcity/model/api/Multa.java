package it.unibo.smartcity.model.api;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Optional;

public interface Multa {

    void setDataPagamento(Date data);

    int getCodice();

    LocalDateTime getDataOraEmissione();

    double getImporto();

    Optional<Date> getDataPagamento();

    int getCodiceCausale();

    int getCodiceCorsa();

    String getDocumentoIntestatario();

    String getUsernameControllore();

}