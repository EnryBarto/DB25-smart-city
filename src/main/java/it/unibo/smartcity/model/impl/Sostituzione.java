package it.unibo.smartcity.model.impl;
import java.util.Date;

public class Sostituzione {

    private final Date sostManutDataInizio;
    private final String sostManutCodiceLinea;
    private final String codiceLinea;
    
    public Sostituzione(Date sostManutDataInizio, String sostManutCodiceLinea, String codiceLinea) {
        this.sostManutDataInizio = sostManutDataInizio;
        this.sostManutCodiceLinea = sostManutCodiceLinea;
        this.codiceLinea = codiceLinea;
    }
    public Date getSostManutDataInizio() {
        return sostManutDataInizio;
    }
    public String getSostManutCodiceLinea() {
        return sostManutCodiceLinea;
    }
    public String getCodiceLinea() {
        return codiceLinea;
    }


}