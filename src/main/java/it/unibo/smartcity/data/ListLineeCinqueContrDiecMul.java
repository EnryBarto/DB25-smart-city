package it.unibo.smartcity.data;

import static com.google.common.base.Preconditions.checkNotNull;

import java.sql.Connection;
import java.util.HashSet;
import java.util.Set;

public record ListLineeCinqueContrDiecMul(String codiceLinea) {
    public ListLineeCinqueContrDiecMul(final String codiceLinea) {
        this.codiceLinea = checkNotNull(codiceLinea);
    }

    public static final class DAO {
        public static Set<ListLineeCinqueContrDiecMul> get(final Connection connection) {
            final var query = Queries.LINEE_CINQUE_CONTROL_DIECI_MULT;
            final var lineeCinqueContrDiecMul = new HashSet<ListLineeCinqueContrDiecMul>();
            try (
                var statement = DAOUtils.prepare(connection, query);
                var rs = statement.executeQuery()
            ) {
                while (rs.next()) {
                    lineeCinqueContrDiecMul.add(new ListLineeCinqueContrDiecMul(rs.getString("codice_linea")));
                }
            } catch (final Exception e) {
                throw new DAOException("Errore nell'estrazione delle linee con < cinque controlli e > dieci multe", e);
            }
            return lineeCinqueContrDiecMul;
        }
    }
}
