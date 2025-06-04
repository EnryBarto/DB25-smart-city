package it.unibo.smartcity.model.impl;

public class Tratta {

    private final int arrivoCodiceFermata;
    private final int partenzaCodiceFermata;
    private final int tempoPercorrenza;

    public Tratta(int arrivoCodiceFermata, int partenzaCodiceFermata, int tempoPercorrenza) {
        this.arrivoCodiceFermata = arrivoCodiceFermata;
        this.partenzaCodiceFermata = partenzaCodiceFermata;
        this.tempoPercorrenza = tempoPercorrenza;
    }

    public int getArrivoCodiceFermata() {
        return arrivoCodiceFermata;
    }

    public int getPartenzaCodiceFermata() {
        return partenzaCodiceFermata;
    }

    public int getTempoPercorrenza() {
        return tempoPercorrenza;
    }
}