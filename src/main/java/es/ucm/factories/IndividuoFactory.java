package es.ucm.factories;

import es.ucm.Individuo;

import java.util.ArrayList;
import java.util.List;

/**
 * Se usa al crear la población inicial
 */
public abstract class IndividuoFactory {
    public abstract Individuo createOne();

    public List<Individuo> createMany(int tam) {
        List<Individuo> individuos = new ArrayList<>(tam);
        for (int i = 0; i < tam; i++) {
            individuos.add(createOne());
        }

        return individuos;
    }
}
