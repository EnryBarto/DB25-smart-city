package it.unibo.smartcity.model.impl;

import java.sql.Connection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

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

    public HubMobilitaImpl(int codiceHub, String latitudine, String longitudine, String nome, String via,
            String civico, String comune, int cap, Integer codiceFermata) {
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
    public Indirizzo getIndirizzo() {
        return indirizzo;
    }

    public Optional<Integer> getCodiceFermata() {
        return codiceFermata;
    }

    public static final class DAO {
        public static List<HubMobilita> list(Connection connection) {
            var query = "SELECT * FROM HUB_MOBILITA ORDER BY codice_hub";
            var hubs = new LinkedList<HubMobilita>();
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

                    hubs.add(new HubMobilitaImpl(codiceHub, latitudine, longitudine, nome, via, civico, comune, cap, codiceFermata));
                }
            } catch (Exception e) {
                throw new DAOException("Errore nell'estrazione degli hub di mobilità.", e);
            }
            return hubs;
        }

        public static void insert(Connection connection, HubMobilita hub) {
            if (hub.getCodiceFermata().isPresent()) {
                    var query = "INSERT INTO HUB_MOBILITA (longitudine, latitudine, nome, indirizzo_via, indirizzo_comune, indirizzo_cap, indirizzo_civico, codice_fermata) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                    try (
                            var statement = DAOUtils.prepare(connection, query);) {
                        statement.setString(1, hub.getLongitudine());
                        statement.setString(2, hub.getLatitudine());
                        statement.setString(3, hub.getNome());
                        statement.setString(4, hub.getIndirizzo().via());
                        statement.setString(5, hub.getIndirizzo().comune());
                        statement.setInt(6, hub.getIndirizzo().cap());
                        statement.setString(7, hub.getIndirizzo().civico());
                        statement.setInt(8, hub.getCodiceFermata().get());
                        statement.executeUpdate();
                    } catch (Exception e) {
                        throw new DAOException("Errore nell'inserimento dell'hub di mobilità." + e.getMessage(), e);
                    }
            } else {
                var query = "INSERT INTO HUB_MOBILITA (longitudine, latitudine, nome, indirizzo_via, indirizzo_comune, indirizzo_cap, indirizzo_civico) VALUES (?, ?, ?, ?, ?, ?, ?)";
                try (
                        var statement = DAOUtils.prepare(connection, query);) {
                    statement.setString(1, hub.getLongitudine());
                    statement.setString(2, hub.getLatitudine());
                    statement.setString(3, hub.getNome());
                    statement.setString(4, hub.getIndirizzo().via());
                    statement.setString(5, hub.getIndirizzo().comune());
                    statement.setInt(6, hub.getIndirizzo().cap());
                    statement.setString(7, hub.getIndirizzo().civico());
                    statement.executeUpdate();
                } catch (Exception e) {
                    throw new DAOException("Errore nell'inserimento dell'hub di mobilità." + e.getMessage(), e);
                }
            }
        }

        public static void delete(Connection connection, HubMobilita hub) {
            var query = "DELETE FROM HUB_MOBILITA WHERE codice_hub = ?";
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