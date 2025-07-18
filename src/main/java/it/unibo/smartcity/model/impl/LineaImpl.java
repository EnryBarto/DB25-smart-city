package it.unibo.smartcity.model.impl;

import static com.google.common.base.Preconditions.checkArgument;

import java.sql.Connection;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.HashMap;
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
            var query = "SELECT * FROM LINEE WHERE attiva IS TRUE ORDER BY codice_linea";
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

        public static List<Linea> listExtraordinary(Connection connection) {
            var query = "SELECT * FROM LINEE WHERE attiva IS NULL ORDER BY codice_linea";
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
                        false,
                        rs.getInt("codice_tipo_mezzo")
                    );
                    lines.add(linea);
                }
            } catch (Exception e) {
                throw new DAOException("Errore nell'estrazione delle linee straordinarie.", e);
            }
            return lines;
        }

        public static List<Linea> listOrdinary(Connection connection) {
            var query = "SELECT * FROM LINEE WHERE fine_validita IS NULL ORDER BY codice_linea";
            var lines = new LinkedList<Linea>();
            try (
                var statement = DAOUtils.prepare(connection, query);
                var rs = statement.executeQuery();
            ) {
                while (rs.next()) {
                    var linea = new LineaImpl(
                        rs.getString("codice_linea"),
                        rs.getInt("tempo_percorrenza"),
                        null,
                        null,
                        rs.getBoolean("attiva"),
                        rs.getInt("codice_tipo_mezzo")
                    );
                    lines.add(linea);
                }
            } catch (Exception e) {
                throw new DAOException("Errore nell'estrazione delle linee straordinarie.", e);
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
            var fermateLinea = new LinkedList<FermataLinea>();
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
            var query = "INSERT INTO LINEE (codice_linea, tempo_percorrenza, attiva, codice_tipo_mezzo) VALUES (?, ?, ?, ?)";
            try (
                var statement = DAOUtils.prepare(connection, query);
            ) {
                statement.setString(1, linea.getCodiceLinea());
                statement.setInt(2, 0);
                statement.setBoolean(3, linea.getAttiva());
                statement.setInt(4, linea.getCodiceTipoMezzo());
                statement.executeUpdate();
            } catch (Exception e) {
                throw new DAOException("Errore nell'inserimento della linea.", e);
            }
        }

        public static void insertStraordinaria(Connection connection, Linea linea) {
            var query = "INSERT INTO LINEE (codice_linea, tempo_percorrenza, inizio_validita, fine_validita, codice_tipo_mezzo) VALUES (?, ?, ?, ?, ?)";
            try (
                var statement = DAOUtils.prepare(connection, query);
            ) {
                statement.setString(1, linea.getCodiceLinea());
                statement.setInt(2, 0);
                statement.setDate(3, linea.getInizioValidita().orElse(null));
                statement.setDate(4, linea.getFineValidita().orElse(null));
                statement.setInt(5, linea.getCodiceTipoMezzo());
                statement.executeUpdate();
            } catch (Exception e) {
                throw new DAOException("Errore nell'inserimento della linea.", e);
            }
        }

        public static void delete(Connection connection, String codiceLinea) {
            var query = "DELETE FROM LINEE WHERE codice_linea = ?";
            try (
                var statement = DAOUtils.prepare(connection, query);
            ) {
                statement.setString(1, codiceLinea);
                statement.executeUpdate();
            } catch (Exception e) {
                throw new DAOException("Errore nell'eliminazione della linea.", e);
            }
        }

        public static int getTempoPercorrenza(Connection connection, String codiceLinea) {
            var tempoPercorrenza = 0;
            try (
                var statement = DAOUtils.prepare(connection, Queries.SELECT_TEMPO_PERCORRENZA, codiceLinea);
                var rs = statement.executeQuery();
            ) {
                if (rs.next()) {
                    tempoPercorrenza = rs.getInt(1);
                }
            } catch (Exception e) {
                throw new DAOException(e.getMessage(), e);
            }
            return tempoPercorrenza;
        }

        public static void updateTempoPercorrenza(Connection connection, String codLinea, int tempoPercorrenza) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.UPDATE_TEMPO_PERCORRENZA, tempoPercorrenza, codLinea);
            ) {
                statement.executeUpdate();
            } catch (Exception e) {
                throw new DAOException(e.getMessage(), e);
            }
        }

        public static List<Linea> listAttiveByDate(Connection connection, LocalDate data) {
            var lines = new LinkedList<Linea>();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String dataFormattata = data.format(formatter);

            try (
                var statement = DAOUtils.prepare(connection, Queries.LIST_LINEE_ATTIVE_BY_DATE, dataFormattata);
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
                throw new DAOException("Errore nell'estrazione delle linee:\n" + e.getMessage(), e);
            }
            return lines;
        }

        public static List<Linea> listLineeNonAttive (Connection connection) {
            var linee = new LinkedList<Linea>();
            try (
                var statement = DAOUtils.prepare(connection, Queries.LIST_LINEE_NON_ATTIVE);
                var rs = statement.executeQuery();
            ) {
                while (rs.next()) {
                    var linea = new LineaImpl(
                        rs.getString("codice_linea"),
                        rs.getInt("tempo_percorrenza"),
                        rs.getDate("inizio_validita"),
                        rs.getDate("inizio_validita"),
                        rs.getBoolean("attiva"),
                        rs.getInt("codice_tipo_mezzo")
                    );
                    linee.add(linea);
                }
            } catch (Exception e) {
                throw new DAOException("Errore nell'estrazione delle linee non attive", e);
            }
            return linee;
        }
    }
}