package it.unibo.smartcity.model.impl;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.sql.Connection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import it.unibo.smartcity.data.DAOException;
import it.unibo.smartcity.data.DAOUtils;
import it.unibo.smartcity.data.Queries;
import it.unibo.smartcity.model.api.Tragitto;
import it.unibo.smartcity.model.api.Tratta;

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
            var query = "SELECT * FROM tragitti";
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
                statement.setString(1, codLinea);
                statement.setInt(2, tratta.getPartenzaCodiceFermata());
                statement.setInt(3, tratta.getArrivoCodiceFermata());
                statement.setInt(4, ordine);
                statement.executeUpdate();

                int tempoPercorrenza = LineaImpl.DAO.getTempoPercorrenza(connection, codLinea);
                tempoPercorrenza += tratta.getTempoPercorrenza();

                LineaImpl.DAO.updateTempoPercorrenza(connection, codLinea, tempoPercorrenza);

            } catch (Exception e) {
                throw new DAOException("Errore nell'inserimento della linea.", e);
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
                        rs.getInt("TRA.partenza_codice_fermata"),
                        rs.getInt("TRA.arrivo_codice_fermata"),
                        rs.getString("TRA.codice_linea"),
                        rs.getInt("TRA.ordine")
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
                statement.setInt(1, tragitto.getArrivoCodiceFermata());
                statement.setInt(2, tragitto.getPartenzaCodiceFermata());
                statement.setString(3, tragitto.getCodiceLinea());
                statement.executeUpdate();

                int tempoPercorrenza = LineaImpl.DAO.getTempoPercorrenza(connection, tragitto.getCodiceLinea());
                tempoPercorrenza -= TrattaImpl.DAO.select(connection, tragitto.getPartenzaCodiceFermata(), tragitto.getArrivoCodiceFermata()).getTempoPercorrenza();

                LineaImpl.DAO.updateTempoPercorrenza(connection, tragitto.getCodiceLinea(), tempoPercorrenza);

            } catch (Exception e) {
                throw new DAOException(e.getMessage(), e);
            }

        }

    }
}