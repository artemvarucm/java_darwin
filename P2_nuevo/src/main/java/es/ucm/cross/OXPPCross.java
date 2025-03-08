package es.ucm.cross;

import es.ucm.factories.IndividuoFactory;
import es.ucm.individuos.Individuo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class OXPPCross extends AbstractCross {
    public OXPPCross(IndividuoFactory factory) {
        super(factory);
    }

    @Override
    public List<Individuo> cross(Individuo parent1, Individuo parent2) {
        List<Individuo> result = new ArrayList<>(2);
        Individuo child1 = this.factory.createOne();
        Individuo child2 = this.factory.createOne();

        int nIntGenes = parent1.getIntGenes().size();
        int randomValue1 = ThreadLocalRandom.current().nextInt(0, nIntGenes);
        int randomValue2 = ThreadLocalRandom.current().nextInt(0, nIntGenes);

        if (randomValue1 > randomValue2) {
            int temp = randomValue1;
            randomValue1 = randomValue2;
            randomValue2 = temp;
        }

        // Conjuntos para ver qué números ya están o faltan
        Set<Integer> child1Set = new HashSet<>();
        Set<Integer> child2Set = new HashSet<>();

        // Copiar el segmento de parent2 a child1 y de parent1 a child2
        for (int i = randomValue1; i <= randomValue2; i++) {
            int gene1 = parent1.getIntGenes().get(i).getFenotipo();
            int gene2 = parent2.getIntGenes().get(i).getFenotipo();

            child1.getIntGenes().get(i).set(0, gene2);
            child1Set.add(gene2);

            child2.getIntGenes().get(i).set(0, gene1);
            child2Set.add(gene1);
        }

        // Completar el resto de los genes
        int parent1Pointer = (randomValue2 + 1) % nIntGenes;
        int parent2Pointer = (randomValue2 + 1) % nIntGenes;

        for (int i = (randomValue2 + 1) % nIntGenes; i != randomValue1; i = (i + 1) % nIntGenes) {
            while (child1Set.contains(parent1.getIntGenes().get(parent1Pointer).getFenotipo())) {
                parent1Pointer = (parent1Pointer + 1) % nIntGenes;
            }
            while (child2Set.contains(parent2.getIntGenes().get(parent2Pointer).getFenotipo())) {
                parent2Pointer = (parent2Pointer + 1) % nIntGenes;
            }

            child1.getIntGenes().get(i).set(0, parent1.getIntGenes().get(parent1Pointer).getFenotipo());
            child1Set.add(parent1.getIntGenes().get(parent1Pointer).getFenotipo());

            child2.getIntGenes().get(i).set(0, parent2.getIntGenes().get(parent2Pointer).getFenotipo());
            child2Set.add(parent2.getIntGenes().get(parent2Pointer).getFenotipo());

            parent1Pointer = (parent1Pointer + 1) % nIntGenes;
            parent2Pointer = (parent2Pointer + 1) % nIntGenes;
        }

        result.add(child1);
        result.add(child2);
        return result;
    }
}