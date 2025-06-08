package it.unibo.smartcity.model.api;

public interface Dipendente extends Utente {

    public static enum Ruolo {
        AMMINISTRATIVO("Amministrativo"),
        AUTISTA("Autista"),
        CONTROLLORE("Controllore");

        private final String nome;

        private Ruolo(final String nome) {
            this.nome = nome;
        }

        public String toString() {
            return this.nome;
        }
    }

    Ruolo getRuolo();

}