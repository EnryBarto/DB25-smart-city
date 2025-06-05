package it.unibo.smartcity.model.impl;
import java.util.Date;

import it.unibo.smartcity.model.api.Convalida;

public record ConvalidaImpl (int codiceBiglietto, Date dataOra, int codiceCorsa) implements Convalida {

}