package it.unibo.smartcity.model.impl;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import it.unibo.smartcity.data.DAOException;
import it.unibo.smartcity.data.DAOUtils;
import it.unibo.smartcity.data.Queries;
import it.unibo.smartcity.model.api.Tragitto;
import it.unibo.smartcity.model.api.Tratta;
import it.unibo.smartcity.data.TragittoConTempo;

public class TragittoImpl implements Tragitto {

    private final int partenzaCodiceFermata;
    private final int arrivoCodiceFermata;
    private final String codiceLinea;
    private final int ordine;

    public TragittoImpl(int partenzaCodiceFermata, int arrivoCodiceFermata, String codiceLinea, int ordine) {
        this.partenzaCodiceFermata = partenzaCodiceFermata;
        this.arrivoCodiceFermata = arrivoCodiceFermata;
        this.codiceLinea = codiceLinea;
        this.ordine = ordine;
        checkArgument(partenzaCodiceFermata != arrivoCodiceFermata,
"Il codice della fermata di partenza e di arrivo devono essere diversi");
        checkArgument(ordine >= 0, "L'ordine deve essere un numero non negativo");
    }

    @Override
    public int getPartenzaCodiceFermata() {
        return partenzaCodiceFermata;
    }

    @Override
    public int getArrivoCodiceFermata() {
        return arrivoCodiceFermata;
    }

    @Override
    public String getCodiceLinea() {
        return codiceLinea;
    }

    @Override
    public int getOrdine() {
        return ordine;
    }

    public static final class DAO {

        public static Set<Tragitto> list(Connection connection) {
            var query = "SELECT * FROM TRAGITTI";
            var tragitti = new HashSet<Tragitto>();
            try (
                var statement = DAOUtils.prepare(connection, query);
                var rs = statement.executeQuery();
            ) {
                while (rs.next()) {
                    var partenzaCodiceFermata = rs.getInt("partenza_codice_fermata");
                    var arrivoCodiceFermata = rs.getInt("arrivo_codice_fermata");
                    var codiceLinea = rs.getString("codice_linea");
                    var ordine = rs.getInt("ordine");

                    tragitti.add(new TragittoImpl(partenzaCodiceFermata, arrivoCodiceFermata, codiceLinea, ordine));
                }
            } catch (Exception e) {
                throw new DAOException("Errore nell'estrazione dei tragitti.", e);
            }
            return tragitti;
        }

        public static int getLastOrdine(Connection connection, String codLinea) {
            int ordine = 0;
            try (
                var statement = DAOUtils.prepare(connection, Queries.SELECT_ULTIMO_TRAGITTO, codLinea);
                var rs = statement.executeQuery();
            ) {
                while (rs.next()) {
                    ordine = rs.getInt("ordine");
                }
            } catch (Exception e) {
                throw new DAOException(e.getMessage(), e);
            }
            return ordine;

        }

        public static void insert(Connection connection, String codLinea, Tratta tratta) {
            int ordine = DAO.getLastOrdine(connection, codLinea);
            ordine++;
            var query = "INSERT INTO TRAGITTI (codice_linea, partenza_codice_fermata, arrivo_codice_fermata, ordine) VALUES (?, ?, ?, ?)";
            try (
                var statement = DAOUtils.prepare(connection, query);
            ) {
                connection.setAutoCommit(false);

                statement.setString(1, codLinea);
                statement.setInt(2, tratta.getPartenzaCodiceFermata());
                statement.setInt(3, tratta.getArrivoCodiceFermata());
                statement.setInt(4, ordine);
                statement.executeUpdate();

                int tempoPercorrenza = LineaImpl.DAO.getTempoPercorrenza(connection, codLinea);
                tempoPercorrenza += tratta.getTempoPercorrenza();

                LineaImpl.DAO.updateTempoPercorrenza(connection, codLinea, tempoPercorrenza);

                connection.commit();
                connection.setAutoCommit(true);

            } catch (SQLException e) {
                if (connection != null) {
                    try {
                        connection.rollback();
                    } catch (SQLException ex) {
                        throw new DAOException(ex.getMessage(), ex);
                    }
                }
                throw new DAOException(e.getMessage(), e);
            } catch (Exception e) {
                throw new DAOException("Errore nell'inserimento del tragitto.\n" + e.getMessage(), e);
            }
        }

