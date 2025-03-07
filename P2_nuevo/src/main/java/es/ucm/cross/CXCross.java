package es.ucm.cross;

import es.ucm.factories.IndividuoFactory;
import es.ucm.individuos.Individuo;

import java.util.ArrayList;
import java.util.List;

public class CXCross extends AbstractCross {

    public CXCross(IndividuoFactory factory) {
        super(factory);
    }

    @Override
    public List<Individuo> cross(Individuo parent1, Individuo parent2) {
        int n = parent1.getIntGenes().size();
        int[] child1Arr = new int[n];
        int[] child2Arr = new int[n];
        // Marcas para saber qué posiciones ya se asignaron
        boolean[] assigned = new boolean[n];
        // Inicializa con un valor inválido (por ejemplo, -1)
        for (int i = 0; i < n; i++) {
            child1Arr[i] = -1;
            child2Arr[i] = -1;
        }
        int cycle = 1;
        while (!allAssigned(assigned)) {
            // Encuentra la primera posición sin asignar
            int index = 0;
            while (index < n && assigned[index]) {
                index++;
            }
            if (index >= n) break;
            // Realiza el ciclo
            int start = index;
            do {
                // Si es ciclo impar, se copia normal; si es par, se intercambian
                if (cycle % 2 != 0) {
                    child1Arr[index] = parent1.getIntGenes().get(index).getFenotipo();
                    child2Arr[index] = parent2.getIntGenes().get(index).getFenotipo();
                } else {
                    child1Arr[index] = parent2.getIntGenes().get(index).getFenotipo();
                    child2Arr[index] = parent1.getIntGenes().get(index).getFenotipo();
                }
                assigned[index] = true;
                // Encuentra el índice en parent1 cuyo elemento coincide con el de parent2 en la posición actual
                int gene = parent2.getIntGenes().get(index).getFenotipo();
                index = findIndex(parent1, gene);
            } while (index != start);
            cycle++;
        }
        
        // Para cualquier posición que no se haya asignado (por seguridad), se asigna de forma directa
        for (int i = 0; i < n; i++) {
            if (child1Arr[i] == -1) {
                child1Arr[i] = parent1.getIntGenes().get(i).getFenotipo();
            }
            if (child2Arr[i] == -1) {
                child2Arr[i] = parent2.getIntGenes().get(i).getFenotipo();
            }
        }
        
        // Crea los hijos y actualiza sus genes
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
    
    // Retorna true si todas las posiciones han sido asignadas
    private boolean allAssigned(boolean[] assigned) {
        for (boolean a : assigned) {
            if (!a) return false;
        }
        return true;
    }
    
    // Encuentra el índice en parent cuyo gen es igual a target
    private int findIndex(Individuo parent, int target) {
        int n = parent.getIntGenes().size();
        for (int i = 0; i < n; i++) {
            if (parent.getIntGenes().get(i).getFenotipo() == target) {
                return i;
            }
        }
        return -1;
    }
}