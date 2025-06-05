package it.unibo.smartcity.model.impl;

import com.google.common.base.Preconditions;

import it.unibo.smartcity.model.api.Contenuto;

public class ContenutoImpl implements Contenuto {

    private final int codiceHub;
    private final int codiceContenuto;
    private final int postiMax;
    private int postiDisponibili;

    public ContenutoImpl(int codiceHub, int codiceContenuto, int postiMax) {
        Preconditions.checkArgument(postiMax > 0, "I posti devono essere un numero positivo");

        this.codiceHub = codiceHub;
        this.codiceContenuto = codiceContenuto;
        this.postiMax = postiMax;
        this.postiDisponibili = postiMax;
    }

    @Override
    public int getCodiceHub() {
        return codiceHub;
    }

    @Override
    public int getCodiceContenuto() {
        return codiceContenuto;
    }

    @Override
    public int getPostiMax() {
        return postiMax;
    }

    @Override
    public int getPostiDisponibili() {
        return postiDisponibili;
    }

}