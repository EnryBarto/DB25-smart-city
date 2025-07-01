package it.unibo.smartcity.model.impl;

import java.sql.Connection;
import java.util.LinkedList;
import java.util.List;
import java.util.Date;

import com.google.common.base.Preconditions;

import it.unibo.smartcity.data.*;
import it.unibo.smartcity.model.api.Abbonamento;

public final class AbbonamentoImpl implements Abbonamento {

    private final Date dataInizio;
    private final int codiceAbbonamento;
    private final Date dataAcquisto;
    private final int numGiorni;
    private final String username;

    public AbbonamentoImpl(Date dataInizio, int codiceAbbonamento, Date dataAcquisto, int numGiorni, String username) {
        Preconditions.checkNotNull(dataInizio);
        Preconditions.checkNotNull(codiceAbbonamento);
        Preconditions.checkNotNull(dataAcquisto);

        this.dataInizio = dataInizio;
        this.codiceAbbonamento = codiceAbbonamento;
        this.dataAcquisto = dataAcquisto;
        this.numGiorni = numGiorni;
        this.username = username;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((dataInizio == null) ? 0 : dataInizio.hashCode());
        result = prime * result + codiceAbbonamento;
        result = prime * result + ((dataAcquisto == null) ? 0 : dataAcquisto.hashCode());
        result = prime * result + numGiorni;
        result = prime * result + ((username == null) ? 0 : username.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AbbonamentoImpl other = (AbbonamentoImpl) obj;
        if (dataInizio == null) {
            if (other.dataInizio != null)
                return false;
        } else if (!dataInizio.equals(other.dataInizio))
            return false;
        if (codiceAbbonamento != other.codiceAbbonamento)
            return false;
        if (dataAcquisto == null) {
            if (other.dataAcquisto != null)
                return false;
        } else if (!dataAcquisto.equals(other.dataAcquisto))
            return false;
        if (numGiorni != other.numGiorni)
            return false;
        if (username == null) {
            if (other.username != null)
                return false;
        } else if (!username.equals(other.username))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return Printer.stringify(
            "Abbonamento",
            List.of(
                Printer.field("data_inizio", this.dataInizio),
                Printer.field("data_acquisto", this.dataAcquisto),
                Printer.field("codice_abbonamento", this.codiceAbbonamento),
                Printer.field("num_giorni", this.numGiorni),
                Printer.field("username", this.username)
            )
        );
    }

    @Override
    public Date getDataInizio() {
        return dataInizio;
    }

    @Override
    public int getCodiceAbbonamento() {
        return codiceAbbonamento;
    }

    @Override
    public Date getDataAcquisto() {
        return dataAcquisto;
    }

    @Override
    public int getNumGiorni() {
        return numGiorni;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public static final class DAO {

        public static List<AbbonamentoImpl> list(Connection connection) {
            var abbonamenti = new LinkedList<AbbonamentoImpl>();
            try (
                var statement = DAOUtils.prepare(connection, Queries.LIST_ABBONAMENTI);
                var rs = statement.executeQuery();
            ) {
                while (rs.next()) {
                    var abbonamento = new AbbonamentoImpl(
                        rs.getDate("data_inizio"),
                        rs.getInt("codice_abbonamento"),
                        rs.getDate("data_acquisto"),
                        rs.getInt("num_giorni"),
                        rs.getString("username")
                        );
                    abbonamenti.add(abbonamento);
                }

            } catch (Exception e) {
                throw new DAOException("Failed to list Abbonamenti", e);
            }
            return abbonamenti;
        }
    }

}