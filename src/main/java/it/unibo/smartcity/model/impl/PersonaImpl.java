package it.unibo.smartcity.model.impl;

import java.util.Optional;
import com.google.common.base.Preconditions;

import it.unibo.smartcity.model.api.Persona;

public class PersonaImpl implements Persona {

    private final String cognome;
    private final String nome;
    private final String documento;
    private final Optional<String> codiceFiscale;

    public PersonaImpl(final String cognome, final String nome, final String documento, final String codiceFiscale) {
        Preconditions.checkArgument(!cognome.isBlank());
        Preconditions.checkArgument(!nome.isBlank());
        Preconditions.checkArgument(!documento.isBlank());

        this.cognome = cognome;
        this.nome = nome;
        this.documento = documento;
        this.codiceFiscale = codiceFiscale.isBlank() ? Optional.empty() : Optional.of(codiceFiscale);
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