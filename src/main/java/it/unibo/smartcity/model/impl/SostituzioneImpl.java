package it.unibo.smartcity.model.impl;
import java.sql.Connection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import it.unibo.smartcity.data.DAOException;
import it.unibo.smartcity.data.DAOUtils;
import it.unibo.smartcity.model.api.Sostituzione;

public class SostituzioneImpl implements Sostituzione {

    private final Date sostManutDataInizio;
    private final String sostManutCodiceLinea;
    private final String codiceLinea;

    public SostituzioneImpl(Date sostManutDataInizio, String sostManutCodiceLinea, String codiceLinea) {
        this.sostManutDataInizio = sostManutDataInizio;
        this.sostManutCodiceLinea = sostManutCodiceLinea;
        this.codiceLinea = codiceLinea;
    }
    @Override
    public Date getSostManutDataInizio() {
        return sostManutDataInizio;
    }
    @Override
    public String getSostManutCodiceLinea() {
        return sostManutCodiceLinea;
    }
    @Override
    public String getCodiceLinea() {
        return codiceLinea;
    }

    public static final class DAO {

        public static Set<Sostituzione> list(Connection connection) {
            var query = "SELECT * FROM SOSTITUZIONI";
            var sostituzioni = new HashSet<Sostituzione>();
            try (
                var statement = DAOUtils.prepare(connection, query);
                var rs = statement.executeQuery();
            ) {
                while (rs.next()) {
                    var sostituzione = new SostituzioneImpl(
                        rs.getDate("sost_manut_data_inizio"),
                        rs.getString("sost_manut_codice_linea"),
                        rs.getString("codice_linea")
                    );
                    sostituzioni.add(sostituzione);
                }
            } catch (Exception e) {
                throw new DAOException("Errore nell'estrazione delle sostituzioni.", e);
            }
            return sostituzioni;
        }
    }

}