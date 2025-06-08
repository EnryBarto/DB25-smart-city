package it.unibo.smartcity.data;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.sql.Connection;

public record MediaSoldiMulte(double mediaSoldi) {

    public MediaSoldiMulte(double mediaSoldi) {
        checkArgument(mediaSoldi >= 0, "La media dei soldi delle multe deve essere maggiore o uguale a zero");
        this.mediaSoldi = checkNotNull(mediaSoldi);
    }

    public static final class DAO {
        public static MediaSoldiMulte get(Connection connection) {
            final var query = Queries.MEDIA_SOLDI_MULTE;
            try (
                var statement = DAOUtils.prepare(connection, query);
                var rs = statement.executeQuery()
            ) {
                if (rs.next()) {
                    return new MediaSoldiMulte(rs.getDouble("media_soldi"));
                }
            } catch (final Exception e) {
                throw new DAOException("Errore nell'estrazione della media dei soldi delle multe", e);
            }
            return new MediaSoldiMulte(0.0);
        }
    }
}
