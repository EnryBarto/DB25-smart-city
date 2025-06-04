package it.unibo.smartcity.model.impl;

import it.unibo.smartcity.model.api.OrarioLinea;

public class OrarioLineaImpl implements OrarioLinea {

    private final int codiceOrario;
    private final String oraPartenza;
    private final String giornoSettimanale;
    private final String codiceLinea;

    public OrarioLineaImpl(int codiceOrario, String oraPartenza, String giornoSettimanale, String codiceLinea) {
        this.codiceOrario = codiceOrario;
        this.oraPartenza = oraPartenza;
        this.giornoSettimanale = giornoSettimanale;
        this.codiceLinea = codiceLinea;
    }

    @Override
    public int getCodiceOrario() {
        return codiceOrario;
    }

    @Override
    public String getOraPartenza() {
        return oraPartenza;
    }

    @Override
    public String getGiornoSettimanale() {
        return giornoSettimanale;
    }

    @Override
    public String getCodiceLinea() {
        return codiceLinea;
    }

}