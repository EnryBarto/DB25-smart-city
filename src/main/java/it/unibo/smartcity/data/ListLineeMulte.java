package it.unibo.smartcity.data;

import static com.google.common.base.Preconditions.checkNotNull;

import java.sql.Connection;
import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

public record ListLineeMulte(String codiceLinea, int numeroMulte) {

    public ListLineeMulte(final String codiceLinea, final int numeroMulte) {
        this.codiceLinea = checkNotNull(codiceLinea);
        this.numeroMulte = checkNotNull(numeroMulte);
    }

    public static final class DAO {
        public static List<ListLineeMulte> get(final Connection connection, final Date dataInizio, final Date dataFine) {
            final var query = Queries.LINEE_PIU_MULTE;
            final var lineeMulte = new LinkedList<ListLineeMulte>();
            try (
                var statement = DAOUtils.prepare(connection, query, dataInizio, dataFine);
                var rs = statement.executeQuery()
            ) {
                while (rs.next()) {
                    lineeMulte.add(new ListLineeMulte(
                        rs.getString("codice_linea"),
                        rs.getInt("numero_multe")
                    ));
                }
            } catch (final Exception e) {
                throw new DAOException("Errore nell'estrazione delle linee con più multe", e);
            }
            return lineeMulte;
        }
    }
}