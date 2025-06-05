package it.unibo.smartcity.model.impl;

import java.sql.Connection;
import java.util.HashSet;
import java.util.Set;

import it.unibo.smartcity.data.DAOException;
import it.unibo.smartcity.data.DAOUtils;
import it.unibo.smartcity.model.api.TipologiaMezzo;

public class TipologiaMezzoImpl implements TipologiaMezzo  {

    private final int codiceTipoMezzo;
    private final String nome;

    public TipologiaMezzoImpl(int codiceTipoMezzo, String nome) {
        this.codiceTipoMezzo = codiceTipoMezzo;
        this.nome = nome;
    }

    @Override
    public int getCodiceTipoMezzo() {
        return codiceTipoMezzo;
    }

    @Override
    public String getNome() {
        return nome;
    }

    public static final class DAO {
        public static Set<TipologiaMezzo> list(Connection connection) {
            var query = "SELECT * FROM tipologie_mezzo";
            var tipologie = new HashSet<TipologiaMezzo>();
            try (
                var statement = DAOUtils.prepare(connection, query);
                var rs = statement.executeQuery();
            ) {
                while (rs.next()) {
                    var tipologia = new TipologiaMezzoImpl(
                        rs.getInt("codice_tipo_mezzo"),
                        rs.getString("nome")
                    );
                    tipologie.add(tipologia);
                }
            } catch (Exception e) {
                throw new DAOException("Errore nell'estrazione delle tipologie di mezzo.", e);
            }
            return tipologie;
        }
    }

}