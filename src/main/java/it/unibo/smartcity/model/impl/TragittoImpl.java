package it.unibo.smartcity.model.impl;

import static com.google.common.base.Preconditions.checkArgument;

import java.sql.Connection;
import java.util.HashSet;
import java.util.Set;

import it.unibo.smartcity.data.DAOException;
import it.unibo.smartcity.data.DAOUtils;
import it.unibo.smartcity.model.api.Tragitto;

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
    }
}