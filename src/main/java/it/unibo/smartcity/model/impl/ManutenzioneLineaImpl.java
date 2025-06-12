package it.unibo.smartcity.model.impl;

import static com.google.common.base.Preconditions.checkNotNull;

import java.sql.Connection;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

import it.unibo.smartcity.data.DAOException;
import it.unibo.smartcity.data.DAOUtils;
import it.unibo.smartcity.data.Printer;
import it.unibo.smartcity.data.Queries;
import it.unibo.smartcity.model.api.ManutenzioneLinea;


public class ManutenzioneLineaImpl implements ManutenzioneLinea {

    public record ManutenzioneGravosa(String codiceLinea, String nome, int durata_lavoro, int numLineeSostitutive, int punteggio) {}

    private String codiceLinea;
    private Date dataInizio;
    private Date dataFine;
    private String nome;
    private String descrizione;
    private Optional<String> pIva;

    public ManutenzioneLineaImpl(String codiceLinea, Date dataInizio, Date dataFine, String nome, String descrizione,
            String pIva) {
        this.codiceLinea = codiceLinea;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
        this.nome = nome;
        this.descrizione = descrizione;
        this.pIva = Optional.ofNullable(pIva);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((codiceLinea == null) ? 0 : codiceLinea.hashCode());
        result = prime * result + ((dataInizio == null) ? 0 : dataInizio.hashCode());
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
        ManutenzioneLineaImpl other = (ManutenzioneLineaImpl) obj;
        if (codiceLinea == null) {
            if (other.codiceLinea != null)
                return false;
        } else if (!codiceLinea.equals(other.codiceLinea))
            return false;
        if (dataInizio == null) {
            if (other.dataInizio != null)
                return false;
        } else if (!dataInizio.equals(other.dataInizio))
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
            "Manutenzione Linea",
            List.of(
                Printer.field("codice_linea", this.codiceLinea),
                Printer.field("data_inizio", this.dataInizio),
                Printer.field("data_fine", this.dataFine),
                Printer.field("nome", this.nome),
                Printer.field("descrizione", this.descrizione),
                Printer.field("p_iva", this.pIva.orElse("N/A"))
            )
        );
    }

    @Override
    public String getCodiceLinea() {
        return codiceLinea;
    }

    @Override
    public Date getDataInizio() {
        return dataInizio;
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
    public String getPIva() {
        return pIva.orElse("N/A");
    }

    public static final class DAO {

        public static List<ManutenzioneLineaImpl> list(Connection connection) {
            var manutenzioniLinee = new ArrayList<ManutenzioneLineaImpl>();
            try (
                var statement = DAOUtils.prepare(connection, Queries.LIST_MANUTENZIONI_LINEE);
                var rs = statement.executeQuery();
            ) {
                while (rs.next()) {
                    var manutenzioneLinea = new ManutenzioneLineaImpl(
                        rs.getString("codice_linea"),
                        rs.getDate("data_inizio"),
                        rs.getDate("data_fine"),
                        rs.getString("nome"),
                        rs.getString("descrizione"),
                        rs.getString("p_iva")
                        );
                    manutenzioniLinee.add(manutenzioneLinea);
                }

            } catch (Exception e) {
                throw new DAOException("Failed to list Manutenzioni Linee", e);
            }
            return manutenzioniLinee;
        }

        public static void insert(Connection connection, ManutenzioneLineaImpl manutenzioneLinea) {
            checkNotNull(manutenzioneLinea);
            try (
                var statement = DAOUtils.prepare(connection, Queries.INSERT_MANUTENZIONI_LINEE,
                    manutenzioneLinea.getCodiceLinea(),
                    java.sql.Date.valueOf(manutenzioneLinea.getDataInizio().toString()),
                    java.sql.Date.valueOf(manutenzioneLinea.getDataFine().toString()),
                    manutenzioneLinea.getNome(),
                    manutenzioneLinea.getDescrizione(),
                    manutenzioneLinea.pIva.orElse(null)
                );
            ) {
                statement.executeUpdate();
            } catch (Exception e) {
                throw new DAOException("Failed to insert Manutenzioni Linee", e);
            }
        }

        public static void remove(Connection connection, String codiceLinea, Date dataInizio) {
            checkNotNull(codiceLinea);
            try (
                var statement = DAOUtils.prepare(connection, Queries.REMOVE_MANUT_LINEE, codiceLinea, dataInizio);
            ) {
                statement.executeUpdate();
            } catch (Exception e) {
                throw new DAOException("Failed to delete Manutenzioni Linee", e);
            }
        }

        public static List<ManutenzioneGravosa> estrazManutPiuGravose(Connection connection) {
            var manutenzioni = new ArrayList<ManutenzioneGravosa>();
            try (
                var statement = DAOUtils.prepare(connection, Queries.CINQUE_MANUT_PIU_GRAVOSE);
                var rs = statement.executeQuery();
            ) {
                while (rs.next()) {
                    var durLav= rs.getInt("durata_lavoro");
                    var numLinSost = rs.getInt("num_linee_sostitutive");
                    var punteggio = durLav % 3 + numLinSost * 5;
                    manutenzioni.add(new ManutenzioneGravosa(
                        rs.getString("codice_linea"),
                        rs.getString("nome"),
                        durLav,numLinSost, punteggio
                    ));
                }
            } catch (Exception e) {
                throw new DAOException("Errore nell'estrazione delle manutenzioni più gravose", e);
            }
            return manutenzioni;
        }
    }

}