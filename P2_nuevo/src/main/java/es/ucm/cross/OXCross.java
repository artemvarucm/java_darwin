package es.ucm.cross;

import es.ucm.factories.IndividuoFactory;
import es.ucm.individuos.Individuo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Cruce por orden (OX)
 */
public class OXCross extends AbstractCross {
    public OXCross(IndividuoFactory factory) {
        super(factory);
    }

    public List<Individuo> cross(Individuo parent1, Individuo parent2) {
        List<Individuo> result = new ArrayList<>(2);
        Individuo child1 = this.factory.createOne();
        Individuo child2 = this.factory.createOne();

        int nIntGenes = parent1.getIntGenes().size();
        int nPossibleCuts = nIntGenes - 1;
        Integer randomValue1 = ThreadLocalRandom.current().nextInt(0, nPossibleCuts);
        Integer randomValue2 = ThreadLocalRandom.current().nextInt(0, nPossibleCuts);
        while (randomValue1.equals(randomValue2)) {
            randomValue2 = ThreadLocalRandom.current().nextInt(0, nPossibleCuts);
        }

        if (randomValue1 > randomValue2) {
            int temp = randomValue1;
            randomValue1 = randomValue2;
            randomValue2 = temp;
        }

        // Conjuntos para ver que numeros ya estan o faltan
        Set<Integer> child1Set = new HashSet<>();
        Set<Integer> child2Set = new HashSet<>();
        for (int i = randomValue1 + 1; i <= randomValue2; i++) {
            int parent1Int = parent1.getIntGenes().get(i).getFenotipo();
            int parent2Int = parent2.getIntGenes().get(i).getFenotipo();

            child1.getIntGenes().get(i).set(0, parent2Int);
            child1Set.add(parent2Int);

            child2.getIntGenes().get(i).set(0, parent1Int);
            child2Set.add(parent1Int);
        }

        int parent1Pointer = randomValue2 + 1;
        fillRemaining(parent1, child1, nIntGenes, child1Set, parent1Pointer);

        int parent2Pointer = randomValue2 + 1;
        fillRemaining(parent2, child2, nIntGenes, child2Set, parent2Pointer);

        result.add(child1);
        result.add(child2);
        return result;
    }

    private void fillRemaining(Individuo parent, Individuo child, int nIntGenes, Set<Integer> childSet, int parentPointer) {
        int childPointer = parentPointer;
        while (childSet.size() < nIntGenes) {
            int parentInt = parent.getIntGenes().get(parentPointer).getFenotipo();
            if (childSet.contains(parentInt)) {
                // movemos el puntero, el numero ya no interesa
                parentPointer = (parentPointer + 1) % nIntGenes;
            } else {
                child.getIntGenes().get(childPointer).set(0, parentInt);
                childSet.add(parentInt);
                childPointer = (childPointer + 1) % nIntGenes;
            }
        }
    }
}
