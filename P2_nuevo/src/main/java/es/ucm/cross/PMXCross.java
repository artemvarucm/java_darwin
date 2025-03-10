package es.ucm.cross;

import es.ucm.factories.IndividuoFactory;
import es.ucm.individuos.Individuo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class PMXCross extends AbstractCross {
    public PMXCross(IndividuoFactory factory) {
        super(factory);
    }

    @Override
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

        // Mapeo de los segmentos cruzados
        Map<Integer, Integer> mapping1 = new HashMap<>();
        Map<Integer, Integer> mapping2 = new HashMap<>();
        for (int i = randomValue1 + 1; i <= randomValue2; i++) {
            int gene1 = parent1.getIntGenes().get(i).getFenotipo();
            int gene2 = parent2.getIntGenes().get(i).getFenotipo();

            child1.getIntGenes().get(i).set(0, gene2);
            child2.getIntGenes().get(i).set(0, gene1);

            mapping1.put(gene2, gene1);
            mapping2.put(gene1, gene2);
        }

        // Completar el resto de los genes
        for (int i = 0; i < nIntGenes; i++) {
            if (i <= randomValue1 || i > randomValue2) {
                // posición que falta por rellenar
                int gene1 = parent1.getIntGenes().get(i).getFenotipo();
                int gene2 = parent2.getIntGenes().get(i).getFenotipo();

                while (mapping1.containsKey(gene1)) {
                    gene1 = mapping1.get(gene1);
                }
                while (mapping2.containsKey(gene2)) {
                    gene2 = mapping2.get(gene2);
                }

                child1.getIntGenes().get(i).set(0, gene1);
                child2.getIntGenes().get(i).set(0, gene2);
            }
        }

        result.add(child1);
        result.add(child2);
        return result;
    }
}