package es.ucm.mutation;

import es.ucm.individuos.Individuo;
import es.ucm.individuos.IndividuoAspiradora;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Mutación heurística que intenta mejorar el fitness del individuo
 */
public class HeuristicMutate extends AbstractMutate {
    public HeuristicMutate(double mutateProbability) {
        super(mutateProbability);
    }

    @Override
    public Individuo mutate(Individuo ind) {
        if (!(ind instanceof IndividuoAspiradora)) {
            throw new IllegalArgumentException("HeuristicMutate solo funciona con IndividuoAspiradora");
        }
        IndividuoAspiradora aspiradora = (IndividuoAspiradora) ind.copy();
        int nIntGenes = aspiradora.getIntGenes().size();

        // Seleccionamos dos posiciones aleatorias para intercambiar
        int randomValue1 = ThreadLocalRandom.current().nextInt(0, nIntGenes);
        int randomValue2 = ThreadLocalRandom.current().nextInt(0, nIntGenes);
        while (randomValue1 == randomValue2) {
            randomValue2 = ThreadLocalRandom.current().nextInt(0, nIntGenes);
        }

        // Obtenemos los valores de las habitaciones en las posiciones seleccionadas
        int intVal1 = aspiradora.getIntGenes().get(randomValue1).getFenotipo();
        int intVal2 = aspiradora.getIntGenes().get(randomValue2).getFenotipo();

        // Intercambiamos los valores
        aspiradora.getIntGenes().get(randomValue1).set(0, intVal2);
        aspiradora.getIntGenes().get(randomValue2).set(0, intVal1);

        // Calculamos el nuevo fitness después del intercambio
        double newFitness = aspiradora.getFitness();

        // Si el nuevo fitness es peor, revertimos el intercambio
        if (newFitness > aspiradora.getFitness()) {
            aspiradora.getIntGenes().get(randomValue1).set(0, intVal1);
            aspiradora.getIntGenes().get(randomValue2).set(0, intVal2);
        }

        return aspiradora;
    }
}