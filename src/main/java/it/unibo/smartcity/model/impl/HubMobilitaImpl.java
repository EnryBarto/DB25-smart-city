package it.unibo.smartcity.model.impl;

import it.unibo.smartcity.model.api.HubMobilita;

public class HubMobilitaImpl implements HubMobilita {

    private final int codiceHub;
    private final String longitudine;
    private final String latitudine;
    private final String nome;
    private final String indirizzo;
    private final int codiceFermata;

    public HubMobilitaImpl(int codiceHub, String longitudine, String latitudine, String nome, String indirizzo,
            int codiceFermata) {
        this.codiceHub = codiceHub;
        this.longitudine = longitudine;
        this.latitudine = latitudine;
        this.nome = nome;
        this.indirizzo = indirizzo;
        this.codiceFermata = codiceFermata;
    }

    @Override
    public int getCodiceHub() {
        return codiceHub;
    }

    @Override
    public String getLongitudine() {
        return longitudine;
    }

    @Override
    public String getLatitudine() {
        return latitudine;
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public String getIndirizzo() {
        return indirizzo;
    }

    @Override
    public int getCodiceFermata() {
        return codiceFermata;
    }

}