package it.unibo.smartcity.model.impl;
import static com.google.common.base.Preconditions.checkArgument;

import it.unibo.smartcity.model.api.ContenutoHub;

public class ContenutoHubImpl implements ContenutoHub {
    private final int codiceContenuto;
    private final int codiceHub;
    private final int postiMax;
    private final String descrizione;
    private int postiDisponibili;

    public ContenutoHubImpl(int codiceContenuto, int codiceHub, int postiMax, int postiDisponibili, String descrizione) {
        this.codiceContenuto = codiceContenuto;
        this.codiceHub = codiceHub;
        this.postiMax = postiMax;
        this.postiDisponibili = postiDisponibili;
        this.descrizione = descrizione;
    }

    @Override
    public int getCodiceContenuto() {
        return codiceContenuto;
    }

    @Override
    public int getCodiceHub() {
        return codiceHub;
    }

    @Override
    public int getPostiMax() {
        return postiMax;
    }

    @Override
    public int getPostiDisponibili() {
        return postiDisponibili;
    }

    @Override
    public String getDescrizione() {
        return descrizione;
    }

    @Override
    public void setPostiDisponibili(int postiDisponibili) {
        checkArgument(postiDisponibili >= 0 && postiDisponibili <= postiMax, "I posti disponibili devono essere tra 0 e il massimo consentito.");
        this.postiDisponibili = postiDisponibili;
    }

    @Override
    public void addPosto() {
        if (this.postiDisponibili < this.postiMax) {
            this.postiDisponibili++;
        }
    }

    @Override
    public void removePosto() {
        if (this.postiDisponibili > 0) {
            this.postiDisponibili--;
        }
    }

}