package it.unibo.smartcity.model.impl;

import java.sql.Connection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import it.unibo.smartcity.data.DAOException;
import it.unibo.smartcity.data.DAOUtils;
import it.unibo.smartcity.data.Queries;
import java.util.Optional;

import it.unibo.smartcity.model.api.Dipendente;
import it.unibo.smartcity.model.api.OrarioLinea;
import it.unibo.smartcity.model.api.Utente;

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

        public static Map<Date, OrarioLinea> listOrari(Connection connection, String username) {
            var orari = new HashMap<Date, OrarioLinea>();
            try (
                var statement = DAOUtils.prepare(connection, Queries.LIST_ORARIO_LINEE_ASSEGN, username);
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
            var query = "SELECT ruolo FROM DIPENDENTI WHERE username = ?";
            String ruolo = null;
            try (
                var statement = DAOUtils.prepare(connection, query, user);
                var rs = statement.executeQuery();
            ) {
                while (rs.next()) {
                    ruolo = rs.getString("ruolo");
                }
            } catch (Exception e) {
                throw new DAOException("Errore nell'estrazione del ruolo del dipendente.", e);
            }

            return ruolo == null ? Optional.empty() : Optional.of(Ruolo.valueOf(ruolo.toUpperCase()));
        }

        public static List<Dipendente> list(Connection connection) {
            List<Dipendente> dipendenti = new LinkedList<>();
            try (
                var statement = DAOUtils.prepare(connection, Queries.LIST_DIPENDENTI);
                var rs = statement.executeQuery();
            ) {
                while (rs.next()) {
                    var dip = new DipendenteImpl(
                        rs.getString("p.cognome"),
                        rs.getString("p.nome"),
                        rs.getString("p.documento"),
                        rs.getString("p.codice_fiscale"),
                        rs.getString("u.username"),
                        rs.getString("u.email"),
                        rs.getString("u.telefono"),
                        rs.getString("u.password"),
                        Dipendente.Ruolo.valueOf(rs.getString("ruolo").toUpperCase())
                        );
                        dipendenti.add(dip);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                throw new DAOException("Failed to list Dipendenti", e);
            }
            return dipendenti;
        }

        public static void insert(Connection connection, Utente utente, Ruolo ruolo) {
            try (
                var insertDipendente = DAOUtils.prepare(
                    connection,
                    Queries.INSERT_DIPENDENTE,
                    utente.getUsername(),
                    ruolo.toString());

                var insertPersona = DAOUtils.prepare(
                    connection,
                    Queries.INSERT_PERSONA,
                    utente.getCognome(),
                    utente.getNome(),
                    utente.getDocumento()
                );

                var insertUtente = DAOUtils.prepare(
                    connection,
                    Queries.INSERT_USER,
                    utente.getUsername(),
                    utente.getDocumento(),
                    utente.getEmail(),
                    utente.getTelefono(),
                    utente.getPassword()
                );
            ) {
                if (utente.getCodiceFiscale().isPresent()) insertPersona.setString(4, utente.getCodiceFiscale().get());
                else insertPersona.setNull(4, java.sql.Types.CHAR);

                if (PersonaImpl.DAO.byDocument(connection, utente.getDocumento()) == null) insertPersona.executeUpdate();

                insertUtente.executeUpdate();
                insertDipendente.executeUpdate();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                throw new DAOException("Failed to add dipendente", e);
            }
        }

        public static void remove(Connection connection, Dipendente dipendente) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.REMOVE_DIPENDENTE, dipendente.getUsername());
            ) {
                statement.executeUpdate();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                throw new DAOException("Failed to remove dipendente", e);
            }
        }
    }
}