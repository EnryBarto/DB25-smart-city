package it.unibo.smartcity.model.impl;

import java.sql.Connection;
import java.util.LinkedList;
import java.util.List;

import com.google.common.base.Preconditions;

import it.unibo.smartcity.data.DAOException;
import it.unibo.smartcity.data.DAOUtils;
import it.unibo.smartcity.model.api.CausaleMulta;

public record CausaleMultaImpl (int codice, String reato, double prezzoBase, double prezzoMassimo) implements CausaleMulta {

    public CausaleMultaImpl {
        Preconditions.checkArgument(prezzoBase > 0, "Il prezzo base deve essere positivo");
        Preconditions.checkArgument(prezzoMassimo > 0, "Il prezzo massimo deve essere positivo");
    }

    public static final class DAO {
        public static List<CausaleMulta> list(Connection connection) {
            var query = "SELECT * FROM CAUSALI_MULTE";
            var causali = new LinkedList<CausaleMulta>();
            try (
                var statement = DAOUtils.prepare(connection, query);
                var rs = statement.executeQuery();
            ) {
                while (rs.next()) {
                    var causa = new CausaleMultaImpl(
                        rs.getInt("codice_causale"),
                        rs.getString("reato"),
                        rs.getDouble("prezzo_base"),
                        rs.getDouble("prezzo_massimo")
                    );
                    causali.add(causa);
                }
            } catch (Exception e) {
                throw new DAOException("Errore nell'estrazione delle causali di multa.", e);
            }
            return causali;
        }
    }
}