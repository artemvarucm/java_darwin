package es.ucm.factories;

import es.ucm.individuos.Individuo;
import es.ucm.individuos.Individuo1;


public class Individuo1Factory extends IndividuoFactory {
    protected double precision;
    public Individuo1Factory(double precision) {
        this.precision = precision;
    }

    public Individuo createOne() {
        return new Individuo1(precision);
    }
}
