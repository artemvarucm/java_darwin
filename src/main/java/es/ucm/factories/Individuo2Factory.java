package es.ucm.factories;

import es.ucm.individuos.Individuo;
import es.ucm.individuos.Individuo2;

public class Individuo2Factory extends IndividuoFactory {

    @Override
    public Individuo createOne() {
        return new Individuo2();
    }
}