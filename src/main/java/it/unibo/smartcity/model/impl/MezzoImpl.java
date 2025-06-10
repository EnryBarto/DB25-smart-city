package it.unibo.smartcity.model.impl;

import java.sql.Connection;
import java.util.ArrayList;

import it.unibo.smartcity.data.DAOException;
import it.unibo.smartcity.data.DAOUtils;
import it.unibo.smartcity.data.Queries;
import it.unibo.smartcity.model.api.Mezzo;

public class MezzoImpl implements Mezzo {

    public record MezzoConNome(String nImmatricolazione, String nomeMezzo) {}

    private String nImmatricolazione;
    private int codiceTipoMezzo;

    public MezzoImpl(String nImmatricolazione, int codiceTipoMezzo) {
        this.nImmatricolazione = nImmatricolazione;
        this.codiceTipoMezzo = codiceTipoMezzo;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((nImmatricolazione == null) ? 0 : nImmatricolazione.hashCode());
        result = prime * result + codiceTipoMezzo;
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
        MezzoImpl other = (MezzoImpl) obj;
        if (nImmatricolazione == null) {
            if (other.nImmatricolazione != null)
                return false;
        } else if (!nImmatricolazione.equals(other.nImmatricolazione))
            return false;
        if (codiceTipoMezzo != other.codiceTipoMezzo)
            return false;
        return true;
    }

    @Override
    public String getnImmatricolazione() {
        return nImmatricolazione;
    }

    @Override
    public int getCodiceTipoMezzo() {
        return codiceTipoMezzo;
    }

    public static final class DAO {

        public static ArrayList<MezzoConNome> list(Connection connection) {
            var mezzi = new ArrayList<MezzoConNome>();
            try (
                var statement = DAOUtils.prepare(connection, Queries.LIST_MEZZI);
                var rs = statement.executeQuery();
            ) {
                while (rs.next()) {
                    var mezzo = new MezzoConNome(
                        rs.getString("n_immatricolazione"),
                        rs.getString("nome")
                        );
                    mezzi.add(mezzo);
                }

            } catch (Exception e) {
                throw new DAOException("Failed to list Mezzi", e);
            }
            return mezzi;
        }
    }

}