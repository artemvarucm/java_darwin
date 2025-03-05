package es.ucm.mutation;

import es.ucm.individuos.Individuo;

import java.util.concurrent.ThreadLocalRandom;

/**
 *  Mutación por intercambio
 */
public class SwapMutate extends AbstractMutate {
    public void mutate(Individuo ind) {
        int nIntGenes = ind.getIntGenes().size();
        Integer randomValue1 = ThreadLocalRandom.current().nextInt(0, nIntGenes);
        Integer randomValue2 = ThreadLocalRandom.current().nextInt(0, nIntGenes);
        while (randomValue1.equals(randomValue2)) {
            randomValue2 = ThreadLocalRandom.current().nextInt(0, nIntGenes);
        }

        Integer intVal1 = ind.getIntGenes().get(randomValue1).getFenotipo();
        Integer intVal2 = ind.getIntGenes().get(randomValue2).getFenotipo();
        ind.getIntGenes().get(randomValue1).set(0, intVal2);
        ind.getIntGenes().get(randomValue2).set(0, intVal1);
    }
}
