package it.unibo.smartcity.model.api;

public interface Contenuto {

    int getCodiceHub();

    int getCodiceContenuto();

    int getPostiMax();

    int getPostiDisponibili();

    void setPostiDisponibili(int postiDisponibili);

    void addPosto();

    void removePosto();

}