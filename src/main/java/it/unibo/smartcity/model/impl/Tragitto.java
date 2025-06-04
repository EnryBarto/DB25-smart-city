package it.unibo.smartcity.model.impl;

public class Tragitto {

    private final int partenzaCodiceFermata;
    private final int arrivoCodiceFermata;
    private final String codiceLinea;
    private final int ordine;

    public Tragitto(int partenzaCodiceFermata, int arrivoCodiceFermata, String codiceLinea, int ordine) {
        this.partenzaCodiceFermata = partenzaCodiceFermata;
        this.arrivoCodiceFermata = arrivoCodiceFermata;
        this.codiceLinea = codiceLinea;
        this.ordine = ordine;
    }

    public int getPartenzaCodiceFermata() {
        return partenzaCodiceFermata;
    }

    public int getArrivoCodiceFermata() {
        return arrivoCodiceFermata;
    }

    public String getCodiceLinea() {
        return codiceLinea;
    }

    public int getOrdine() {
        return ordine;
    }
}