package es.ucm.factories;

import es.ucm.individuos.Individuo;
import es.ucm.individuos.Individuo4;

public class Individuo4Factory extends IndividuoFactory {

    private int dimension; // Parámetro d (dimensión)

    public Individuo4Factory(int dimension) {
        this.dimension = dimension;
    }

    @Override
    public Individuo createOne() {
        return new Individuo4(dimension);
    }
}