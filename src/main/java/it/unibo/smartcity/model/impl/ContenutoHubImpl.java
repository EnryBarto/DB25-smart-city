package it.unibo.smartcity.model.impl;
import java.sql.Connection;
import java.util.HashSet;
import java.util.Set;

import it.unibo.smartcity.data.DAOException;
import it.unibo.smartcity.data.DAOUtils;
import it.unibo.smartcity.model.api.ContenutoHub;

public class ContenutoHubImpl implements ContenutoHub {
    private final int codiceContenuto;
    private final String descrizione;

    public ContenutoHubImpl(int codiceContenuto, String descrizione) {
        this.codiceContenuto = codiceContenuto;
        this.descrizione = descrizione;
    }

    @Override
    public int getCodiceContenuto() {
        return codiceContenuto;
    }

    @Override
    public String getDescrizione() {
        return descrizione;
    }

    public static final class DAO {

        public static Set<ContenutoHub> list(Connection connection) {
            var query = "SELECT * FROM CONTENUTI_HUB";
            var contenuti = new HashSet<ContenutoHub>();
            try (
                var statement = DAOUtils.prepare(connection, query);
                var rs = statement.executeQuery();
            ) {
                while (rs.next()) {
                    var contenuto = new ContenutoHubImpl(
                        rs.getInt("codice_contenuto"),
                        rs.getString("descrizione")
                    );
                    contenuti.add(contenuto);
                }
            } catch (Exception e) {
                throw new DAOException("Errore nell'estrazione dei contenuti degli hub.", e);
            }
            return contenuti;
        }
    }
}