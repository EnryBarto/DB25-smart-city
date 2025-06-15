package it.unibo.smartcity.model.impl;

import java.sql.Connection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import static com.google.common.base.Preconditions.checkArgument;

import it.unibo.smartcity.data.DAOException;
import it.unibo.smartcity.data.DAOUtils;
import it.unibo.smartcity.data.Queries;
import it.unibo.smartcity.model.api.Persona;

public class PersonaImpl implements Persona {

    private final String cognome;
    private final String nome;
    private final String documento;
    private final Optional<String> codiceFiscale;

    public PersonaImpl(final String cognome, final String nome, final String documento, final String codiceFiscale) {
        checkArgument(!cognome.isBlank());
        checkArgument(!nome.isBlank());
        checkArgument(!documento.isBlank());

        this.cognome = cognome;
        this.nome = nome;
        this.documento = documento;
        if (codiceFiscale != null && !codiceFiscale.isBlank()) this.codiceFiscale = Optional.of(codiceFiscale);
        else this.codiceFiscale = Optional.empty();
    }

    public PersonaImpl(final String cognome, final String nome, final String documento) {
        this(cognome,  nome, documento, "");
    }

    @Override
    public String getCognome() {
        return cognome;
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public String getDocumento() {
        return documento;
    }

    @Override
    public Optional<String> getCodiceFiscale() {
        return codiceFiscale;
    }

    public static final class DAO {

        public static List<Persona> list(Connection connection) {
            var persone = new LinkedList<Persona>();
            var query = "SELECT p.cognome, p.nome, p.documento, p.codice_fiscale FROM PERSONE p";
            try (
                var statement = DAOUtils.prepare(connection, query);
                var rs = statement.executeQuery();
            ) {
                while (rs.next()) {
                    var persona = new PersonaImpl(
                        rs.getString("p.cognome"),
                        rs.getString("p.nome"),
                        rs.getString("p.documento"),
                        rs.getString("p.codice_fiscale")
                    );
                    persone.add(persona);
                }
            } catch (Exception e) {
                throw new DAOException("Failed to select Personas", e);
            }
            return persone;
        }

        public static Persona byDocument(Connection connection, String document) {
            Persona persona = null;
            try (
                var statement = DAOUtils.prepare(connection, Queries.SELECT_PERSONA, document);
                var rs = statement.executeQuery();
            ) {
                while (rs.next()) {
                    persona = new PersonaImpl(
                        rs.getString("p.cognome"),
                        rs.getString("p.nome"),
                        rs.getString("p.documento"),
                        rs.getString("p.codice_fiscale")
                    );
                }
            } catch (Exception e) {
                throw new DAOException("Failed to select Persona", e);
            }
            return persona;
        }

        public static void insert(Connection connection, Persona persona) {
            var query = "INSERT INTO PERSONE (cognome, nome, documento, codice_fiscale) VALUES (?, ?, ?, ?)";
            try (
                var statement = DAOUtils.prepare(connection, query,
                persona.getCognome(),
                persona.getNome(),
                persona.getDocumento(),
                persona.getCodiceFiscale().orElse(null));
            ) {
                statement.executeUpdate();
            } catch (Exception e) {
                throw new DAOException("Failed to insert Persona", e);
            }
        }
    }

}