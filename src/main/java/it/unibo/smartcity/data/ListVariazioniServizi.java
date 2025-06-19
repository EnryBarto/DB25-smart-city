package it.unibo.smartcity.data;

import static com.google.common.base.Preconditions.checkNotNull;

import java.sql.Connection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import it.unibo.smartcity.model.api.Azienda;
import it.unibo.smartcity.model.api.Linea;
import it.unibo.smartcity.model.api.ManutenzioneLinea;
import it.unibo.smartcity.model.impl.AziendaImpl;
import it.unibo.smartcity.model.impl.ManutenzioneLineaImpl;

public record ListVariazioniServizi(ManutenzioneLinea manutenzione, Optional<Azienda> a, Set<String> codiciLineeSostituite) {
    public ListVariazioniServizi(final ManutenzioneLinea manutenzione, final Optional<Azienda> a, final Set<String> codiciLineeSostituite) {
        this.manutenzione = checkNotNull(manutenzione);
        this.a = checkNotNull(a);
        this.codiciLineeSostituite = checkNotNull(codiciLineeSostituite);
    }

    public static final class DAO {
        public static Set<ListVariazioniServizi> get(final Connection connection, final String codiceLinea) {
            final var query = Queries.VARIAZIONE_SERVIZIO_LINEA;
            final var variazioni = new HashSet<ListVariazioniServizi>();
            try (
                var statement = DAOUtils.prepare(connection, query, codiceLinea);
                var rs = statement.executeQuery()
            ) {
                while (rs.next()) {
                    final var manutenzione = new ListVariazioniServizi(new ManutenzioneLineaImpl(
                                rs.getString("codice_linea_in_manutenzione"),
                                rs.getDate("data_inizio"),
                                rs.getDate("data_fine"),
                                rs.getString("nome"),
                                rs.getString("descrizione"),
                                rs.getString("p_iva")),
                        rs.getString("p_iva") == null ? Optional.empty() : Optional.of(new AziendaImpl(
                                rs.getString("partita_iva"),
                                rs.getString("ragione_sociale"),
                                "",
                                "",
                                "",
                                1,
                                rs.getString("telefono"),
                                rs.getString("email"))),
                        Set.of(rs.getString("codice_linea_sostituita")));
                    if (variazioni.stream()
                        .anyMatch(v ->
                            v.manutenzione.getCodiceLinea().equals(manutenzione.manutenzione().getCodiceLinea())
                            && v.manutenzione.getDataInizio().equals(manutenzione.manutenzione().getDataInizio())
                        )) {
                        variazioni.add(manutenzione);
                    } else {
                        final var variazione = variazioni.stream()
                            .filter(v ->
                                v.manutenzione.getCodiceLinea().equals(manutenzione.manutenzione().getCodiceLinea())
                                && v.manutenzione.getDataInizio().equals(manutenzione.manutenzione().getDataInizio())
                            ).findFirst().get();
                        final Set<String> codiciLineeSostituite = Stream.concat(
                            variazione.codiciLineeSostituite.stream(),
                            Stream.of(rs.getString("codice_linea_sostituita"))
                        ).collect(Collectors.toSet());
                        final ListVariazioniServizi newVariazione = new ListVariazioniServizi(
                            variazione.manutenzione,
                            variazione.a,
                            codiciLineeSostituite
                        );
                        variazioni.remove(variazione);
                        variazioni.add(newVariazione);
                    }
                }
            } catch(final Exception e) {
                throw new DAOException("Errore nell'estrazione delle variazioni di servizio", e);
            }
            return variazioni;
        }

        public static void insert(ManutenzioneLinea m, Linea lineaSostituita, Connection connection) {
            checkNotNull(m);
            checkNotNull(lineaSostituita);
            checkNotNull(connection);
            var query = Queries.AGGIUNGI_VARIAZIONE;
            try (var statement = DAOUtils.prepare(connection, query,
                m.getDataInizio(),
                m.getCodiceLinea(),
                lineaSostituita.getCodiceLinea())) {
                statement.executeUpdate();
            } catch (final Exception e) {
                throw new DAOException("Errore nell'inserimento della variazione di servizio:" + e.getMessage(), e);
            }
            query = Queries.UPDATE_LINEA_ATTIVA;
            /* try (var statement = DAOUtils.prepare(connection, query, # TODO: fix this
                m.getCodiceLinea(),
                m.getCodiceLinea())) {
                statement.executeUpdate();
            } catch (final Exception e) {
                throw new DAOException("Errore nell'aggiornamento della linea attiva:" + e.getMessage(), e);
            } */
        }
    }
}
