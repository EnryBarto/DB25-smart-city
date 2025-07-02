package it.unibo.smartcity.model.impl;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.Date;

import it.unibo.smartcity.data.DAOException;
import it.unibo.smartcity.data.DAOUtils;
import it.unibo.smartcity.data.Queries;
import it.unibo.smartcity.model.api.Convalida;

public record ConvalidaImpl (int codiceBiglietto, Date dataOra, int codiceCorsa) implements Convalida {
    public static class DAO {

        public static void insert(Connection connection, int codiceBiglietto, LocalDateTime data, int codiceCorsa) {
            try (
                var statement = DAOUtils.prepare(
                    connection,
                    Queries.INSERT_CONVALIDA,
                    codiceBiglietto,
                    data,
                    codiceCorsa);
            ) {
                statement.executeUpdate();
            } catch (Exception e) {
                throw new DAOException("Failed to insert Convalida", e);
            }
        }
    }
}