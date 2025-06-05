package it.unibo.smartcity.model.impl;

import java.sql.Connection;
import java.util.HashSet;
import java.util.Set;

import it.unibo.smartcity.data.DAOException;
import it.unibo.smartcity.data.DAOUtils;
import it.unibo.smartcity.model.api.HubMobilita;

public class HubMobilitaImpl implements HubMobilita {

    private final int codiceHub;
    private final String longitudine;
    private final String latitudine;
    private final String nome;
    private final String indirizzo;
    private final int codiceFermata;

    public HubMobilitaImpl(int codiceHub, String longitudine, String latitudine, String nome, String indirizzo,
            int codiceFermata) {
        this.codiceHub = codiceHub;
        this.longitudine = longitudine;
        this.latitudine = latitudine;
        this.nome = nome;
        this.indirizzo = indirizzo;
        this.codiceFermata = codiceFermata;
    }

    @Override
    public int getCodiceHub() {
        return codiceHub;
    }

    @Override
    public String getLongitudine() {
        return longitudine;
    }

    @Override
    public String getLatitudine() {
        return latitudine;
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public String getIndirizzo() {
        return indirizzo;
    }

    @Override
    public int getCodiceFermata() {
        return codiceFermata;
    }

    public static final class DAO {
        public static Set<HubMobilita> list(Connection connection) {
            var query = "SELECT * FROM hub_mobilita";
            var hubs = new HashSet<HubMobilita>();
            try (
                var statement = DAOUtils.prepare(connection, query);
                var rs = statement.executeQuery();
            ) {
                while (rs.next()) {
                    var codiceHub = rs.getInt("codice_hub");
                    var longitudine = rs.getString("longitudine");
                    var latitudine = rs.getString("latitudine");
                    var nome = rs.getString("nome");
                    var indirizzo = rs.getString("indirizzo");
                    var codiceFermata = rs.getInt("codice_fermata");

                    hubs.add(new HubMobilitaImpl(codiceHub, longitudine, latitudine, nome, indirizzo, codiceFermata));
                }
            } catch (Exception e) {
                throw new DAOException("Errore nell'estrazione degli hub di mobilità.", e);
            }
            return hubs;
        }
    }
}