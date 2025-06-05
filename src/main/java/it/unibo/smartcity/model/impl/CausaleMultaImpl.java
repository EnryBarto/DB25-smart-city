package it.unibo.smartcity.model.impl;

import com.google.common.base.Preconditions;

import it.unibo.smartcity.model.api.CausaleMulta;

public record CausaleMultaImpl (int codice, String reato, double prezzoBase, double prezzoMassimo) implements CausaleMulta {

    public CausaleMultaImpl {
        Preconditions.checkArgument(prezzoBase > 0, "Il prezzo base deve essere positivo");
        Preconditions.checkArgument(prezzoMassimo > 0, "Il prezzo massimo deve essere positivo");
    }

}