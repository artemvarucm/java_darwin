package es.ucm.mutation;

import es.ucm.individuos.Individuo;


public abstract class AbstractMutate {
    protected int numMutations; // numero de mutaciones, se incrementa con cada mutacion exitosa, se reinicia al inicializar el algoritmo
    protected double mutateProbability;
    public AbstractMutate(double mutateProbability) {
        this.mutateProbability = mutateProbability;
        this.numMutations = 0;
    }

    /**
     * Muta el individuo pasado como parámetro
     */
    public abstract Individuo mutate(Individuo ind);

    public void resetNumMutation() {
        numMutations = 0;
    }

    protected void successfulMutate() {
        numMutations++;
    }

    public int getNumMutations() {
        return numMutations;
    }
}
