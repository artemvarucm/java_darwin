package es.ucm.cross;

import es.ucm.factories.IndividuoFactory;
import es.ucm.genes.IntegerGen;
import es.ucm.individuos.Individuo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map; 
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class ERXCross extends AbstractCross {

    public ERXCross(IndividuoFactory factory) {
        super(factory);
    }

    @Override
    public List<Individuo> cross(Individuo parent1, Individuo parent2) {
        List<Integer> p1 = getPermutation(parent1);
        List<Integer> p2 = getPermutation(parent2);
        int n = p1.size();
        
        // Genera dos hijos aplicando el algoritmo de ERX
        List<Integer> child1Perm = generateChild(p1, p2);
        List<Integer> child2Perm = generateChild(p2, p1);
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
    
    // Extrae la permutación del individuo
    private List<Integer> getPermutation(Individuo ind) {
        List<Integer> perm = new ArrayList<>();
        for (IntegerGen gene : ind.getIntGenes()) {
            perm.add(gene.getFenotipo());
        }
        return perm;
    }
    
    // Construye el edge map a partir de los dos padres
    private Map<Integer, Set<Integer>> buildEdgeMap(List<Integer> p1, List<Integer> p2) {
        int n = p1.size();
        Map<Integer, Set<Integer>> edgeMap = new HashMap<>();
        // Inicializa el mapa para cada gen
        for (Integer gene : p1) {
            edgeMap.put(gene, new HashSet<>());
        }
        // Función auxiliar para agregar vecinos (con vecinos cíclicos)
        for (int i = 0; i < n; i++) {
            int gene1 = p1.get(i);
            int left = p1.get((i - 1 + n) % n);
            int right = p1.get((i + 1) % n);
            edgeMap.get(gene1).add(left);
            edgeMap.get(gene1).add(right);
        }
        for (int i = 0; i < p2.size(); i++) {
            int gene2 = p2.get(i);
            int left = p2.get((i - 1 + n) % n);
            int right = p2.get((i + 1) % n);
            edgeMap.get(gene2).add(left);
            edgeMap.get(gene2).add(right);
        }
        return edgeMap;
    }
    
    // Genera un hijo usando ERX
    private List<Integer> generateChild(List<Integer> p1, List<Integer> p2) {
        int n = p1.size();
        Map<Integer, Set<Integer>> edgeMap = buildEdgeMap(p1, p2);
        List<Integer> child = new ArrayList<>();
        // la ruta empieza en el primer nodo del padre p1
        child.add(p1.get(0));
        recursive(child, edgeMap, n);
        return child;
    }

    private void recursive(List<Integer> child, Map<Integer, Set<Integer>> edgeMap, int parentSize) {
        int current = child.get(child.size() - 1);
        Set<Integer> neighbors = edgeMap.get(current);
        if (!neighbors.isEmpty()) {
            // Escoge el vecino con lista de adyacencia mínima (en caso de empate, aleatorio)
            int min = Integer.MAX_VALUE;
            List<Integer> candidateList = new ArrayList<>();
            for (int gene : neighbors) {
                int size = edgeMap.get(gene).size();
                if (size < min) {
                    candidateList.clear();
                    candidateList.add(gene);
                    min = size;
                } else if (size == min) {
                    candidateList.add(gene);
                }
            }

            while (!candidateList.isEmpty()) {
                int random = ThreadLocalRandom.current().nextInt(0, candidateList.size());
                Integer next = candidateList.get(random);
                candidateList.remove(random);
                child.add(next);
                if (child.size() == parentSize) {
                    break;
                } else {
                    recursive(child, edgeMap, parentSize);
                    if (child.size() == parentSize) {
                        break;
                    } else {
                        child.remove(next);
                    }
                }
            }
        }
    }
}