package es.ucm.factories;

import es.ucm.Individuo;
import es.ucm.Individuo1;


public class Individuo1Factory extends IndividuoFactory {

    public Individuo createOne() {
        return new Individuo1();
    }
}
