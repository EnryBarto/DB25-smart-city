package it.unibo.smartcity.data;

import static com.google.common.base.Preconditions.checkNotNull;

import java.sql.Connection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import it.unibo.smartcity.model.api.HubMobilita;
import it.unibo.smartcity.model.impl.HubMobilitaImpl;

public record ListHubMobilita(HubMobilita hub, Optional<String> nomeFermata, String tipoContenuto, int postiDisponibili) {

    public ListHubMobilita(final HubMobilita hub, final Optional<String> nomeFermata, final String tipoContenuto, final int postiDisponibili) {
        this.hub = checkNotNull(hub);
        this.nomeFermata = checkNotNull(nomeFermata);
        this.tipoContenuto = checkNotNull(tipoContenuto);
        this.postiDisponibili = checkNotNull(postiDisponibili);
    }

    public static final class DAO {
        public static List<ListHubMobilita> get(final Connection connection) {
            final var query = Queries.LIST_HUB_MOBILITA;
            final var hubs = new LinkedList<ListHubMobilita>();
            try (
                var statement = DAOUtils.prepare(connection, query);
                var rs = statement.executeQuery();
            ) {
                while (rs.next()) {
                    hubs.add(new ListHubMobilita(
                        new HubMobilitaImpl(
                            rs.getInt("codice_hub"),
                            rs.getString("latitudine"),
                            rs.getString("longitudine"),
                            rs.getString("nome_hub"),
                            rs.getString("indirizzo_via"),
                            rs.getString("indirizzo_civico"),
                            rs.getString("indirizzo_comune"),
                            rs.getInt("indirizzo_cap"),
                            rs.getInt("codice_fermata")
                        ),
                        Optional.ofNullable(rs.getString("nome_fermata")),
                        rs.getString("tipo_contenuto"),
                        rs.getInt("posti_disponibili")
                    ));
                }
            } catch (final Exception e) {
                throw new DAOException("Errore nell'estrazione dei hub di mobilità", e);
            }
            return hubs;
        }
    }
}
