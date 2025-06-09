package it.unibo.smartcity.model.impl;

import java.sql.Connection;
import java.util.HashSet;
import java.util.Set;

import it.unibo.smartcity.data.DAOException;
import it.unibo.smartcity.data.DAOUtils;
import it.unibo.smartcity.model.api.Fermata;


public class FermataImpl implements Fermata {

    private final int codiceFermata;
    private final String nome;
    private final String via;
    private final int cap;
    private final String longitudine;
    private final String latitudine;

    public FermataImpl(int codiceFermata, String nome, String via, int cap, String longitudine, String latitudine) {
        this.codiceFermata = codiceFermata;
        this.nome = nome;
        this.via = via;
        this.cap = cap;
        this.longitudine = longitudine;
        this.latitudine = latitudine;
    }
    @Override
    public int getCodiceFermata() {
        return codiceFermata;
    }
    @Override
    public String getNome() {
        return nome;
    }
    @Override
    public String getVia() {
        return via;
    }
    @Override
    public int getCap() {
        return cap;
    }
    @Override
    public String getLongitudine() {
        return longitudine;
    }
    @Override
    public String getLatitudine() {
        return latitudine;
    }

    public static final class DAO {
        public static Set<Fermata> list(Connection connection) {
            var query = "SELECT * FROM fermate";
            var fermate = new HashSet<Fermata>();
            try (
                var statement = DAOUtils.prepare(connection, query);
                var rs = statement.executeQuery();
            ) {
                while (rs.next()) {
                    var fermata = new FermataImpl(
                        rs.getInt("codice_fermata"),
                        rs.getString("nome"),
                        rs.getString("via"),
                        rs.getInt("CAP"),
                        rs.getString("longitudine"),
                        rs.getString("latitudine")
                    );
                    fermate.add(fermata);
                }
            } catch (Exception e) {
                throw new DAOException("Errore nell'estrazione delle fermate.", e);
            }
            return fermate;
        }

        public static void insert(Connection connection, Fermata fermata) {
            var query = "INSERT INTO fermate (nome, via, CAP, longitudine, latitudine) VALUES (?, ?, ?, ?, ?)";
            try (
                var statement = DAOUtils.prepare(connection, query);
            ) {
                statement.setString(1, fermata.getNome());
                statement.setString(2, fermata.getVia());
                statement.setInt(3, fermata.getCap());
                statement.setString(4, fermata.getLongitudine());
                statement.setString(5, fermata.getLatitudine());
                statement.executeUpdate();
            } catch (Exception e) {
                throw new DAOException("Errore nell'inserimento della fermata.", e);
            }
        }

        public static void delete(Connection connection, int codiceFermata) {
            var query = "DELETE FROM fermate WHERE codice_fermata = ?";
            try (
                var statement = DAOUtils.prepare(connection, query);
            ) {
                statement.setInt(1, codiceFermata);
                statement.executeUpdate();
            } catch (Exception e) {
                throw new DAOException("Errore nell'eliminazione della fermata.", e);
            }
        }
    }
}