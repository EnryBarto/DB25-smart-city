package it.unibo.smartcity.model.impl;

import java.sql.Connection;

import it.unibo.smartcity.data.DAOException;
import it.unibo.smartcity.data.DAOUtils;
import it.unibo.smartcity.model.api.Controllo;

public record ControlloImpl (String username, int codiceCorsa) implements Controllo {

    public static class DAO {
        public static void insert(final Connection connection, final Controllo controllo) {
            var query = "INSERT INTO CONTROLLI (username, codice_corsa) VALUES (?, ?)";
            try (var statement = DAOUtils.prepare(connection, query, controllo.username(), controllo.codiceCorsa())) {
                statement.executeUpdate();
            } catch (final Exception e) {
                throw new DAOException("Errore nell'inserimento del controllo: " + e.getMessage(), e);
            }
        }
    }
}