package it.unibo.smartcity.model.impl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import it.unibo.smartcity.data.DAOException;
import it.unibo.smartcity.data.DAOUtils;
import it.unibo.smartcity.data.Printer;
import it.unibo.smartcity.data.Queries;
import it.unibo.smartcity.model.api.TariffaBiglietto;

public class TariffaBigliettoImpl implements TariffaBiglietto {

    private String nome;
    private int durata;
    private double prezzo;

    public TariffaBigliettoImpl(String nome, int durata, double prezzo) {
        this.nome = nome;
        this.durata = durata;
        this.prezzo = prezzo;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((nome == null) ? 0 : nome.hashCode());
        long temp;
        temp = Double.doubleToLongBits(durata);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(prezzo);
        result = prime * result + (int) (temp ^ (temp >>> 32));
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
        TariffaBigliettoImpl other = (TariffaBigliettoImpl) obj;
        if (nome == null) {
            if (other.nome != null)
                return false;
        } else if (!nome.equals(other.nome))
            return false;
        if (Double.doubleToLongBits(durata) != Double.doubleToLongBits(other.durata))
            return false;
        if (Double.doubleToLongBits(prezzo) != Double.doubleToLongBits(other.prezzo))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return Printer.stringify(
            "Tariffa Biglietti",
            List.of(
                Printer.field("nome", this.nome),
                Printer.field("durata", this.durata),
                Printer.field("prezzo", this.prezzo)
            )
        );
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public double getDurata() {
        return durata;
    }

    @Override
    public double getPrezzo() {
        return prezzo;
    }

    public static final class DAO {

        public static ArrayList<TariffaBigliettoImpl> list(Connection connection) {
            var tariffe = new ArrayList<TariffaBigliettoImpl>();
            try (
                var statement = DAOUtils.prepare(connection, Queries.LIST_TARIFFE_ABBONAMENTI);
                var rs = statement.executeQuery();
            ) {
                while (rs.next()) {
                    var tariffa = new TariffaBigliettoImpl(
                        rs.getString("nome"),
                        rs.getInt("durata"),
                        rs.getDouble("prezzo")
                        );
                    tariffe.add(tariffa);
                }

            } catch (Exception e) {
                throw new DAOException("Failed to list Tariffe Biglietti", e);
            }
            return tariffe;
        }
    }
}
