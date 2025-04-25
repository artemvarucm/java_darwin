package es.ucm.mutation.grammar;

import es.ucm.individuos.Individuo;
import es.ucm.mutation.AbstractMutate;

import java.util.concurrent.ThreadLocalRandom;

public class BasicMutate extends AbstractMutate {
    public BasicMutate(double mutate_probability) {
        super(mutate_probability);
    }

    @Override
    public Individuo mutate(Individuo ind) {
        boolean mutated = false;
        for (int i = 0; i < ind.getIntGenes().size(); i++) {
            double p = ThreadLocalRandom.current().nextDouble();
            if (p < mutateProbability) {
                mutated = true;
                ind.getIntGenes().get(i).set(0, ThreadLocalRandom.current().nextInt(256));
            }
        }

        if (mutated)
            this.successfulMutate();

        return ind.copy();
    }
}