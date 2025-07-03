package it.unibo.smartcity.data;

import java.sql.Connection;
import java.util.Date;

import it.unibo.smartcity.model.api.TariffaBiglietto;

public record IncassiTariffa(TariffaBiglietto tariffa, double importo, Date dateFrom, Date dateTo) {

    public static class DAO {

        public static IncassiTariffa selectByDate(Connection connection, Date dateFrom, Date dateTo, TariffaBiglietto tariffa) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.INCASSI_TIPO_TITOLO_RANGE_DATA, tariffa.getDurata(), dateFrom, dateTo);
                var rs = statement.executeQuery()
            ) {
                double incasso = 0;
                if (rs.next()) {
                    incasso = rs.getDouble("incasso");
                }
                return new IncassiTariffa(tariffa, incasso, dateFrom, dateTo);
            } catch (final Exception e) {
                throw new DAOException("Errore nell'estrazione degli incassi", e);
            }
        }
    }
}
