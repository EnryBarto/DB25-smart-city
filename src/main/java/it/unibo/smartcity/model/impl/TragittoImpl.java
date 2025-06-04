package it.unibo.smartcity.model.impl;

import it.unibo.smartcity.model.api.Tragitto;

public class TragittoImpl implements Tragitto {

    private final int partenzaCodiceFermata;
    private final int arrivoCodiceFermata;
    private final String codiceLinea;
    private final int ordine;

    public TragittoImpl(int partenzaCodiceFermata, int arrivoCodiceFermata, String codiceLinea, int ordine) {
        this.partenzaCodiceFermata = partenzaCodiceFermata;
        this.arrivoCodiceFermata = arrivoCodiceFermata;
        this.codiceLinea = codiceLinea;
        this.ordine = ordine;
    }

    @Override
    public int getPartenzaCodiceFermata() {
        return partenzaCodiceFermata;
    }

    @Override
    public int getArrivoCodiceFermata() {
        return arrivoCodiceFermata;
    }

    @Override
    public String getCodiceLinea() {
        return codiceLinea;
    }

    @Override
    public int getOrdine() {
        return ordine;
    }
}