package es.ucm.factories;

import es.ucm.individuos.Individuo;
import es.ucm.individuos.Individuo3;

public class Individuo3Factory extends IndividuoFactory {
    protected double precision;
    public Individuo3Factory(double precision) {
        this.precision = precision;
    }

    @Override
    public Individuo createOne() {
        return new Individuo3(precision);
    }
}