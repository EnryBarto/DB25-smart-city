package it.unibo.smartcity.data;

import it.unibo.smartcity.model.api.Fermata;
import it.unibo.smartcity.model.api.Linea;
import it.unibo.smartcity.model.api.TipologiaMezzo;
import it.unibo.smartcity.model.impl.FermataImpl;
import it.unibo.smartcity.model.impl.LineaImpl;
import it.unibo.smartcity.model.impl.TipologiaMezzoImpl;

import static com.google.common.base.Preconditions.checkNotNull;

import java.sql.Connection;
import java.util.LinkedList;
import java.util.List;

public record InfoLinea(Linea linea, TipologiaMezzo mezzo, Fermata partenza, Fermata arrivo) {

    public InfoLinea {
        checkNotNull(linea);
        checkNotNull(mezzo);
        checkNotNull(partenza);
        checkNotNull(arrivo);
    }

    public static final class DAO {
        public static List<InfoLinea> list(Connection connection) {
            var query = Queries.LIST_LINEE_ATTIVE;
            var lines = new LinkedList<InfoLinea>();
            try (
                var statement = DAOUtils.prepare(connection, query);
                var rs = statement.executeQuery();
            ) {
                while (rs.next()) {
                    lines.add(new InfoLinea(
                        new LineaImpl(
                            rs.getString("L.codice_linea"),
                            rs.getInt("L.tempo_percorrenza"),
                            rs.getDate("L.inizio_validita"),
                            rs.getDate("L.fine_validita"),
                            rs.getBoolean("L.attiva"),
                            rs.getInt("L.codice_tipo_mezzo")),
                        new TipologiaMezzoImpl(
                            rs.getInt("M.codice_tipo_mezzo"),
                            rs.getString("M.nome")),
                        new FermataImpl(
                            rs.getInt("F1.codice_fermata"),
                            rs.getString("F1.nome"),
                            rs.getString("F1.via"),
                            rs.getInt("F1.cap"),
                            rs.getString("F1.longitudine"),
                            rs.getString("F1.latitudine")),
                        new FermataImpl(
                            rs.getInt("F2.codice_fermata"),
                            rs.getString("F2.nome"),
                            rs.getString("F2.via"),
                            rs.getInt("F2.cap"),
                            rs.getString("F2.longitudine"),
                            rs.getString("F2.latitudine"))
                    ));
                }
            } catch (Exception e) {
                throw new DAOException("Errore nell'estrazione delle linee.", e);
            }
            return lines;
        }
    }
}
