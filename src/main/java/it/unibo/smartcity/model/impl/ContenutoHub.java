package it.unibo.smartcity.model.impl;

public class ContenutoHub {
    private final int codiceContenuto;
    private final int codiceHub;
    private final int postiMax;
    private final int postiDisponibili;
    private final String descrizione;

    public ContenutoHub(int codiceContenuto, int codiceHub, int postiMax, int postiDisponibili, String descrizione) {
        this.codiceContenuto = codiceContenuto;
        this.codiceHub = codiceHub;
        this.postiMax = postiMax;
        this.postiDisponibili = postiDisponibili;
        this.descrizione = descrizione;
    }

    public int getCodiceContenuto() {
        return codiceContenuto;
    }

    public int getCodiceHub() {
        return codiceHub;
    }

    public int getPostiMax() {
        return postiMax;
    }

    public int getPostiDisponibili() {
        return postiDisponibili;
    }

    public String getDescrizione() {
        return descrizione;
    }

}