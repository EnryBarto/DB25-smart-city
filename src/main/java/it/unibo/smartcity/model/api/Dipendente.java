package it.unibo.smartcity.model.api;

import it.unibo.smartcity.model.impl.DipendenteImpl.Ruolo;

public interface Dipendente extends Utente {

    Ruolo getRuolo();

}