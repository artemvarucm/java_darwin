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
        int i = parent1Pointer;
        while (child1Set.size() < nIntGenes) {
            int parent1Int = parent1.getIntGenes().get(parent1Pointer).getFenotipo();
            if (child1Set.contains(parent1Int)) {
                // movemos el puntero, el numero ya no interesa
                parent1Pointer = (parent1Pointer + 1) % nIntGenes;
            } else {
                child1.getIntGenes().get(i).set(0, parent1Int);
                child1Set.add(parent1Int);
                i = (i + 1) % nIntGenes;
            }
        }

        int parent2Pointer = randomValue2 + 1;
        i = parent2Pointer;
        while (child2Set.size() < nIntGenes) {
            int parent2Int = parent2.getIntGenes().get(parent2Pointer).getFenotipo();
            if (child2Set.contains(parent2Int)) {
                // movemos el puntero, el numero ya no interesa
                parent2Pointer = (parent2Pointer + 1) % nIntGenes;
            } else {
                child2.getIntGenes().get(i).set(0, parent2Int);
                child2Set.add(parent2Int);
                i = (i + 1) % nIntGenes;
            }
        }

        result.add(child1);
        result.add(child2);
        return result;
    }
}
