package es.ucm.factories;

import es.ucm.individuos.Individuo;
import es.ucm.individuos.Individuo5;

public class Individuo5Factory extends IndividuoFactory {

    private int dimension; // Parámetro d (dimensión)

    public Individuo5Factory(int dimension) {
        this.dimension = dimension;
    }

    @Override
    public Individuo createOne() {
        return new Individuo5(dimension);
    }
}