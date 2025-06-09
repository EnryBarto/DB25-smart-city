package it.unibo.smartcity.model.impl;

import java.util.Optional;
import static com.google.common.base.Preconditions.checkArgument;

import it.unibo.smartcity.model.api.Persona;

public class PersonaImpl implements Persona {

    private final String cognome;
    private final String nome;
    private final String documento;
    private final Optional<String> codiceFiscale;

    public PersonaImpl(final String cognome, final String nome, final String documento, final String codiceFiscale) {
        checkArgument(!cognome.isBlank());
        checkArgument(!nome.isBlank());
        checkArgument(!documento.isBlank());

        this.cognome = cognome;
        this.nome = nome;
        this.documento = documento;
        if (codiceFiscale != null && !codiceFiscale.isBlank()) this.codiceFiscale = Optional.of(codiceFiscale);
        else this.codiceFiscale = Optional.empty();
    }

    public PersonaImpl(final String cognome, final String nome, final String documento) {
        this(cognome,  nome, documento, "");
    }

    @Override
    public String getCognome() {
        return cognome;
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public String getDocumento() {
        return documento;
    }

    @Override
    public Optional<String> getCodiceFiscale() {
        return codiceFiscale;
    }

}