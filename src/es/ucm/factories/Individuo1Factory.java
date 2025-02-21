package es.ucm.factories;

import es.ucm.individuos.Individuo;
import es.ucm.individuos.Individuo1;


public class Individuo1Factory extends IndividuoFactory {

    public Individuo createOne() {
        return new Individuo1();
    }
}
