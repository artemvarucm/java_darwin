package es.ucm.mutation;

import es.ucm.individuos.Individuo;

import java.util.concurrent.ThreadLocalRandom;

public class SimpleMutate extends AbstractMutate {
    protected double mutate_probability;
    public SimpleMutate(double mutate_probability) {
        this.mutate_probability = mutate_probability;
    }

    @Override
    public void mutate(Individuo ind) {
        for (int i = 0; i <= ind.getGenotipoLength(); i++) {
            double p = ThreadLocalRandom.current().nextDouble();
            if (p < mutate_probability) {
                // System.out.println("MUTACION DEL INDICE " + i);
                ind.mutateGenotypeElem(i);
            }
        }
    }
}