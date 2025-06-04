package it.unibo.smartcity.model.impl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Preconditions;

import it.unibo.smartcity.data.*;
import it.unibo.smartcity.model.api.TariffaAbbonamento;

public class TariffaAbbonamentoImpl implements TariffaAbbonamento {

    private final String nome;
    private final int num_giorni;
    private final Double prezzo;

    public TariffaAbbonamentoImpl(String nome, int num_giorni, Double prezzo) {
        Preconditions.checkArgument(!nome.isBlank());
        Preconditions.checkNotNull(num_giorni);
        Preconditions.checkNotNull(prezzo);

        this.nome = nome;
        this.num_giorni = num_giorni;
        this.prezzo = prezzo;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((nome == null) ? 0 : nome.hashCode());
        result = prime * result + num_giorni;
        result = prime * result + ((prezzo == null) ? 0 : prezzo.hashCode());
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
        TariffaAbbonamentoImpl other = (TariffaAbbonamentoImpl) obj;
        if (nome == null) {
            if (other.nome != null)
                return false;
        } else if (!nome.equals(other.nome))
            return false;
        if (num_giorni != other.num_giorni)
            return false;
        if (prezzo == null) {
            if (other.prezzo != null)
                return false;
        } else if (!prezzo.equals(other.prezzo))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return Printer.stringify(
            "Tariffa Abbonamento",
            List.of(
                Printer.field("nome", this.nome),
                Printer.field("numero di giorni", this.num_giorni),
                Printer.field("prezzo", this.prezzo)
            )
        );
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public int getNum_giorni() {
        return num_giorni;
    }

    @Override
    public Double getPrezzo() {
        return prezzo;
    }

    public static final class DAO {

        public static ArrayList<TariffaAbbonamentoImpl> list(Connection connection) {
            var tariffe = new ArrayList<TariffaAbbonamentoImpl>();
            try (
                var statement = DAOUtils.prepare(connection, Queries.LIST_TARIFFE_ABBONAMENTI);
                var rs = statement.executeQuery();
            ) {
                while (rs.next()) {
                    var tariffa = new TariffaAbbonamentoImpl(
                        rs.getString("nome"),
                        rs.getInt("num_giorni"),
                        rs.getDouble("prezzo")
                        );
                    tariffe.add(tariffa);
                }

            } catch (Exception e) {
                throw new DAOException("Failed to list Tariffe Abbonamenti", e);
            }
            return tariffe;
        }
    }
}