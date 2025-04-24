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
        double p = ThreadLocalRandom.current().nextDouble();
        if (p < mutateProbability) {
            for (int i = 0; i < ind.getIntGenes().size(); i++) {
                p = ThreadLocalRandom.current().nextDouble();
                if (p < mutateProbability) {
                    ind.getIntGenes().get(i).set(0, ThreadLocalRandom.current().nextInt(256));
                }
            }

            this.successfulMutate();
        }
        return ind.copy();
    }
}