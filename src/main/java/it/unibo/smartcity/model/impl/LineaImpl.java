package it.unibo.smartcity.model.impl;

import static com.google.common.base.Preconditions.checkArgument;

import java.sql.Connection;
import java.util.ArrayList;
import java.sql.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import it.unibo.smartcity.data.DAOException;
import it.unibo.smartcity.data.DAOUtils;
import it.unibo.smartcity.data.Queries;
import it.unibo.smartcity.model.api.Linea;

public class LineaImpl implements Linea {

    private final String codiceLinea;
    private final int tempoPercorrenza;
    private final Optional<Date> inizioValidita;
    private Optional<Date> fineValidita;
    private boolean attiva;
    private final int codiceTipoMezzo;

    public LineaImpl(String codiceLinea, int tempoPercorrenza, Date inizioValidita, Date fineValidita, boolean attiva,
            int codiceTipoMezzo) {
        this.codiceLinea = codiceLinea;
        this.tempoPercorrenza = tempoPercorrenza;
        this.inizioValidita = inizioValidita == null ? Optional.empty() : Optional.of(inizioValidita);
        this.fineValidita = fineValidita == null ? Optional.empty() : Optional.of(fineValidita);
        this.attiva = attiva;
        this.codiceTipoMezzo = codiceTipoMezzo;
    }

    @Override
    public String getCodiceLinea() {
        return codiceLinea;
    }

    @Override
    public int getTempoPercorrenza() {
        return tempoPercorrenza;
    }

    @Override
    public Optional<Date> getInizioValidita() {
        return inizioValidita;
    }

    @Override
    public Optional<Date> getFineValidita() {
        return fineValidita;
    }

    @Override
    public boolean getAttiva() {
        return attiva;
    }

    @Override
    public int getCodiceTipoMezzo() {
        return codiceTipoMezzo;
    }

    @Override
    public void setAttiva(boolean attiva) {
        this.attiva = attiva;
    }

    @Override
    public void setFineValidita(Date fineValidita) {
        checkArgument(fineValidita != null, "La data di fine validità non può essere null");
        this.fineValidita.ifPresent(date -> checkArgument(date.before(fineValidita),
                "La data di fine validità deve essere successiva alla data attuale"));
        this.fineValidita = Optional.of(fineValidita);
    }

    public static final class DAO {
        public static List<Linea> list(Connection connection) {
            var query = "SELECT * FROM linee ORDER BY codice_linea";
            var lines = new LinkedList<Linea>();
            try (
                var statement = DAOUtils.prepare(connection, query);
                var rs = statement.executeQuery();
            ) {
                while (rs.next()) {
                    var linea = new LineaImpl(
                        rs.getString("codice_linea"),
                        rs.getInt("tempo_percorrenza"),
                        rs.getDate("inizio_validita"),
                        rs.getDate("fine_validita"),
                        rs.getBoolean("attiva"),
                        rs.getInt("codice_tipo_mezzo")
                    );
                    lines.add(linea);
                }
            } catch (Exception e) {
                throw new DAOException("Errore nell'estrazione delle linee.", e);
            }
            return lines;
        }

        public static Map<Date, OrarioLineaImpl> listOrari(Connection connection, String codiceLinea) {
            var orari = new HashMap<Date, OrarioLineaImpl>();
            try (
                var statement = DAOUtils.prepare(connection, Queries.LIST_ORARI_UNA_LINEA, codiceLinea);
                var rs = statement.executeQuery();
            ) {
                while (rs.next()) {
                    var orarioLinea = new OrarioLineaImpl(
                        rs.getInt("codice_orario"),
                        rs.getString("ora_partenza"),
                        rs.getString("giorno_settimanale"),
                        rs.getString("codice_linea")
                    );
                    var data = rs.getDate("data");
                    orari.put(data, orarioLinea);
                }
            } catch (Exception e) {
                throw new DAOException("Errore nell'estrazione degli orari delle linee.", e);
            }
            return orari;
        }

