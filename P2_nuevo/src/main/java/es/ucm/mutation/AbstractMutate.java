package es.ucm.mutation;

import es.ucm.individuos.Individuo;

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
