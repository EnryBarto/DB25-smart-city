package it.unibo.smartcity.data;

import static com.google.common.base.Preconditions.checkNotNull;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import it.unibo.smartcity.model.api.Linea;
import it.unibo.smartcity.model.api.Tratta;
import it.unibo.smartcity.model.impl.LineaImpl;

public class InsertLineaComplete {
    private final Linea linea;
    private final List<Tratta> tratte;
    private final boolean straordinaria;

    public Linea getLinea() {
        return linea;
    }

    public List<Tratta> getTratte() {
        return tratte;
    }

    public boolean isStraordinaria() {
        return straordinaria;
    }

    public InsertLineaComplete(Linea linea, List<Tratta> tratte, boolean straordinaria) {
        this.linea = checkNotNull(linea, "linea must not be null");
        this.tratte = checkNotNull(tratte, "tratte must not be null");
        this.straordinaria = straordinaria;
    }

    public static class DAO {
        public static void insert(InsertLineaComplete l, Connection c) {
            checkNotNull(l, "info must not be null");
            try {
                c.setAutoCommit(false);
            } catch (final Exception e) {
                throw new DAOException("Can't set auto-commit false", e);
            }

            try {
                if (l.isStraordinaria()) {
                    LineaImpl.DAO.insertStraordinaria(c, l.getLinea());
                } else {
                    LineaImpl.DAO.insertOrdinaria(c, l.getLinea());
                }
            } catch (final Exception e) {
                try {
                    c.rollback();
                } catch (SQLException ex) {
                    throw new DAOException("Rollback failed", ex);
                }
                throw new DAOException("Errore nell'inserimento della linea", e);
            }
            int order = 1;
            for (final var t : l.getTratte()) {
                final var query = Queries.AGGIUNGI_TRAGITTO;
                try (
                        var statement = DAOUtils.prepare(c, query);) {
                    statement.setInt(1, t.getPartenzaCodiceFermata());
                    statement.setInt(2, t.getArrivoCodiceFermata());
                    statement.setString(3, l.getLinea().getCodiceLinea());
                    statement.setInt(4, order);
                    order++;
                    statement.executeUpdate();
                } catch (final Exception e) {
                    try {
                        c.rollback();
                    } catch (SQLException ex) {
                        throw new DAOException("Rollback failed", ex);
                    }
                    throw new DAOException("Errore nell'inserimento delle tratte", e);
                }
            }
            final var query = Queries.UPDATE_TEMPO_PERCORRENZA;
            try (
                    var statement = DAOUtils.prepare(c, query);) {
                statement.setString(1, l.getLinea().getCodiceLinea());
                statement.setString(2, l.getLinea().getCodiceLinea());
                statement.executeUpdate();
            } catch (final Exception e) {
                try {
                    c.rollback();
                } catch (SQLException ex) {
                    throw new DAOException("Rollback failed", ex);
                }
                throw new DAOException("Errore nell'aggiornamento del tempo di percorrenza", e);
            }
            try {
                c.commit();
            } catch (SQLException e) {
                throw new DAOException("Commit failed", e);
            }
            finally {
                try {
                    c.setAutoCommit(true);
                } catch (SQLException e) {
                    throw new DAOException("Can't set auto-commit true", e);
                }
            }
        }
    }

}
