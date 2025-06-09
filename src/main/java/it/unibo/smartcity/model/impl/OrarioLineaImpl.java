package it.unibo.smartcity.model.impl;

import java.sql.Connection;
import java.util.HashSet;
import java.util.Set;

import it.unibo.smartcity.data.DAOException;
import it.unibo.smartcity.data.DAOUtils;
import it.unibo.smartcity.model.api.OrarioLinea;

public class OrarioLineaImpl implements OrarioLinea {

    private final int codiceOrario;
    private final String oraPartenza;
    private final String giornoSettimanale;
    private final String codiceLinea;

    public OrarioLineaImpl(int codiceOrario, String oraPartenza, String giornoSettimanale, String codiceLinea) {
        this.codiceOrario = codiceOrario;
        this.oraPartenza = oraPartenza;
        this.giornoSettimanale = giornoSettimanale;
        this.codiceLinea = codiceLinea;
    }

    @Override
    public int getCodiceOrario() {
        return codiceOrario;
    }

    @Override
    public String getOraPartenza() {
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
            var query = "SELECT * FROM orari_linea";
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

        public static void insert(Connection connection, OrarioLinea orarioLinea) {
            var query = "INSERT INTO orari_linea (ora_partenza, giorno_settimanale, codice_linea) VALUES (?, ?, ?)";
            try (
                var statement = DAOUtils.prepare(connection, query);
            ) {
                statement.setString(1, orarioLinea.getOraPartenza());
                statement.setString(2, orarioLinea.getGiornoSettimanale());
                statement.setString(3, orarioLinea.getCodiceLinea());
                statement.executeUpdate();
            } catch (Exception e) {
                throw new DAOException("Errore nell'inserimento dell'orario della linea.", e);
            }
        }

        public static void delete(Connection connection, OrarioLinea orarioLinea) {
            var query = "DELETE FROM orari_linea WHERE codice_orario = ?";
            try (
                var statement = DAOUtils.prepare(connection, query);
            ) {
                statement.setInt(1, orarioLinea.getCodiceOrario());
                statement.executeUpdate();
            } catch (Exception e) {
                throw new DAOException("Errore nell'eliminazione dell'orario della linea.", e);
            }
        }
    }
}