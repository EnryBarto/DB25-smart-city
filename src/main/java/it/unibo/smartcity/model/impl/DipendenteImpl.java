package it.unibo.smartcity.model.impl;

import it.unibo.smartcity.model.api.Dipendente;

public class DipendenteImpl extends UtenteImpl implements Dipendente {

    public static enum Ruolo {
        AMMINISTRATIVO("Amministrativo"),
        AUTISTA("Autista"),
        DIPENDENTE("Dipendente");

        private final String nome;

        private Ruolo(final String nome) {
            this.nome = nome;
        }

        public String toString() {
            return this.nome;
        }
    }

    private final Ruolo ruolo;

    public DipendenteImpl(final String cognome, final String nome, final String documento, final String codiceFiscale, final String username,
            final String email, final String telefono, final String password, final Ruolo ruolo) {
        super(cognome, nome, documento, codiceFiscale, username, email, telefono, password);
        this.ruolo = ruolo;
    }

    @Override
    public Ruolo getRuolo() {
        return ruolo;
    }

}