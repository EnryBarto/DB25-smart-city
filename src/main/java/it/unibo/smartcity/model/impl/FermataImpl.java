package it.unibo.smartcity.model.impl;

import it.unibo.smartcity.model.api.Fermata;

public class FermataImpl implements Fermata {

    private final int codiceFermata;
    private final String nome;
    private final String via;
    private final int cap;
    private final String longitudine;
    private final String latitudine;

    public FermataImpl(int codiceFermata, String nome, String via, int cap, String longitudine, String latitudine) {
        this.codiceFermata = codiceFermata;
        this.nome = nome;
        this.via = via;
        this.cap = cap;
        this.longitudine = longitudine;
        this.latitudine = latitudine;
    }
    @Override
    public int getCodiceFermata() {
        return codiceFermata;
    }
    @Override
    public String getNome() {
        return nome;
    }
    @Override
    public String getVia() {
        return via;
    }
    @Override
    public int getCap() {
        return cap;
    }
    @Override
    public String getLongitudine() {
        return longitudine;
    }
    @Override
    public String getLatitudine() {
        return latitudine;
    }

}