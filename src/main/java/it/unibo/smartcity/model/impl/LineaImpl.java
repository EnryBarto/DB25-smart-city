package it.unibo.smartcity.model.impl;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.Date;
import java.util.Optional;

import it.unibo.smartcity.model.api.Linea;

public class LineaImpl implements Linea {

    private final String codiceLinea;
    private final int tempoPercorrenza;
    private final Optional<Date> inizioValidita;
    private Optional<Date> fineValidita;
    private boolean attiva;
    private final int codiceTipoMezzo;

    public LineaImpl(String codiceLinea, int tempoPercorrenza, Date inizioValidita, Date fineValidita, boolean attiva,
            int codiceTipoMezzo) {
        this.codiceLinea = codiceLinea;
        this.tempoPercorrenza = tempoPercorrenza;
        this.inizioValidita = Optional.of(inizioValidita);
        this.fineValidita = Optional.of(fineValidita);
        this.attiva = attiva;
        this.codiceTipoMezzo = codiceTipoMezzo;
    }

    @Override
    public String getCodiceLinea() {
        return codiceLinea;
    }

    @Override
    public int getTempoPercorrenza() {
        return tempoPercorrenza;
    }

    @Override
    public Optional<Date> getInizioValidita() {
        return inizioValidita;
    }

    @Override
    public Optional<Date> getFineValidita() {
        return fineValidita;
    }

    @Override
    public boolean getAttiva() {
        return attiva;
    }

    @Override
    public int getCodiceTipoMezzo() {
        return codiceTipoMezzo;
    }

    @Override
    public void setAttiva(boolean attiva) {
        this.attiva = attiva;
    }

    @Override
    public void setFineValidita(Date fineValidita) {
        checkArgument(fineValidita != null, "La data di fine validità non può essere null");
        this.fineValidita.ifPresent(date -> checkArgument(date.before(fineValidita),
                "La data di fine validità deve essere successiva alla data attuale"));
        this.fineValidita = Optional.of(fineValidita);
    }
}