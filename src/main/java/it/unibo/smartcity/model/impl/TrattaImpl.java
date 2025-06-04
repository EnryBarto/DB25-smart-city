package it.unibo.smartcity.model.impl;

import it.unibo.smartcity.model.api.Tratta;

public class TrattaImpl implements Tratta {

    private final int arrivoCodiceFermata;
    private final int partenzaCodiceFermata;
    private final int tempoPercorrenza;

    public TrattaImpl(int arrivoCodiceFermata, int partenzaCodiceFermata, int tempoPercorrenza) {
        this.arrivoCodiceFermata = arrivoCodiceFermata;
        this.partenzaCodiceFermata = partenzaCodiceFermata;
        this.tempoPercorrenza = tempoPercorrenza;
    }

    @Override
    public int getArrivoCodiceFermata() {
        return arrivoCodiceFermata;
    }

    @Override
    public int getPartenzaCodiceFermata() {
        return partenzaCodiceFermata;
    }

    @Override
    public int getTempoPercorrenza() {
        return tempoPercorrenza;
    }
}