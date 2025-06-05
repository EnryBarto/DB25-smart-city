package it.unibo.smartcity.model.impl;

import com.google.common.base.Preconditions;

import it.unibo.smartcity.model.api.Azienda;

public class AziendaImpl implements Azienda {

    private final String partitaIva;
    private String ragioneSociale;
    private String indirizzoCivico;
    private String indirizzoVia;
    private String indirizzoComune;
    private int indirizzoCap;
    private String telefono;
    private String email;

    public AziendaImpl(final String partitaIva, final String ragioneSociale, final String indirizzoCivico, final String indirizzoVia,
            final String indirizzoComune, final int indirizzoCap, final String telefono, final String email) {
        Preconditions.checkNotNull(partitaIva);
        Preconditions.checkArgument(!partitaIva.isBlank(), "Partita Iva necessaria");
        Preconditions.checkArgument(partitaIva.length() == 11, "La p.iva deve essere lunga 11 cifre");

        this.partitaIva = partitaIva;
        this.ragioneSociale = ragioneSociale;
        this.indirizzoCivico = indirizzoCivico;
        this.indirizzoVia = indirizzoVia;
        this.indirizzoComune = indirizzoComune;
        this.indirizzoCap = indirizzoCap;
        this.telefono = telefono;
        this.email = email;
    }

    @Override
    public String getPartitaIva() {
        return partitaIva;
    }

    @Override
    public String getRagioneSociale() {
        return ragioneSociale;
    }

    @Override
    public String getIndirizzoCivico() {
        return indirizzoCivico;
    }

    @Override
    public String getIndirizzoVia() {
        return indirizzoVia;
    }

    @Override
    public String getIndirizzoComune() {
        return indirizzoComune;
    }

    @Override
    public int getIndirizzoCap() {
        return indirizzoCap;
    }

    @Override
    public String getTelefono() {
        return telefono;
    }

    @Override
    public String getEmail() {
        return email;
    }

}