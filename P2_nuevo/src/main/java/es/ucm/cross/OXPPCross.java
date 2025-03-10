package es.ucm.cross;

import es.ucm.factories.IndividuoFactory;
import es.ucm.individuos.Individuo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import static es.ucm.utils.RandomUtil.getNDifferentRandInt;

public class OXPPCross extends AbstractCross {
    int N_RANDOMS = 3; // el número de posiciones aleatorias seleccionadas

    public OXPPCross(IndividuoFactory factory) {
        super(factory);
    }

    public List<Individuo> cross(Individuo parent1, Individuo parent2) {
        List<Individuo> result = new ArrayList<>(2);
        Individuo child1 = this.factory.createOne();
        Individuo child2 = this.factory.createOne();

        int nIntGenes = parent1.getIntGenes().size();
        int nPossibleCuts = nIntGenes - 1;
        List<Integer> randomValues = getNDifferentRandInt(0, nPossibleCuts, N_RANDOMS);

        // Conjuntos para ver que numeros ya estan o faltan
        Set<Integer> child1Set = new HashSet<>();
        Set<Integer> child2Set = new HashSet<>();
        for (Integer i: randomValues) {
            int parent1Int = parent1.getIntGenes().get(i).getFenotipo();
            int parent2Int = parent2.getIntGenes().get(i).getFenotipo();

            child1.getIntGenes().get(i).set(0, parent2Int);
            child1Set.add(parent2Int);

            child2.getIntGenes().get(i).set(0, parent1Int);
            child2Set.add(parent1Int);
        }

        fillRemaining(parent1, child1, nIntGenes, child1Set, randomValues);
        fillRemaining(parent2, child2, nIntGenes, child2Set, randomValues);

        result.add(child1);
        result.add(child2);
        return result;
    }

    private void fillRemaining(Individuo parent, Individuo child, int nIntGenes, Set<Integer> childSet, List<Integer> randomFilled) {
        // escogemos una posición vacía de forma aleatoria
        Integer randomEmpty = ThreadLocalRandom.current().nextInt(0, nIntGenes);
        while (randomFilled.contains(randomEmpty)) {
            randomEmpty = ThreadLocalRandom.current().nextInt(0, nIntGenes);
        }

        int parentPointer = randomEmpty;
        int childPointer = parentPointer;
        while (childSet.size() < nIntGenes) {
            if (randomFilled.contains(childPointer)) {
                childPointer = (childPointer + 1) % nIntGenes;
            } else {
                int parent1Int = parent.getIntGenes().get(parentPointer).getFenotipo();
                if (childSet.contains(parent1Int)) {
                    // movemos el puntero, el numero ya no interesa
                    parentPointer = (parentPointer + 1) % nIntGenes;
                } else {
                    child.getIntGenes().get(childPointer).set(0, parent1Int);
                    childSet.add(parent1Int);
                    childPointer = (childPointer + 1) % nIntGenes;
                }
            }
        }
    }
}