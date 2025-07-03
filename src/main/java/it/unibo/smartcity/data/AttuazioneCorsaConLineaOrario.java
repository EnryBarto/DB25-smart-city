package it.unibo.smartcity.data;

import java.sql.Connection;
import java.util.LinkedList;
import java.util.List;

import it.unibo.smartcity.model.api.AttuazioneCorsa;
import it.unibo.smartcity.model.api.Linea;
import it.unibo.smartcity.model.api.OrarioLinea;
import it.unibo.smartcity.model.impl.AttuazioneCorsaImpl;
import it.unibo.smartcity.model.impl.LineaImpl;
import it.unibo.smartcity.model.impl.OrarioLineaImpl;

public record AttuazioneCorsaConLineaOrario(AttuazioneCorsa attuazioneCorsa, Linea linea, OrarioLinea orario) {
    public static final class DAO {
        public static List<AttuazioneCorsaConLineaOrario> list(Connection connection) {
            var corse = new LinkedList<AttuazioneCorsaConLineaOrario>();
            try (
                var statement = DAOUtils.prepare(connection, Queries.LIST_ATTUAZIONI_CORSE);
                var rs = statement.executeQuery();
            ) {
                while (rs.next()) {
                    var c = new AttuazioneCorsaConLineaOrario(
                        new AttuazioneCorsaImpl(
                            rs.getInt("A.codice_corsa"),
                            rs.getDate("A.data"),
                            rs.getInt("A.codice_orario"),
                            rs.getString("A.n_immatricolazione"),
                            rs.getString("A.username")
                        ),
                        new LineaImpl(
                            rs.getString("L.codice_linea"),
                            rs.getInt("L.tempo_percorrenza"),
                            rs.getDate("L.inizio_validita"),
                            rs.getDate("L.fine_validita"),
                            rs.getBoolean("L.attiva"),
                            rs.getInt("L.codice_tipo_mezzo")
                        ),
                        new OrarioLineaImpl(
                            rs.getInt("O.codice_orario"),
                            rs.getString("O.ora_partenza"),
                            rs.getString("O.giorno_settimanale"),
                            rs.getString("O.codice_linea")
                        )
                    );
                    corse.add(c);
                }
            } catch (Exception e) {
                throw new DAOException("Errore nell'estrazione delle linee.", e);
            }
            return corse;
        }
    }

}
