package es.ucm.mutation;

import es.ucm.individuos.Individuo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public abstract class AbstractMutate {
    protected double mutateProbability;
    public AbstractMutate(double mutateProbability) {
        this.mutateProbability = mutateProbability;
    }

    /**
     * Muta el individuo pasado como parámetro
     */
    public abstract Individuo mutate(Individuo ind);
}
