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

    /**
     * Devuelve una lista de enteros aleatorios en el rango seleccionado.
     * !Todos los numeros son diferentes!
     */
    protected List<Integer> getNDifferentRandInt(int origin, int bound, int size) {
        List<Integer> result = new ArrayList<>();

        while (result.size() < size) {
            int randomValue = ThreadLocalRandom.current().nextInt(origin, bound);
            if (!result.contains(randomValue)) {
                result.add(randomValue);
            }
        }

        return result;
    }
}
