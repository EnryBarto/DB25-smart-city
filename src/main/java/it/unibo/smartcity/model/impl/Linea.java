package it.unibo.smartcity.model.impl;

import java.util.Date;

public class Linea {

    private final String codiceLinea;
    private final int tempoPercorrenza;
    private final Date inizioValidita;
    private final Date fineValidita;
    private final String attiva;
    private final int codiceTipoMezzo;

    public Linea(String codiceLinea, int tempoPercorrenza, Date inizioValidita, Date fineValidita, String attiva,
            int codiceTipoMezzo) {
        this.codiceLinea = codiceLinea;
        this.tempoPercorrenza = tempoPercorrenza;
        this.inizioValidita = inizioValidita;
        this.fineValidita = fineValidita;
        this.attiva = attiva;
        this.codiceTipoMezzo = codiceTipoMezzo;
    }

    public String getCodiceLinea() {
        return codiceLinea;
    }

    public int getTempoPercorrenza() {
        return tempoPercorrenza;
    }

    public Date getInizioValidita() {
        return inizioValidita;
    }

    public Date getFineValidita() {
        return fineValidita;
    }

    public String getAttiva() {
        return attiva;
    }

    public int getCodiceTipoMezzo() {
        return codiceTipoMezzo;
    }

}