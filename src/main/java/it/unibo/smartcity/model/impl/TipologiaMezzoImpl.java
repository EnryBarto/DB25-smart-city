package it.unibo.smartcity.model.impl;

import it.unibo.smartcity.model.api.TipologiaMezzo;

public class TipologiaMezzoImpl implements TipologiaMezzo  {

    private final int codiceTipoMezzo;
    private final String nome;

    public TipologiaMezzoImpl(int codiceTipoMezzo, String nome) {
        this.codiceTipoMezzo = codiceTipoMezzo;
        this.nome = nome;
    }

    @Override
    public int getCodiceTipoMezzo() {
        return codiceTipoMezzo;
    }

    @Override
    public String getNome() {
        return nome;
    }

}