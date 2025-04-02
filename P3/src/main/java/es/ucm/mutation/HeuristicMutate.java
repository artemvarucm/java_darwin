package es.ucm.mutation;

import es.ucm.individuos.Individuo;
import es.ucm.individuos.IndividuoAspiradora;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static es.ucm.utils.RandomUtil.getNDifferentRandInt;


/**
 * Mutación heurística que intenta mejorar el fitness del individuo
 */
public class HeuristicMutate extends AbstractMutate {
    int N_SWAP_ELEMS = 3; // numero de elementos que vamos a intercambiar
    public HeuristicMutate(double mutateProbability) {
        super(mutateProbability);
    }

    @Override
    public Individuo mutate(Individuo ind) {
        if (!(ind instanceof IndividuoAspiradora)) {
            throw new IllegalArgumentException("HeuristicMutate solo funciona con IndividuoAspiradora");
        }
        IndividuoAspiradora indMutado = (IndividuoAspiradora) ind.copy();
        int nIntGenes = indMutado.getIntGenes().size();
        double p = ThreadLocalRandom.current().nextDouble();
        if (p < mutateProbability && nIntGenes > 1) {
            // Seleccionamos posiciones aleatorias para intercambiar
            List<Integer> randomPositions = getNDifferentRandInt(0, nIntGenes, N_SWAP_ELEMS);

            // Obtenemos los valores de las habitaciones en las posiciones seleccionadas
            List<Integer> intValues = new ArrayList<>();
            for (Integer pos: randomPositions)
                intValues.add(indMutado.getIntGenes().get(pos).getFenotipo());

            indMutado = this.permuteAndGetBest(intValues, indMutado, randomPositions, new ArrayList<>());
        }

        return indMutado;
    }

    /**
     * Realiza las permutaciones y devuelve el individuo con mejor fitness
     */
    private IndividuoAspiradora permuteAndGetBest(List<Integer> intValues, IndividuoAspiradora original, List<Integer> positions, List<Integer> permutation){
        IndividuoAspiradora mejor = original;
        if (intValues.isEmpty()) {
            IndividuoAspiradora indPerm = (IndividuoAspiradora) original.copy();
            for (int i = 0; i < positions.size(); i++) {
                indPerm.getIntGenes().get(positions.get(i)).set(0, permutation.get(i));
            }
            if (indPerm.getMaximizar() && indPerm.getFitness() > mejor.getFitness()) {
                mejor = indPerm;
            } else if (!indPerm.getMaximizar() && indPerm.getFitness() < mejor.getFitness()) {
                mejor = indPerm;
            }
        } else {
            for (int i = 0; i < intValues.size(); i++) {
                // sacamos el elemento
                Integer temp = intValues.remove(i);
                permutation.add(temp);
                IndividuoAspiradora indPerm = permuteAndGetBest(intValues, original, positions, permutation);
                if (indPerm.getMaximizar() && indPerm.getFitness() > mejor.getFitness()) {
                    mejor = indPerm;
                } else if (!indPerm.getMaximizar() && indPerm.getFitness() < mejor.getFitness()) {
                    mejor = indPerm;
                }
                permutation.remove(temp);
                // volvemos el elemento
                intValues.add(i, temp);
            }
        }

        return mejor;
    }
}