package it.unibo.smartcity.data;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.sql.Connection;

public record LineaPiuHubMobilita(String codLinea, int numHub) {

    public LineaPiuHubMobilita(final String codLinea, final int numHub) {
        checkNotNull(codLinea);
        checkArgument(numHub >= 0);
        this.codLinea = codLinea;
        this.numHub = numHub;
    }

    public static final class DAO {
        public static LineaPiuHubMobilita get(Connection connection) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.LINEA_PIU_HUB_MOBILITA);
                var rs = statement.executeQuery()
            ) {
                if (rs.next()) {
                    return new LineaPiuHubMobilita(
                        rs.getString("TRA.codice_linea"),
                        rs.getInt("num_hub")
                    );
                }
            } catch (final Exception e) {
                throw new DAOException("Errore nell'estrazione della media dei soldi delle multe", e);
            }
            return new LineaPiuHubMobilita("No results", 0);
        }
    }
}
