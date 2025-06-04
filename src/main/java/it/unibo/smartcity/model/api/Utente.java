package it.unibo.smartcity.model.api;

public interface Utente extends Persona{

    String getUsername();

    String getDocumento();

    String getEmail();

    String getTelefono();

    String getPassword();

}