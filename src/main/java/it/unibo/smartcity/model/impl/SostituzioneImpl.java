package it.unibo.smartcity.model.impl;
import java.util.Date;

import it.unibo.smartcity.model.api.Sostituzione;

public class SostituzioneImpl implements Sostituzione {

    private final Date sostManutDataInizio;
    private final String sostManutCodiceLinea;
    private final String codiceLinea;
    
    public SostituzioneImpl(Date sostManutDataInizio, String sostManutCodiceLinea, String codiceLinea) {
        this.sostManutDataInizio = sostManutDataInizio;
        this.sostManutCodiceLinea = sostManutCodiceLinea;
        this.codiceLinea = codiceLinea;
    }
    @Override
    public Date getSostManutDataInizio() {
        return sostManutDataInizio;
    }
    @Override
    public String getSostManutCodiceLinea() {
        return sostManutCodiceLinea;
    }
    @Override
    public String getCodiceLinea() {
        return codiceLinea;
    }


}