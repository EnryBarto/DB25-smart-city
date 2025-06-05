package it.unibo.smartcity.model.api;

import java.util.Date;
import java.util.Optional;

public interface Biglietto {

    int getCodice();

    Date getDataAcquisto();

    int getCodDurata();

    Optional<String> getUsername();

}