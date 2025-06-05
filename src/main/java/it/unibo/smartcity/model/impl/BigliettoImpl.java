package it.unibo.smartcity.model.impl;
import java.util.Date;
import java.util.Optional;

import it.unibo.smartcity.model.api.Biglietto;

public class BigliettoImpl implements Biglietto {

    private final int codice;
    private final Date dataAcquisto;
    private final int codDurata;
    private final Optional<String> username;


    public BigliettoImpl(final int codice, final Date dataAcquisto, final int codDurata, final String username) {
        this.codice = codice;
        this.dataAcquisto = dataAcquisto;
        this.codDurata = codDurata;
        this.username = username.isBlank() ? Optional.empty() : Optional.of(username);
    }

    public BigliettoImpl(final int codice, Date dataAcquisto, int durata) {
        this(codice, dataAcquisto, durata, "");
    }

    @Override
    public int getCodice() {
        return codice;
    }

    @Override
    public Date getDataAcquisto() {
        return dataAcquisto;
    }

    @Override
    public int getCodDurata() {
        return codDurata;
    }

    @Override
    public Optional<String> getUsername() {
        return username;
    }


}