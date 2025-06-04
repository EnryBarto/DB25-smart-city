package it.unibo.smartcity.model.impl;

public class HubMobilita {

    private final int codiceHub;
    private final String longitudine;
    private final String latitudine;
    private final String nome;
    private final String indirizzo;
    private final int codiceFermata;

    public HubMobilita(int codiceHub, String longitudine, String latitudine, String nome, String indirizzo,
            int codiceFermata) {
        this.codiceHub = codiceHub;
        this.longitudine = longitudine;
        this.latitudine = latitudine;
        this.nome = nome;
        this.indirizzo = indirizzo;
        this.codiceFermata = codiceFermata;
    }

    public int getCodiceHub() {
        return codiceHub;
    }

    public String getLongitudine() {
        return longitudine;
    }

    public String getLatitudine() {
        return latitudine;
    }

    public String getNome() {
        return nome;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public int getCodiceFermata() {
        return codiceFermata;
    }

}