package it.unibo.smartcity.model.impl;

import java.sql.Connection;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import it.unibo.smartcity.data.DAOException;
import it.unibo.smartcity.data.DAOUtils;
import it.unibo.smartcity.model.api.OrarioLinea;

public class OrarioLineaImpl implements OrarioLinea {

    private final int codiceOrario;
    private final LocalTime oraPartenza;
    private final String giornoSettimanale;
    private final String codiceLinea;

    public OrarioLineaImpl(int codiceOrario, String oraPartenza, String giornoSettimanale, String codiceLinea) {
        this.codiceOrario = codiceOrario;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        this.oraPartenza = LocalTime.parse(oraPartenza, formatter);
        this.giornoSettimanale = giornoSettimanale;
        this.codiceLinea = codiceLinea;
    }

    @Override
    public int getCodiceOrario() {
        return codiceOrario;
    }

    @Override
    public LocalTime getOraPartenza() {
        return oraPartenza;
    }

    @Override
    public String getGiornoSettimanale() {
        return giornoSettimanale;
    }

    @Override
    public String getCodiceLinea() {
        return codiceLinea;
    }

    public static final class DAO {
        public static Set<OrarioLinea> list(Connection connection) {
            var query = "SELECT * FROM orari_linee";
            var orari = new HashSet<OrarioLinea>();
            try (
                var statement = DAOUtils.prepare(connection, query);
                var rs = statement.executeQuery();
            ) {
                while (rs.next()) {
                    var codiceOrario = rs.getInt("codice_orario");
                    var oraPartenza = rs.getString("ora_partenza");
                    var giornoSettimanale = rs.getString("giorno_settimanale");
                    var codiceLinea = rs.getString("codice_linea");

                    orari.add(new OrarioLineaImpl(codiceOrario, oraPartenza, giornoSettimanale, codiceLinea));
                }
            } catch (Exception e) {
                throw new DAOException("Errore nell'estrazione degli orari delle linee.", e);
            }
            return orari;
        }

        public static void insert(Connection connection, String codLinea, String giorno, LocalTime orario) {
            var query = "INSERT INTO orari_linee (ora_partenza, giorno_settimanale, codice_linea) VALUES (?, ?, ?)";
            try (
                var statement = DAOUtils.prepare(connection, query);
            ) {
                statement.setString(1, orario.toString());
                statement.setString(2, giorno);
                statement.setString(3, codLinea);
                statement.executeUpdate();
            } catch (Exception e) {
                throw new DAOException("Errore nell'inserimento dell'orario della linea:\n" + e.getMessage(), e);
            }
        }

        public static void delete(Connection connection, OrarioLinea orarioLinea) {
            var query = "DELETE FROM orari_linee WHERE codice_orario = ?";
            try (
                var statement = DAOUtils.prepare(connection, query);
            ) {
                statement.setInt(1, orarioLinea.getCodiceOrario());
                statement.executeUpdate();
            } catch (Exception e) {
                throw new DAOException("Errore nell'eliminazione dell'orario della linea.", e);
            }
        }

        public static List<OrarioLinea> listByCodiceLinea(Connection connection, String codLinea) {
            var query = "SELECT * FROM orari_linee WHERE codice_linea = ? ORDER BY ora_partenza";
            var orari = new LinkedList<OrarioLinea>();
            try (
                var statement = DAOUtils.prepare(connection, query, codLinea);
                var rs = statement.executeQuery();
            ) {
                while (rs.next()) {
                    var codiceOrario = rs.getInt("codice_orario");
                    var oraPartenza = rs.getString("ora_partenza");
                    var giornoSettimanale = rs.getString("giorno_settimanale");
                    var codiceLinea = rs.getString("codice_linea");

                    orari.add(new OrarioLineaImpl(codiceOrario, oraPartenza, giornoSettimanale, codiceLinea));
                }
            } catch (Exception e) {
                throw new DAOException("Errore nell'estrazione degli orari delle linee.", e);
            }
            return orari;
        }

    }
}