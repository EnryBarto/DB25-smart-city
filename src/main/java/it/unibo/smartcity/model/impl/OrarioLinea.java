package it.unibo.smartcity.model.impl;

public class OrarioLinea {

    private final int codiceOrario;
    private final String oraPartenza;
    private final String giornoSettimanale;
    private final String codiceLinea;

    public OrarioLinea(int codiceOrario, String oraPartenza, String giornoSettimanale, String codiceLinea) {
        this.codiceOrario = codiceOrario;
        this.oraPartenza = oraPartenza;
        this.giornoSettimanale = giornoSettimanale;
        this.codiceLinea = codiceLinea;
    }

    public int getCodiceOrario() {
        return codiceOrario;
    }

    public String getOraPartenza() {
        return oraPartenza;
    }

    public String getGiornoSettimanale() {
        return giornoSettimanale;
    }

    public String getCodiceLinea() {
        return codiceLinea;
    }

}