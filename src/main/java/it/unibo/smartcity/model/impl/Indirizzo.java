package it.unibo.smartcity.model.impl;

public record Indirizzo(String via, String civico, String comune, int cap) {

    @Override
    public String toString() {
        return this.via + ", n. " + this.civico + ", " + this.comune + ", " + this.cap;
    }

}
