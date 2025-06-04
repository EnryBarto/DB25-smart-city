package it.unibo.smartcity.model.api;

import java.math.BigDecimal;

public interface TariffaAbbonamento {

    String getNome();

    int getNum_giorni();

    BigDecimal getPrezzo();

}