package it.unibo.smartcity.model.impl;

import static com.google.common.base.Preconditions.checkArgument;

import java.sql.Connection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import it.unibo.smartcity.data.DAOException;
import it.unibo.smartcity.data.DAOUtils;
import it.unibo.smartcity.data.Queries;
import it.unibo.smartcity.model.api.Tratta;

public class TrattaImpl implements Tratta {

    private final int arrivoCodiceFermata;
    private final int partenzaCodiceFermata;
    private final int tempoPercorrenza;

    public TrattaImpl(int arrivoCodiceFermata, int partenzaCodiceFermata, int tempoPercorrenza) {
        this.arrivoCodiceFermata = arrivoCodiceFermata;
        this.partenzaCodiceFermata = partenzaCodiceFermata;
        this.tempoPercorrenza = tempoPercorrenza;
        checkArgument(partenzaCodiceFermata != arrivoCodiceFermata,
"Il codice della fermata di partenza e di arrivo devono essere diversi");
        checkArgument(tempoPercorrenza > 0, "Il tempo di percorrenza deve essere un numero positivo");}

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

        public static void insert(Connection connection, Tratta tratta) {
            var query = "INSERT INTO tratte (arrivo_codice_fermata, partenza_codice_fermata, tempo_percorrenza) VALUES (?, ?, ?)";
            try (
                var statement = DAOUtils.prepare(connection, query);
            ) {
                statement.setInt(1, tratta.getArrivoCodiceFermata());
                statement.setInt(2, tratta.getPartenzaCodiceFermata());
                statement.setInt(3, tratta.getTempoPercorrenza());
                statement.executeUpdate();
            } catch (Exception e) {
                throw new DAOException("Errore nell'inserimento della tratta.", e);
            }
        }

        public static void delete(Connection connection, Tratta tratta) {
            var query = "DELETE FROM tratte WHERE arrivo_codice_fermata = ? AND partenza_codice_fermata = ?";
            try (
                var statement = DAOUtils.prepare(connection, query);
            ) {
                statement.setInt(1, tratta.getArrivoCodiceFermata());
                statement.setInt(2, tratta.getPartenzaCodiceFermata());
                statement.executeUpdate();
            } catch (Exception e) {
                throw new DAOException("Errore nell'eliminazione della tratta.", e);
            }
        }

        public static List<Tratta> listByCodicePartenza(Connection connection, String codiceLinea) {
            var list = new LinkedList<Tratta>();
            try (
                var statement = DAOUtils.prepare(connection, Queries.LIST_TRATTE_POSSIBILI_LINEA, codiceLinea, codiceLinea);
                var rs = statement.executeQuery();
            ) {
                while (rs.next()) {
                    list.add(new TrattaImpl(
                        rs.getInt("arrivo_codice_fermata"),
                        rs.getInt("partenza_codice_fermata"),
                        rs.getInt("tempo_percorrenza")
                    ));

                }
            } catch (Exception e) {
                throw new DAOException("Errore nell'estrazione delle tratte.", e);
            }
            return list;
        }

        public static Tratta select(Connection connection, int partenzaCodiceFermata, int arrivoCodiceFermata) {
            Tratta tratta = null;
            var query = "SELECT * FROM TRATTE WHERE partenza_codice_fermata = ? AND arrivo_codice_fermata = ?";
            try (
                var statement = DAOUtils.prepare(connection, query, partenzaCodiceFermata, arrivoCodiceFermata);
                var rs = statement.executeQuery();
            ) {
                while (rs.next()) {
                    tratta = new TrattaImpl(
                        rs.getInt("arrivo_codice_fermata"),
                        rs.getInt("partenza_codice_fermata"),
                        rs.getInt("tempo_percorrenza")
                    );
                }
            } catch (Exception e) {
                throw new DAOException(e.getMessage(), e);
            }
            return tratta;
        }
    }
}