        public static List<Tragitto> listUltimiTragitti(Connection connection) {
            var list = new LinkedList<Tragitto>();
            try (
                var statement = DAOUtils.prepare(connection, Queries.LIST_ULTIMI_TRAGITTI);
                var rs = statement.executeQuery();
            ) {
                while (rs.next()) {
                    var trag = new TragittoImpl(
                        rs.getInt("partenza_codice_fermata"),
                        rs.getInt("arrivo_codice_fermata"),
                        rs.getString("codice_linea"),
                        rs.getInt("ordine")
                    );
                    list.add(trag);
                }
            } catch (Exception e) {
                throw new DAOException(e.getMessage(), e);
            }
            return list;
        }

        public static void remove(Connection connection, Tragitto tragitto) {
            checkNotNull(tragitto);
            var query = "DELETE FROM TRAGITTI WHERE arrivo_codice_fermata = ? AND partenza_codice_fermata = ? AND codice_linea = ?";
            try (
                var statement = DAOUtils.prepare(connection, query);
            ) {
                connection.setAutoCommit(false);

                statement.setInt(1, tragitto.getArrivoCodiceFermata());
                statement.setInt(2, tragitto.getPartenzaCodiceFermata());
                statement.setString(3, tragitto.getCodiceLinea());
                statement.executeUpdate();

                int tempoPercorrenza = LineaImpl.DAO.getTempoPercorrenza(connection, tragitto.getCodiceLinea());
                tempoPercorrenza -= TrattaImpl.DAO.select(connection, tragitto.getPartenzaCodiceFermata(), tragitto.getArrivoCodiceFermata()).getTempoPercorrenza();

                LineaImpl.DAO.updateTempoPercorrenza(connection, tragitto.getCodiceLinea(), tempoPercorrenza);

                connection.commit();
                connection.setAutoCommit(true);

            } catch (SQLException e) {
                if (connection != null) {
                    try {
                        connection.rollback();
                    } catch (SQLException ex) {
                        throw new DAOException(ex.getMessage(), ex);
                    }
                }
                throw new DAOException(e.getMessage(), e);
            } catch (Exception e) {
                throw new DAOException(e.getMessage(), e);
            }

        }

        public static List<TragittoConTempo> tragittiByLinea(Connection connection, String codiceLinea) {
            var tragitti = new LinkedList<TragittoConTempo>();
            try (
                var statement = DAOUtils.prepare(connection, Queries.LIST_TRATTE_PER_LINEA, codiceLinea);
                var rs = statement.executeQuery();
            ){
                while (rs.next()) {
                    var tragitto = new TragittoConTempo(
                        new TragittoImpl(
                            rs.getInt("t.partenza_codice_fermata"),
                            rs.getInt("t.arrivo_codice_fermata"),
                            codiceLinea,
                            rs.getInt("t.ordine")),
                        rs.getInt("tr.tempo_percorrenza"),
                        new FermataImpl(
                            rs.getInt("f_par.codice_fermata"),
                            rs.getString("f_par.nome"),
                            rs.getString("f_par.indirizzo_via"),
                            rs.getString("f_par.indirizzo_civico"),
                            rs.getString("f_par.indirizzo_comune"),
                            rs.getInt("f_par.indirizzo_cap"),
                            rs.getString("f_par.longitudine"),
                            rs.getString("f_par.latitudine")
                        ),
                        new FermataImpl(
                            rs.getInt("f_arr.codice_fermata"),
                            rs.getString("f_arr.nome"),
                            rs.getString("f_arr.indirizzo_via"),
                            rs.getString("f_arr.indirizzo_civico"),
                            rs.getString("f_arr.indirizzo_comune"),
                            rs.getInt("f_arr.indirizzo_cap"),
                            rs.getString("f_arr.longitudine"),
                            rs.getString("f_arr.latitudine")
                        )
                    );
                    tragitti.add(tragitto);
                }
            } catch (Exception e) {
                throw new DAOException("failed to list tragitti by linea", e);
            }
            return tragitti;
        }
    }
}