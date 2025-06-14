package it.unibo.smartcity.model.impl;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import it.unibo.smartcity.data.DAOException;
import it.unibo.smartcity.data.DAOUtils;
import it.unibo.smartcity.data.Queries;
import it.unibo.smartcity.model.api.Biglietto;

public class BigliettoImpl implements Biglietto {

    private final int codice;
    private final Date dataAcquisto;
    private final int codDurata;
    private final Optional<String> username;


    public BigliettoImpl(final int codice, final Date dataAcquisto, final int codDurata, final String username) {
        this.codice = codice;
        this.dataAcquisto = dataAcquisto;
        this.codDurata = codDurata;
        this.username = username.isBlank() ? Optional.empty() : Optional.of(username);
    }

    public BigliettoImpl(final int codice, Date dataAcquisto, int durata) {
        this(codice, dataAcquisto, durata, "");
    }

    @Override
    public int getCodice() {
        return codice;
    }

    @Override
    public Date getDataAcquisto() {
        return dataAcquisto;
    }

    @Override
    public int getCodDurata() {
        return codDurata;
    }

    @Override
    public Optional<String> getUsername() {
        return username;
    }

    public class DAO {

        public static void insert(Connection connection, String data, int durata, String username) {
            try (
                var statement = DAOUtils.prepare(
                    connection,
                    Queries.INSERT_BIGLIETTO,
                    java.sql.Date.valueOf(data),
                    durata,
                    username);
            ) {
                statement.executeUpdate();
            } catch (Exception e) {
                throw new DAOException("Failed to insert Biglietto", e);
            }
        }

        public static List<Biglietto> byUser(Connection connection, String username) {
            var biglietti = new ArrayList<Biglietto>();
            try (
                var statement = DAOUtils.prepare(connection, Queries.ESTRAZ_BIGLIETTI_BYUSER, username);
                var rs = statement.executeQuery();
            ){
                while (rs.next()) {
                    var biglietto = new BigliettoImpl(
                        rs.getInt("codice_biglietto"),
                        rs.getDate("data_acquisto"),
                        rs.getInt("durata"),
                        rs.getString("username")
                    );
                    biglietti.add(biglietto);
                }

            } catch (Exception e) {
                throw new DAOException("Failed to list biglietti by user", e);
            }
            return biglietti;
        }
    }
}