package it.unibo.smartcity.model.impl;

import java.sql.Connection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import it.unibo.smartcity.data.DAOException;
import it.unibo.smartcity.data.DAOUtils;
import it.unibo.smartcity.data.Queries;
import java.util.Optional;

import it.unibo.smartcity.model.api.Dipendente;

public class DipendenteImpl extends UtenteImpl implements Dipendente {

    private final Ruolo ruolo;

    public DipendenteImpl(final String cognome, final String nome, final String documento, final String codiceFiscale, final String username,
            final String email, final String telefono, final String password, final Ruolo ruolo) {
        super(cognome, nome, documento, codiceFiscale, username, email, telefono, password);
        this.ruolo = ruolo;
    }

    @Override
    public Ruolo getRuolo() {
        return ruolo;
    }

    public static final class DAO {

        public static Map<Date, OrarioLineaImpl> listOrari(Connection connection, String codiceLinea) {
            var orari = new HashMap<Date, OrarioLineaImpl>();
            try (
                var statement = DAOUtils.prepare(connection, Queries.LIST_ORARI_UNA_LINEA, codiceLinea);
                var rs = statement.executeQuery();
            ) {
                while (rs.next()) {
                    var orarioLavoro = new OrarioLineaImpl(
                        rs.getInt("codice_orario"),
                        rs.getString("ora_partenza"),
                        rs.getString("giorno_settimanale"),
                        rs.getString("codice_linea")
                    );
                    var data = rs.getDate("data");
                    orari.put(data, orarioLavoro);
                }
            } catch (Exception e) {
                throw new DAOException("Errore nell'estrazione degli orari di lavoro.", e);
            }
            return orari;
        }

        public static Optional<Ruolo> getRuolo(Connection connection, String user) {
            var query = "SELECT ruolo FROM dipendenti WHERE username = ?";
            String ruolo = null;
            try (
                var statement = DAOUtils.prepare(connection, query, user);
                var rs = statement.executeQuery();
            ) {
                while (rs.next()) {
                    ruolo = rs.getString("ruolo");
                }
            } catch (Exception e) {
                throw new DAOException("Errore nella query sul dipendente.", e);
            }

            return ruolo == null ? Optional.empty() : Optional.of(Ruolo.valueOf(ruolo.toUpperCase()));
        }
    }
}