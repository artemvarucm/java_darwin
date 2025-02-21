package es.ucm.mutation;

import es.ucm.individuos.Individuo;

public abstract class AbstractMutate {
    /**
     * Muta el individuo pasado como parámetro
     */
    public abstract void mutate(Individuo ind);
}
