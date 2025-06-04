package it.unibo.smartcity.model.impl;

public class Fermata {

    private final int codiceFermata;
    private final String nome;
    private final String via;
    private final int cap;
    private final String longitudine;
    private final String latitudine;

    public Fermata(int codiceFermata, String nome, String via, int cap, String longitudine, String latitudine) {
        this.codiceFermata = codiceFermata;
        this.nome = nome;
        this.via = via;
        this.cap = cap;
        this.longitudine = longitudine;
        this.latitudine = latitudine;
    }
    public int getCodiceFermata() {
        return codiceFermata;
    }
    public String getNome() {
        return nome;
    }
    public String getVia() {
        return via;
    }
    public int getCap() {
        return cap;
    }
    public String getLongitudine() {
        return longitudine;
    }
    public String getLatitudine() {
        return latitudine;
    }

}