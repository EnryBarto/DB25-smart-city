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
    }
}