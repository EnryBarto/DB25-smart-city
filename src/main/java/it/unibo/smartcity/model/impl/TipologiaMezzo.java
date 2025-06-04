package it.unibo.smartcity.model.impl;

public class TipologiaMezzo {

    private final int codiceTipoMezzo;
    private final String nome;

    public TipologiaMezzo(int codiceTipoMezzo, String nome) {
        this.codiceTipoMezzo = codiceTipoMezzo;
        this.nome = nome;
    }

    public int getCodiceTipoMezzo() {
        return codiceTipoMezzo;
    }

    public String getNome() {
        return nome;
    }

}