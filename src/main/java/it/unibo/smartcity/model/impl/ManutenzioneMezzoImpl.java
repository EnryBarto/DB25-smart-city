package it.unibo.smartcity.model.impl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import it.unibo.smartcity.data.DAOException;
import it.unibo.smartcity.data.DAOUtils;
import it.unibo.smartcity.data.Printer;
import it.unibo.smartcity.data.Queries;
import it.unibo.smartcity.model.api.ManutenzioneMezzo;

public class ManutenzioneMezzoImpl implements ManutenzioneMezzo {

    private String nImmatricolazione;
    private Date dataInzio;
    private Date dataFine;
    private String nome;
    private String descrizione;
    private Optional<String> pIva;

    public ManutenzioneMezzoImpl(String nImmatricolazione, Date dataInzio, Date dataFine, String nome,
            String descrizione, Optional<String> pIva) {
        this.nImmatricolazione = nImmatricolazione;
        this.dataInzio = dataInzio;
        this.dataFine = dataFine;
        this.nome = nome;
        this.descrizione = descrizione;
        this.pIva = pIva;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((nImmatricolazione == null) ? 0 : nImmatricolazione.hashCode());
        result = prime * result + ((dataInzio == null) ? 0 : dataInzio.hashCode());
        result = prime * result + ((dataFine == null) ? 0 : dataFine.hashCode());
        result = prime * result + ((nome == null) ? 0 : nome.hashCode());
        result = prime * result + ((descrizione == null) ? 0 : descrizione.hashCode());
        result = prime * result + ((pIva == null) ? 0 : pIva.hashCode());
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
        ManutenzioneMezzoImpl other = (ManutenzioneMezzoImpl) obj;
        if (nImmatricolazione == null) {
            if (other.nImmatricolazione != null)
                return false;
        } else if (!nImmatricolazione.equals(other.nImmatricolazione))
            return false;
        if (dataInzio == null) {
            if (other.dataInzio != null)
                return false;
        } else if (!dataInzio.equals(other.dataInzio))
            return false;
        if (dataFine == null) {
            if (other.dataFine != null)
                return false;
        } else if (!dataFine.equals(other.dataFine))
            return false;
        if (nome == null) {
            if (other.nome != null)
                return false;
        } else if (!nome.equals(other.nome))
            return false;
        if (descrizione == null) {
            if (other.descrizione != null)
                return false;
        } else if (!descrizione.equals(other.descrizione))
            return false;
        if (pIva == null) {
            if (other.pIva != null)
                return false;
        } else if (!pIva.equals(other.pIva))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return Printer.stringify(
            "Manutenzione Mezzo",
            List.of(
                Printer.field("n_immatricolazione", this.nImmatricolazione),
                Printer.field("data_inizio", this.dataInzio),
                Printer.field("data_fine", this.dataFine),
                Printer.field("nome", this.nome),
                Printer.field("descrizione", this.descrizione),
                Printer.field("p_iva", this.pIva.get())
            )
        );
    }

    @Override
    public String getnImmatricolazione() {
        return nImmatricolazione;
    }

    @Override
    public Date getDataInzio() {
        return dataInzio;
    }

    @Override
    public Date getDataFine() {
        return dataFine;
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public String getDescrizione() {
        return descrizione;
    }

    @Override
    public String getpIva() {
        return pIva.get();
    }

    public static final class DAO {

        public static ArrayList<ManutenzioneMezzoImpl> list(Connection connection) {
            var manutenzioniMezzi = new ArrayList<ManutenzioneMezzoImpl>();
            try (
                var statement = DAOUtils.prepare(connection, Queries.LIST_MANUTENZIONI_MEZZI);
                var rs = statement.executeQuery();
            ) {
                while (rs.next()) {
                    var manutenzioneMezzo = new ManutenzioneMezzoImpl(
                        rs.getString("n_immatricolazione"),
                        rs.getDate("data_inizio"),
                        rs.getDate("data_fine"),
                        rs.getString("nome"),
                        rs.getString("descrizione"),
                        Optional.of(rs.getString("p_iva"))
                        );
                    manutenzioniMezzi.add(manutenzioneMezzo);
                }

            } catch (Exception e) {
                throw new DAOException("Failed to list Manutenzioni Mezzi", e);
            }
            return manutenzioniMezzi;
        }
    }
}