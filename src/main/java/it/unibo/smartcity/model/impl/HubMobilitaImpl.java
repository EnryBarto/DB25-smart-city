package it.unibo.smartcity.model.impl;

import java.sql.Connection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import it.unibo.smartcity.data.DAOException;
import it.unibo.smartcity.data.DAOUtils;
import it.unibo.smartcity.model.api.HubMobilita;

public class HubMobilitaImpl implements HubMobilita {

    private final int codiceHub;
    private final String longitudine;
    private final String latitudine;
    private final String nome;
    private final Indirizzo indirizzo;
    private final Optional<Integer> codiceFermata;

    public HubMobilitaImpl(int codiceHub, String longitudine, String latitudine, String nome, String via,
            String civico, String comune, int cap, int codiceFermata) {
        this.codiceHub = codiceHub;
        this.longitudine = longitudine;
        this.latitudine = latitudine;
        this.nome = nome;
        this.indirizzo = new Indirizzo(via, civico, comune, cap);
        this.codiceFermata = Optional.ofNullable(codiceFermata);
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
        return this.indirizzo.toString();
    }

    public Optional<Integer> getCodiceFermata() {
        return codiceFermata;
    }

    public static final class DAO {
        public static Set<HubMobilita> list(Connection connection) {
            var query = "SELECT * FROM hub_mobilita";
            var hubs = new HashSet<HubMobilita>();
            try (
                    var statement = DAOUtils.prepare(connection, query);
                    var rs = statement.executeQuery();) {
                while (rs.next()) {
                    var codiceHub = rs.getInt("codice_hub");
                    var longitudine = rs.getString("longitudine");
                    var latitudine = rs.getString("latitudine");
                    var nome = rs.getString("nome");
                    var civico = rs.getString("indirizzo_civico");
                    var via = rs.getString("indirizzo_via");
                    var comune = rs.getString("indirizzo_comune");
                    var cap = rs.getInt("indirizzo_cap");
                    var codiceFermata = rs.getInt("codice_fermata");

                    hubs.add(new HubMobilitaImpl(codiceHub, longitudine, latitudine, nome, via, civico, comune, cap, codiceFermata));
                }
            } catch (Exception e) {
                throw new DAOException("Errore nell'estrazione degli hub di mobilità.", e);
            }
            return hubs;
        }

        public static void insert(Connection connection, HubMobilita hub) {
            var query = "INSERT INTO hub_mobilita (longitudine, latitudine, nome, indirizzo, codice_fermata) VALUES (?, ?, ?, ?, ?)";
            try (
                    var statement = DAOUtils.prepare(connection, query);) {
                statement.setString(1, hub.getLongitudine());
                statement.setString(2, hub.getLatitudine());
                statement.setString(3, hub.getNome());
                statement.setString(4, hub.getIndirizzo());
                statement.setInt(5, hub.getCodiceFermata().orElse(null));
                statement.executeUpdate();
            } catch (Exception e) {
                throw new DAOException("Errore nell'inserimento dell'hub di mobilità.", e);
            }
        }

        public static void delete(Connection connection, HubMobilita hub) {
            var query = "DELETE FROM hub_mobilita WHERE codice_hub = ?";
            try (
                    var statement = DAOUtils.prepare(connection, query);) {
                statement.setInt(1, hub.getCodiceHub());
                statement.executeUpdate();
            } catch (Exception e) {
                throw new DAOException("Errore nell'eliminazione dell'hub di mobilità.", e);
            }
        }
    }
}