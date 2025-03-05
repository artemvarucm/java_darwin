package es.ucm.factories;

import es.ucm.individuos.Individuo;
import es.ucm.individuos.Individuo4;

public class Individuo4Factory extends IndividuoFactory {

    private int dimension; // Parámetro d (dimensión)
    protected double precision;
    public Individuo4Factory(double precision, int dimension) {
        this.precision = precision;
        this.dimension = dimension;
    }

    @Override
    public Individuo createOne() {
        return new Individuo4(precision, dimension);
    }
}