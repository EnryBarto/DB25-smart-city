package it.unibo.smartcity.model.impl;

import java.sql.Connection;
import java.util.HashSet;
import java.util.Set;

import it.unibo.smartcity.data.DAOException;
import it.unibo.smartcity.data.DAOUtils;
import it.unibo.smartcity.model.api.Tratta;

public class TrattaImpl implements Tratta {

    private final int arrivoCodiceFermata;
    private final int partenzaCodiceFermata;
    private final int tempoPercorrenza;

    public TrattaImpl(int arrivoCodiceFermata, int partenzaCodiceFermata, int tempoPercorrenza) {
        this.arrivoCodiceFermata = arrivoCodiceFermata;
        this.partenzaCodiceFermata = partenzaCodiceFermata;
        this.tempoPercorrenza = tempoPercorrenza;
    }

    @Override
    public int getArrivoCodiceFermata() {
        return arrivoCodiceFermata;
    }

    @Override
    public int getPartenzaCodiceFermata() {
        return partenzaCodiceFermata;
    }

    @Override
    public int getTempoPercorrenza() {
        return tempoPercorrenza;
    }

    public static final class DAO {

        public static Set<Tratta> list(Connection connection) {
            var query = "SELECT * FROM tratte";
            var tratte = new HashSet<Tratta>();
            try (
                var statement = DAOUtils.prepare(connection, query);
                var rs = statement.executeQuery();
            ) {
                while (rs.next()) {
                    var arrivoCodiceFermata = rs.getInt("arrivo_codice_fermata");
                    var partenzaCodiceFermata = rs.getInt("partenza_codice_fermata");
                    var tempoPercorrenza = rs.getInt("tempo_percorrenza");

                    tratte.add(new TrattaImpl(arrivoCodiceFermata, partenzaCodiceFermata, tempoPercorrenza));
                }
            } catch (Exception e) {
                throw new DAOException("Errore nell'estrazione delle tratte.", e);
            }
            return tratte;
        }
    }
}