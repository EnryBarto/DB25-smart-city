package it.unibo.smartcity.model.impl;

import java.sql.Connection;
import java.util.LinkedList;
import java.util.List;

import it.unibo.smartcity.data.DAOException;
import it.unibo.smartcity.data.DAOUtils;
import it.unibo.smartcity.model.api.Fermata;


public class FermataImpl implements Fermata {

    private final int codiceFermata;
    private final String nome;
    private final Indirizzo indirizzo;
    private final String longitudine;
    private final String latitudine;

    public FermataImpl(int codiceFermata, String nome, String via, String civico, String comune, int cap, String longitudine, String latitudine) {
        this.codiceFermata = codiceFermata;
        this.nome = nome;
        this.indirizzo = new Indirizzo(via, civico, comune, cap);
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
    public Indirizzo getIndirizzo() {
        return this.indirizzo;
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
        public static List<Fermata> list(Connection connection) {
            var query = "SELECT * FROM FERMATE ORDER BY codice_fermata";
            var fermate = new LinkedList<Fermata>();
            try (
                var statement = DAOUtils.prepare(connection, query);
                var rs = statement.executeQuery();
            ) {
                while (rs.next()) {
                    var fermata = new FermataImpl(
                        rs.getInt("codice_fermata"),
                        rs.getString("nome"),
                        rs.getString("indirizzo_via"),
                        rs.getString("indirizzo_civico"),
                        rs.getString("indirizzo_comune"),
                        rs.getInt("indirizzo_cap"),
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
            var query = "INSERT INTO FERMATE (nome, indirizzo_via, indirizzo_civico, indirizzo_comune, indirizzo_cap, longitudine, latitudine) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (
                var statement = DAOUtils.prepare(connection, query);
            ) {
                statement.setString(1, fermata.getNome());
                statement.setString(2, fermata.getIndirizzo().via());
                statement.setString(3, fermata.getIndirizzo().civico());
                statement.setString(4, fermata.getIndirizzo().comune());
                statement.setInt(5, fermata.getIndirizzo().cap());
                statement.setString(6, fermata.getLongitudine());
                statement.setString(7, fermata.getLatitudine());
                statement.executeUpdate();
            } catch (Exception e) {
                throw new DAOException("Errore nell'inserimento della fermata." + e.getMessage(), e);
            }
        }

        public static void delete(Connection connection, int codiceFermata) {
            var query = "DELETE FROM FERMATE WHERE codice_fermata = ?";
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