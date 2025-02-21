package es.ucm.mutation;

import es.ucm.individuos.Individuo;
import java.util.concurrent.ThreadLocalRandom;

public class UniformMutate extends AbstractMutate {
    private double mutateProbability;

    public UniformMutate(double mutateProbability) {
        this.mutateProbability = mutateProbability;
    }

    @Override
    public void mutate(Individuo ind) {
        for (int i = 0; i < ind.getGenotipoLength(); i++) {
            if (ThreadLocalRandom.current().nextDouble() < mutateProbability) {
                // Mutar el gen i con un valor aleatorio dentro de los límites del individuo
                ind.mutateGenotypeElem(i);
            }
        }
    }
}