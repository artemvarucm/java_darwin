package es.ucm.mutation;

import es.ucm.individuos.Individuo;

import java.util.Random;

public class UniformMutate extends AbstractMutate {
    private double mutationRate;

    public UniformMutate(double mutationRate) {
        this.mutationRate = mutationRate;
    }

    @Override
    public void mutate(Individuo individuo) {
        Random random = new Random();
        for (int i = 0; i < individuo.getGenotipoLength(); i++) {
            if (random.nextDouble() < mutationRate) {
                individuo.mutateGenotypeElem(i); // Solo muta si el índice es válido
            }
        }
    }
}