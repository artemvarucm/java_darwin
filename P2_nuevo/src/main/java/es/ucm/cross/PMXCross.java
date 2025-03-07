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
        int n = parent1.getIntGenes().size();
        // Selecciona dos puntos de corte (aseguramos que sean diferentes)
        int cut1 = ThreadLocalRandom.current().nextInt(0, n);
        int cut2 = ThreadLocalRandom.current().nextInt(0, n);
        while (cut1 == cut2) {
            cut2 = ThreadLocalRandom.current().nextInt(0, n);
        }
        int start = Math.min(cut1, cut2);
        int end = Math.max(cut1, cut2);
        
        // Prepara arrays para los hijos
        int[] child1Arr = new int[n];
        int[] child2Arr = new int[n];
        // Inicializa con un valor inválido (por ejemplo, -1)
        for (int i = 0; i < n; i++) {
            child1Arr[i] = -1;
            child2Arr[i] = -1;
        }
        
        // Copia el segmento central y crea el mapeo
        Map<Integer, Integer> mapping1 = new HashMap<>();
        Map<Integer, Integer> mapping2 = new HashMap<>();
        for (int i = start; i <= end; i++) {
            int p1Gene = parent1.getIntGenes().get(i).getFenotipo();
            int p2Gene = parent2.getIntGenes().get(i).getFenotipo();
            child1Arr[i] = p1Gene;
            child2Arr[i] = p2Gene;
            mapping1.put(p2Gene, p1Gene);
            mapping2.put(p1Gene, p2Gene);
        }
        
        // Función auxiliar para resolver mapeo
        // Para child1: se toman genes de parent2, y se resuelve si ya están en el segmento.
        for (int i = 0; i < n; i++) {
            if (i >= start && i <= end) continue;
            int candidate = parent2.getIntGenes().get(i).getFenotipo();
            // Mientras exista mapeo, se sigue la cadena
            while (mapping1.containsKey(candidate)) {
                candidate = mapping1.get(candidate);
            }
            child1Arr[i] = candidate;
        }
        
        // Para child2: se toman genes de parent1, y se resuelve el mapeo inverso.
        for (int i = 0; i < n; i++) {
            if (i >= start && i <= end) continue;
            int candidate = parent1.getIntGenes().get(i).getFenotipo();
            while (mapping2.containsKey(candidate)) {
                candidate = mapping2.get(candidate);
            }
            child2Arr[i] = candidate;
        }
        
        // Actualiza los hijos usando la fábrica y actualizando los genes
        Individuo child1 = factory.createOne();
        Individuo child2 = factory.createOne();
        for (int i = 0; i < n; i++) {
            child1.getIntGenes().get(i).set(0, child1Arr[i]);
            child2.getIntGenes().get(i).set(0, child2Arr[i]);
        }
        
        List<Individuo> result = new ArrayList<>(2);
        result.add(child1);
        result.add(child2);
        return result;
    }
}