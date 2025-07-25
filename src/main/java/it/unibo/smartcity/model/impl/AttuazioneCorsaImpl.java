package it.unibo.smartcity.model.impl;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import com.google.common.base.Preconditions;

import it.unibo.smartcity.data.*;
import it.unibo.smartcity.model.api.AttuazioneCorsa;
import it.unibo.smartcity.model.api.Dipendente;
import it.unibo.smartcity.model.api.OrarioLinea;

public class AttuazioneCorsaImpl implements AttuazioneCorsa {

    private final int codiceCorsa;
    private final Date data;
    private final int codiceOrario;
    private final String nImmatricolazione;
    private final String username;

    public AttuazioneCorsaImpl(int codiceCorsa, Date data, int codiceOrario, String nImmatricolazione, String username) {
        Preconditions.checkNotNull(codiceCorsa);
        Preconditions.checkNotNull(data);

        this.codiceCorsa = codiceCorsa;
        this.data = data;
        this.codiceOrario = codiceOrario;
        this.nImmatricolazione = nImmatricolazione;
        this.username = username;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + codiceCorsa;
        result = prime * result + ((data == null) ? 0 : data.hashCode());
        result = prime * result + codiceOrario;
        result = prime * result + ((nImmatricolazione == null) ? 0 : nImmatricolazione.hashCode());
        result = prime * result + ((username == null) ? 0 : username.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AttuazioneCorsaImpl other = (AttuazioneCorsaImpl) obj;
        if (codiceCorsa != other.codiceCorsa)
            return false;
        if (data == null) {
            if (other.data != null)
                return false;
        } else if (!data.equals(other.data))
            return false;
        if (codiceOrario != other.codiceOrario)
            return false;
        if (nImmatricolazione == null) {
            if (other.nImmatricolazione != null)
                return false;
        } else if (!nImmatricolazione.equals(other.nImmatricolazione))
            return false;
        if (username == null) {
            if (other.username != null)
                return false;
        } else if (!username.equals(other.username))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return Printer.stringify(
            "Attuazione_corsa",
            List.of(
                Printer.field("codice_corsa", this.codiceCorsa),
                Printer.field("data", this.data),
                Printer.field("codice_orario", this.codiceOrario),
                Printer.field("n_immatricolazione", this.nImmatricolazione),
                Printer.field("username", this.username)
            )
        );
    }

    @Override
    public int getCodiceCorsa() {
        return codiceCorsa;
    }

    @Override
    public Date getData() {
        return data;
    }

    @Override
    public int getCodiceOrario() {
        return codiceOrario;
    }

    @Override
    public String getnImmatricolazione() {
        return nImmatricolazione;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public static final class DAO {

        public static void insert(Connection connection, LocalDate data, OrarioLinea orario, Dipendente dipendente, String codMezzo) {
            var query = "INSERT INTO ATTUAZIONI_CORSE (data, codice_orario, n_immatricolazione, username) VALUES (?, ?, ?, ?)";
            try (
                var statement = DAOUtils.prepare(connection, query);
            ) {
                statement.setString(1, data.toString());
                statement.setInt(2, orario.getCodiceOrario());
                statement.setString(3, codMezzo);
                statement.setString(4, dipendente.getUsername());
                statement.executeUpdate();
            } catch (Exception e) {
                throw new DAOException("Errore nell'inserimento dell'attuazione corsa:\n" + e.getMessage(), e);
            }
        }
    }
}