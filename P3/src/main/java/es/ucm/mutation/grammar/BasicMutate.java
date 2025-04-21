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

        /*for (int i = 0; i < ind.getGenotipoLength(); i++) {
            double p = ThreadLocalRandom.current().nextDouble();
            if (p < mutateProbability) {
                // System.out.println("MUTACION DEL INDICE " + i);
                //ind.mutateGenotypeElem(i);
            }
        }*/
        return ind.copy();
    }
}