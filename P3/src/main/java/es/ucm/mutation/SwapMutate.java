package es.ucm.mutation;

import es.ucm.individuos.Individuo;

import java.util.concurrent.ThreadLocalRandom;

/**
 *  Mutación por intercambio
 */
public class SwapMutate extends AbstractMutate {
    public SwapMutate(double mutateProbability) {
        super(mutateProbability);
    }

    public Individuo mutate(Individuo ind) {
        Individuo indMutado = ind.copy();
        int nIntGenes = indMutado.getIntGenes().size();
        double p = ThreadLocalRandom.current().nextDouble();
        if (p < mutateProbability && nIntGenes > 1) {
            Integer randomValue1 = ThreadLocalRandom.current().nextInt(0, nIntGenes);
            Integer randomValue2 = ThreadLocalRandom.current().nextInt(0, nIntGenes);
            while (randomValue1.equals(randomValue2)) {
                randomValue2 = ThreadLocalRandom.current().nextInt(0, nIntGenes);
            }

            Integer intVal1 = indMutado.getIntGenes().get(randomValue1).getFenotipo();
            Integer intVal2 = indMutado.getIntGenes().get(randomValue2).getFenotipo();
            indMutado.getIntGenes().get(randomValue1).set(0, intVal2);
            indMutado.getIntGenes().get(randomValue2).set(0, intVal1);
        }

        return indMutado;
    }
}
