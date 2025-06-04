package it.unibo.smartcity.model.impl;
import static com.google.common.base.Preconditions.checkArgument;

public class ContenutoHub {
    private final int codiceContenuto;
    private final int codiceHub;
    private final int postiMax;
    private final String descrizione;
    private int postiDisponibili;

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

    public void setPostiDisponibili(int postiDisponibili) {
        checkArgument(postiDisponibili >= 0 && postiDisponibili <= postiMax, "I posti disponibili devono essere tra 0 e il massimo consentito.");
        this.postiDisponibili = postiDisponibili;
    }

    public void addPosto() {
        if (this.postiDisponibili < this.postiMax) {
            this.postiDisponibili++;
        }
    }

    public void removePosto() {
        if (this.postiDisponibili > 0) {
            this.postiDisponibili--;
        }
    }

}