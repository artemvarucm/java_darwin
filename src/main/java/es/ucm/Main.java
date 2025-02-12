package es.ucm;

import es.ucm.factories.Individuo1Factory;
import es.ucm.factories.IndividuoFactory;

public class Main {
    public static void main(String[] args) {
        IndividuoFactory factory = new Individuo1Factory();
        AlgoritmoGenetico algo = new AlgoritmoGenetico(factory, 50);
        algo.optimize();
    }
}