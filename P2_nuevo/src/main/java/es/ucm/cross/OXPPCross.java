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
        int n = parent1.getIntGenes().size();
        int cut1 = ThreadLocalRandom.current().nextInt(0, n);
        int cut2 = ThreadLocalRandom.current().nextInt(0, n);
        while (cut1 == cut2) {
            cut2 = ThreadLocalRandom.current().nextInt(0, n);
        }
        int start = Math.min(cut1, cut2);
        int end = Math.max(cut1, cut2);

        Individuo child1 = factory.createOne();
        Individuo child2 = factory.createOne();

        // Para child1: copia el segmento de parent1
        Set<Integer> child1Set = new HashSet<>();
        for (int i = start; i <= end; i++) {
            int gene = parent1.getIntGenes().get(i).getFenotipo();
            child1.getIntGenes().get(i).set(0, gene);
            child1Set.add(gene);
        }
        // Rellena el resto usando el orden de parent2 (empezando desde el índice 0)
        int pointer = 0;
        for (int i = 0; i < n; i++) {
            if (child1Set.contains(parent2.getIntGenes().get(i).getFenotipo()))
                continue;
            // Encuentra la siguiente posición vacía en child1 (recorremos de 0 a n-1)
            while (child1.getIntGenes().get(pointer).getFenotipo() != -1 && pointer < n) {
                pointer++;
            }
            if (pointer < n) {
                child1.getIntGenes().get(pointer).set(0, parent2.getIntGenes().get(i).getFenotipo());
                child1Set.add(parent2.getIntGenes().get(i).getFenotipo());
            }
        }

        // Para child2: intercambiamos los roles de los padres
        Set<Integer> child2Set = new HashSet<>();
        for (int i = start; i <= end; i++) {
            int gene = parent2.getIntGenes().get(i).getFenotipo();
            child2.getIntGenes().get(i).set(0, gene);
            child2Set.add(gene);
        }
        pointer = 0;
        for (int i = 0; i < n; i++) {
            if (child2Set.contains(parent1.getIntGenes().get(i).getFenotipo()))
                continue;
            while (child2.getIntGenes().get(pointer).getFenotipo() != -1 && pointer < n) {
                pointer++;
            }
            if (pointer < n) {
                child2.getIntGenes().get(pointer).set(0, parent1.getIntGenes().get(i).getFenotipo());
                child2Set.add(parent1.getIntGenes().get(i).getFenotipo());
            }
        }

        List<Individuo> result = new ArrayList<>(2);
        result.add(child1);
        result.add(child2);
        return result;
    }
}