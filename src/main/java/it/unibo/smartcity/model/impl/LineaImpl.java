package it.unibo.smartcity.model.impl;

import static com.google.common.base.Preconditions.checkArgument;

import java.sql.Connection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import it.unibo.smartcity.data.DAOException;
import it.unibo.smartcity.data.DAOUtils;
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
    }
}