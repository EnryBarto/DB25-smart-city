package it.unibo.smartcity.model.api;

public interface ContenutoHub {

    int getCodiceContenuto();

    int getCodiceHub();

    int getPostiMax();

    int getPostiDisponibili();

    String getDescrizione();

    void setPostiDisponibili(int postiDisponibili);

    void addPosto();

    void removePosto();

}