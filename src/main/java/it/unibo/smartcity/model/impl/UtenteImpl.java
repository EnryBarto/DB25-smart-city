package it.unibo.smartcity.model.impl;

import java.sql.Connection;
import java.util.List;

import com.google.common.base.Preconditions;

import it.unibo.smartcity.data.*;
import it.unibo.smartcity.model.api.Utente;

public class UtenteImpl extends PersonaImpl implements Utente {

    private final String username;
    private final String email;
    private final String telefono;
    private final String password;

    public UtenteImpl(String cognome, String nome, String documento, String codiceFiscale, String username,String email, String telefono, String password) {
        super(cognome, nome, documento, codiceFiscale);

        Preconditions.checkArgument(!username.isBlank());
        Preconditions.checkArgument(!email.isBlank());
        Preconditions.checkArgument(!telefono.isBlank());
        Preconditions.checkArgument(!password.isBlank());

        this.username = username;
        this.email = email;
        this.telefono = telefono;
        this.password = password;
    }

    public UtenteImpl(String cognome, String nome, String documento, String username, String email,
            String telefono, String password) {
        super(cognome, nome, documento);
        this.username = username;
        this.email = email;
        this.telefono = telefono;
        this.password = password;
    }

    @Override
    public String toString() {
        return Printer.stringify(
            "Utente",
            List.of(
                Printer.field("username", this.username),
                Printer.field("email", this.email),
                Printer.field("telefono", this.telefono)
            )
        );
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getTelefono() {
        return telefono;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public static final class DAO {

        public static Utente byUser(Connection connection, String username) {
            Utente utente = null;
            try (
                var statement = DAOUtils.prepare(connection, Queries.SELECT_UTENTE, username);
                var rs = statement.executeQuery();
            ) {
                while (rs.next()) {
                    utente = new UtenteImpl(
                        rs.getString("p.cognome"),
                        rs.getString("p.nome"),
                        rs.getString("p.documento"),
                        rs.getString("p.codice_fiscale"),
                        rs.getString("u.username"),
                        rs.getString("u.email"),
                        rs.getString("u.telefono"),
                        rs.getString("u.password")
                        );
                }
            } catch (Exception e) {
                throw new DAOException("Failed to list Utente", e);
            }
            return utente;
        }

        public static void insert(Connection connection, Utente utente) {
            Preconditions.checkNotNull(connection);
            Preconditions.checkNotNull(utente);

            try (
                var insertPerson = DAOUtils.prepare(
                    connection,
                    Queries.INSERT_PERSONA,
                    utente.getCognome(),
                    utente.getNome(),
                    utente.getDocumento()
                );

                var insertUser = DAOUtils.prepare(
                    connection,
                    Queries.INSERT_USER,
                    utente.getUsername(),
                    utente.getDocumento(),
                    utente.getEmail(),
                    utente.getTelefono(),
                    utente.getPassword()
                );
            ) {
                if (utente.getCodiceFiscale().isPresent()) insertPerson.setString(4, utente.getCodiceFiscale().get());
                else insertPerson.setNull(4, java.sql.Types.CHAR);

                if (PersonaImpl.DAO.byDocument(connection, utente.getDocumento()) == null) insertPerson.executeUpdate();
                insertUser.executeUpdate();
            } catch (Exception e) {
                throw new DAOException("Failed to insert Utente", e);
            }
        }
    }

}