        public static List<FermataLinea> listFermate(Connection connection, String codiceLinea) {
            var fermateLinea = new ArrayList<FermataLinea>();
            try (
                var statement = DAOUtils.prepare(connection, Queries.LIST_FERMATE_UNA_LINEA, codiceLinea);
                var rs = statement.executeQuery();
            ) {
                if (rs.next()) {
                    var fermataLinea = new FermataLinea(
                        rs.getString("codice_linea"),
                        rs.getInt("codice_fermata_arrivo"),
                        rs.getInt("codice_fermata_partenza"),
                        rs.getInt("ordine"),
                        rs.getString("nome"),
                        rs.getString("via"),
                        rs.getInt("tempo_percorrenza")
                    );
                    fermateLinea.add(fermataLinea);
                }
            } catch (Exception e) {
                throw new DAOException("Errore nell'estrazione delle fermate della linea.", e);
            }
            return fermateLinea;
        }

        public static Map<String, Integer> getPiuConvalide(Connection connection, String codiceLinea) {
            var linee = new HashMap<String, Integer>();
            try (
                var statement = DAOUtils.prepare(connection, Queries.ESTRAZ_LINEE_PIU_CONVALIDE, codiceLinea);
                var rs = statement.executeQuery();
            ) {
                if (rs.next()) {
                    var numConvalide = rs.getInt("numero_convalide");
                    linee.put(codiceLinea, numConvalide);
                }
            } catch (Exception e) {
                throw new DAOException("Errore nell'estrazione delle linee con più convalide.", e);
            }
            return linee;
        }

        public static int getIncassi(Connection connection, String codiceLinea) {
            var incassi = 0;
            try (
                var statement = DAOUtils.prepare(connection, Queries.LIST_INCASSI, codiceLinea);
                var rs = statement.executeQuery();
            ) {
                if (rs.next()) {
                    incassi = rs.getInt("incassi");
                }
            } catch (Exception e) {
                throw new DAOException("Errore nell'estrazione degli incassi relativi alla linea.", e);
            }
            return incassi;
        }

        public static void insertOrdinaria(Connection connection, Linea linea) {
            var query = "INSERT INTO linee (tempo_percorrenza, attiva, codice_tipo_mezzo) VALUES (?, ?, ?)";
            try (
                var statement = DAOUtils.prepare(connection, query);
            ) {
                statement.setInt(1, 0);
                statement.setBoolean(2, linea.getAttiva());
                statement.setInt(3, linea.getCodiceTipoMezzo());
                statement.executeUpdate();
            } catch (Exception e) {
                throw new DAOException("Errore nell'inserimento della linea.", e);
            }
        }

        public static void insertStraordinaria(Connection connection, Linea linea) {
            var query = "INSERT INTO linee (tempo_percorrenza, inizio_validita, fine_validita, codice_tipo_mezzo) VALUES (?, ?, ?, ?)";
            try (
                var statement = DAOUtils.prepare(connection, query);
            ) {
                statement.setInt(1, linea.getTempoPercorrenza());
                statement.setDate(2, linea.getInizioValidita().orElse(null));
                statement.setDate(3, linea.getFineValidita().orElse(null));
                statement.setInt(4, linea.getCodiceTipoMezzo());
                statement.executeUpdate();
            } catch (Exception e) {
                throw new DAOException("Errore nell'inserimento della linea.", e);
            }
        }

        public static void delete(Connection connection, String codiceLinea) {
            var query = "DELETE FROM linee WHERE codice_linea = ?";
            try (
                var statement = DAOUtils.prepare(connection, query);
            ) {
                statement.setString(1, codiceLinea);
                statement.executeUpdate();
            } catch (Exception e) {
                throw new DAOException("Errore nell'eliminazione della linea.", e);
            }
        }
    }
}