package es.ucm.factories;

import es.ucm.individuos.Individuo;
import es.ucm.individuos.Individuo2;

public class Individuo2Factory extends IndividuoFactory {
    protected double precision;
    public Individuo2Factory(double precision) {
        this.precision = precision;
    }

    @Override
    public Individuo createOne() {
        return new Individuo2(precision);
    }
}