package it.unibo.smartcity.model.impl;

import java.util.Date;
import java.util.Optional;

import com.google.common.base.Preconditions;

import it.unibo.smartcity.model.api.Multa;

public class MultaImpl implements Multa {

    private final int codice;
    private final Date dataOraEmissione;
    private final double importo;
    private Optional<Date> dataPagamento;
    private final int codiceCausale;
    private final int codiceCorsa;
    private final String documentoIntestatario;
    private final String usernameControllore;

    public MultaImpl(final int codice, final Date dataOraEmissione, final double importo, final Date dataPagamento, final int codiceCausale,
            final int codiceCorsa, final String documentoIntestatario, final String usernameControllore) {
        this.codice = codice;
        this.dataOraEmissione = dataOraEmissione;
        this.importo = importo;
        this.dataPagamento = dataPagamento == null ? Optional.empty() : Optional.of(dataPagamento);
        this.codiceCausale = codiceCausale;
        this.codiceCorsa = codiceCorsa;
        this.documentoIntestatario = documentoIntestatario;
        this.usernameControllore = usernameControllore;
    }

    public MultaImpl(final int codice, final Date dataOraEmissione, final double importo, final int codiceCausale, final int codiceCorsa,
            final String documentoIntestatario, final String usernameControllore) {
        this(codice, dataOraEmissione, importo, null, codiceCausale, codiceCorsa, documentoIntestatario, usernameControllore);
    }

    @Override
    public void setDataPagamento(final Date data) {
        Preconditions.checkState(dataPagamento.isEmpty(), "La multa risulta già pagata");
        this.dataPagamento = Optional.of(data);
    }

    @Override
    public int getCodice() {
        return codice;
    }

    @Override
    public Date getDataOraEmissione() {
        return dataOraEmissione;
    }

    @Override
    public double getImporto() {
        return importo;
    }

    @Override
    public Optional<Date> getDataPagamento() {
        return dataPagamento;
    }

    @Override
    public int getCodiceCausale() {
        return codiceCausale;
    }

    @Override
    public int getCodiceCorsa() {
        return codiceCorsa;
    }

    @Override
    public String getDocumentoIntestatario() {
        return documentoIntestatario;
    }

    @Override
    public String getUsernameControllore() {
        return usernameControllore;
    }

}