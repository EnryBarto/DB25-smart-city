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
            var lines = new LinkedList<InfoLinea>();
            try (
                var statement = DAOUtils.prepare(connection, Queries.LIST_LINEE_ATTIVE);
                var rs = statement.executeQuery();
            ) {
                while (rs.next()) {
                    lines.add(new InfoLinea(
                        new LineaImpl(
                            rs.getString("codice_linea"),
                            rs.getInt("tempo_percorrenza"),
                            rs.getDate("inizio_validita"),
                            rs.getDate("fine_validita"),
                            rs.getBoolean("attiva"),
                            rs.getInt("codice_tipo_mezzo")),
                        new TipologiaMezzoImpl(
                            rs.getInt("codice_tipo_mezzo"),
                            rs.getString("tipo_mezzo")),
                        new FermataImpl(
                            rs.getInt("part_codice_fermata"),
                            rs.getString("part_nome"),
                            rs.getString("part_via"),
                            rs.getString("part_civico"),
                            rs.getString("part_comune"),
                            rs.getInt("part_cap"),
                            rs.getString("part_lat"),
                            rs.getString("part_long")),
                        new FermataImpl(
                            rs.getInt("arr_codice_fermata"),
                            rs.getString("arr_nome"),
                            rs.getString("arr_via"),
                            rs.getString("arr_civico"),
                            rs.getString("arr_comune"),
                            rs.getInt("arr_cap"),
                            rs.getString("arr_lat"),
                            rs.getString("arr_long"))
                    ));
                }
            } catch (Exception e) {
                throw new DAOException("Errore nell'estrazione delle linee.", e);
            }
            return lines;
        }
    }
}
