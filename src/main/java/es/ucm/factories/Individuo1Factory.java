package es.ucm.factories;

import es.ucm.Individuo;
import es.ucm.Individuo1;

import java.util.ArrayList;
import java.util.List;


public class Individuo1Factory extends IndividuoFactory {
    public List<Individuo> createIndividuos(int tam) {
        List<Individuo> individuos = new ArrayList<>(tam);
        for (int i = 0; i < tam; i++) {
            individuos.add(new Individuo1());
        }

        return individuos;
    }
}
