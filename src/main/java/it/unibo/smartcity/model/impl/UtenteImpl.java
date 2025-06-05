package it.unibo.smartcity.model.impl;

import java.sql.Connection;
import java.util.ArrayList;
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

        public static ArrayList<UtenteImpl> list(Connection connection) {
            var utenti = new ArrayList<UtenteImpl>();
            try (
                var statement = DAOUtils.prepare(connection, Queries.SELECT_UTENTE);
                var rs = statement.executeQuery();
            ) {
                while (rs.next()) {
                    var utente = new UtenteImpl(
                        rs.getString("cognome"),
                        rs.getString("nome"),
                        rs.getString("documento"),
                        rs.getString("codice_fiscale"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("telefono"),
                        rs.getString("password")
                        );
                    utenti.add(utente);
                }

            } catch (Exception e) {
                throw new DAOException("Failed to list Utente", e);
            }
            return utenti;
        }

        public static void insert(Connection connection, UtenteImpl utente) {
            Preconditions.checkNotNull(connection);
            Preconditions.checkNotNull(utente);

            try (
                var statement = DAOUtils.prepare(
                    connection,
                    Queries.INSERT_USE,
                    utente.getNome(),
                    utente.getCognome(),
                    utente.getDocumento(),
                    utente.getCodiceFiscale(),
                    utente.getTelefono(),
                    utente.getEmail(),
                    utente.getUsername(),
                    utente.getPassword()
                );
            ) {
                statement.executeUpdate();
            } catch (Exception e) {
                throw new DAOException("Failed to insert Utente", e);
            }
        }
    }

}