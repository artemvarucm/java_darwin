package es.ucm.factories;

import es.ucm.individuos.Individuo;
import es.ucm.individuos.Individuo3;

public class Individuo3Factory extends IndividuoFactory {

    @Override
    public Individuo createOne() {
        return new Individuo3();
    }
}