package es.ucm.cross;

import es.ucm.factories.IndividuoFactory;
import es.ucm.genes.IntegerGen;
import es.ucm.individuos.Individuo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class COCross extends AbstractCross {

    public COCross(IndividuoFactory factory) {
        super(factory);
    }

    @Override
    public List<Individuo> cross(Individuo parent1, Individuo parent2) {
        List<Integer> p1 = getPermutation(parent1);
        List<Integer> p2 = getPermutation(parent2);
        int n = p1.size();
        
        // Se calcula el conjunto ordenado (los números de la permutación)
        List<Integer> sortedSet = new ArrayList<>(p1);
        Collections.sort(sortedSet);
        
        // Convierte cada permutación en su codificación ordinal
        List<Integer> ordinal1 = toOrdinal(p1, sortedSet);
        List<Integer> ordinal2 = toOrdinal(p2, sortedSet);
        
        // Cruce de un punto (se elige punto de crossover aleatorio)
        int crossoverPoint = ThreadLocalRandom.current().nextInt(0, n - 1);
        List<Integer> child1Ordinal = new ArrayList<>();
        List<Integer> child2Ordinal = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (i <= crossoverPoint) {
                child1Ordinal.add(ordinal1.get(i));
                child2Ordinal.add(ordinal2.get(i));
            } else {
                child1Ordinal.add(ordinal2.get(i));
                child2Ordinal.add(ordinal1.get(i));
            }
        }
        
        // Decodifica la codificación ordinal para obtener la permutación
        List<Integer> child1Perm = fromOrdinal(child1Ordinal, sortedSet);
        List<Integer> child2Perm = fromOrdinal(child2Ordinal, sortedSet);
        
        // Actualiza los hijos
        Individuo child1 = factory.createOne();
        Individuo child2 = factory.createOne();
        for (int i = 0; i < n; i++) {
            child1.getIntGenes().get(i).set(0, child1Perm.get(i));
            child2.getIntGenes().get(i).set(0, child2Perm.get(i));
        }
        
        List<Individuo> result = new ArrayList<>(2);
        result.add(child1);
        result.add(child2);
        return result;
    }
    
    // Extrae la permutación a partir de los genes
    private List<Integer> getPermutation(Individuo ind) {
        List<Integer> perm = new ArrayList<>();
        for (IntegerGen gene : ind.getIntGenes()) {
            perm.add(gene.getFenotipo());
        }
        return perm;
    }
    
    // Convierte una permutación en su codificación ordinal.
    // Se parte de una lista copia de la permutación y para cada elemento se busca el índice en la lista
    private List<Integer> toOrdinal(List<Integer> perm, List<Integer> sortedSet) {
        List<Integer> available = new ArrayList<>(sortedSet);
        List<Integer> ordinal = new ArrayList<>();
        for (int gene : perm) {
            int index = available.indexOf(gene);
            ordinal.add(index);
            available.remove(index);
        }
        return ordinal;
    }
    
    // Decodifica la codificación ordinal para obtener la permutación.
    private List<Integer> fromOrdinal(List<Integer> ordinal, List<Integer> sortedSet) {
        List<Integer> available = new ArrayList<>(sortedSet);
        List<Integer> perm = new ArrayList<>();
        for (int num : ordinal) {
            perm.add(available.get(num));
            available.remove(num);
        }
        return perm;
    }
}