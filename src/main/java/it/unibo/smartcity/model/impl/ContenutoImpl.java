package it.unibo.smartcity.model.impl;

import static com.google.common.base.Preconditions.checkArgument;

import java.sql.Connection;
import java.util.LinkedList;
import java.util.List;
import com.google.common.base.Preconditions;

import it.unibo.smartcity.data.DAOException;
import it.unibo.smartcity.data.DAOUtils;
import it.unibo.smartcity.model.api.Contenuto;

public class ContenutoImpl implements Contenuto {

    private final int codiceHub;
    private final int codiceContenuto;
    private final int postiMax;
    private int postiDisponibili;

    public ContenutoImpl(int codiceHub, int codiceContenuto, int postiMax) {
        Preconditions.checkArgument(postiMax > 0, "I posti devono essere un numero positivo");

        this.codiceHub = codiceHub;
        this.codiceContenuto = codiceContenuto;
        this.postiMax = postiMax;
        this.postiDisponibili = postiMax;
    }

    @Override
    public int getCodiceHub() {
        return codiceHub;
    }

    @Override
    public int getCodiceContenuto() {
        return codiceContenuto;
    }

    @Override
    public int getPostiMax() {
        return postiMax;
    }

    @Override
    public int getPostiDisponibili() {
        return postiDisponibili;
    }

    @Override
    public void setPostiDisponibili(int postiDisponibili) {
        checkArgument(postiDisponibili >= 0 && postiDisponibili <= postiMax, "I posti disponibili devono essere tra 0 e il massimo consentito.");
        this.postiDisponibili = postiDisponibili;
    }

    @Override
    public void addPosto() {
        if (this.postiDisponibili < this.postiMax) {
            this.postiDisponibili++;
        }
    }

    @Override
    public void removePosto() {
        if (this.postiDisponibili > 0) {
            this.postiDisponibili--;
        }
    }

    public static final class DAO {

        public static List<Contenuto> list(Connection connection) {
            var query = "SELECT * FROM CONTENUTI";
            var contenuti = new LinkedList<Contenuto>();
            try (
                var statement = DAOUtils.prepare(connection, query);
                var rs = statement.executeQuery();
            ) {
                while (rs.next()) {
                    var contenuto = new ContenutoImpl(
                        rs.getInt("codice_hub"),
                        rs.getInt("codice_contenuto"),
                        rs.getInt("posti_max")
                    );
                    contenuti.add(contenuto);
                }
            } catch (Exception e) {
                throw new DAOException("Errore nell'estrazione dei contenuti degli hub.", e);
            }
            return contenuti;
        }

        public static void insert(Contenuto contenuto, Connection connection) {
            Preconditions.checkNotNull(contenuto, "Il contenuto non può essere null");
            var query = "INSERT INTO CONTENUTI (codice_hub, codice_contenuto, posti_max, posti_disponibili) VALUES (?, ?, ?, ?)";
            try (
                var statement = DAOUtils.prepare(connection, query);
            ) {
                statement.setInt(1, contenuto.getCodiceHub());
                statement.setInt(2, contenuto.getCodiceContenuto());
                statement.setInt(3, contenuto.getPostiMax());
                statement.setInt(4, contenuto.getPostiDisponibili());
                statement.executeUpdate();
            } catch (Exception e) {
                throw new DAOException("Errore nell'inserimento del contenuto:\n" + e.getMessage() + "\n (Hub: " + contenuto.getCodiceHub() + ", Contenuto: " + contenuto.getCodiceContenuto() + ")", e);
            }
        }

        public static void delete(Contenuto contenuto, Connection connection) {
            Preconditions.checkNotNull(contenuto, "Il contenuto non può essere null");
            var query = "DELETE FROM CONTENUTI WHERE codice_hub = ? AND codice_contenuto = ?";
            try (
                var statement = DAOUtils.prepare(connection, query);
            ) {
                statement.setInt(1, contenuto.getCodiceHub());
                statement.setInt(2, contenuto.getCodiceContenuto());
                statement.executeUpdate();
            } catch (Exception e) {
                throw new DAOException("Errore nell'eliminazione del contenuto.", e);
            }
        }
    }
}