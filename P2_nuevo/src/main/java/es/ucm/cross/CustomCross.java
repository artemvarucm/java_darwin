package es.ucm.cross;

import es.ucm.factories.IndividuoFactory;
import es.ucm.individuos.Individuo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*
 * Como operador inventado se propone un cruce alternante (por ejemplo, en posiciones pares se toma el gen de parent1 y en impares de parent2) y, 
 * a continuación, se repara la permutación completando los huecos con los elementos faltantes.
 * */


public class CustomCross extends AbstractCross {

    public CustomCross(IndividuoFactory factory) {
        super(factory);
    }

    @Override
    public List<Individuo> cross(Individuo parent1, Individuo parent2) {
        int n = parent1.getIntGenes().size();
        int[] child1Arr = new int[n];
        int[] child2Arr = new int[n];
        // Inicializamos con un valor “vacío” (por ejemplo, -1)
        for (int i = 0; i < n; i++) {
            child1Arr[i] = -1;
            child2Arr[i] = -1;
        }
        
        // Se alterna la selección: en posiciones pares, child1 toma parent1 y child2 toma parent2; en impares, se hace lo contrario
        for (int i = 0; i < n; i++) {
            if (i % 2 == 0) {
                child1Arr[i] = parent1.getIntGenes().get(i).getFenotipo();
                child2Arr[i] = parent2.getIntGenes().get(i).getFenotipo();
            } else {
                child1Arr[i] = parent2.getIntGenes().get(i).getFenotipo();
                child2Arr[i] = parent1.getIntGenes().get(i).getFenotipo();
            }
        }
        
        // Se repara cada hijo para que sea una permutación válida:
        // Se detectan los elementos duplicados y se sustituyen por los que falten.
        child1Arr = repairPermutation(child1Arr);
        child2Arr = repairPermutation(child2Arr);
        
        // Actualiza los hijos
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
    
    // Repara una permutación: reemplaza los duplicados por los genes que faltan
    private int[] repairPermutation(int[] perm) {
        int n = perm.length;
        Set<Integer> present = new HashSet<>();
        for (int num : perm) {
            if (num != -1) {
                present.add(num);
            }
        }
        // Suponemos que la permutación original contiene los números del 0 a n-1 o los que tenga parent
        // Aquí se infiere el conjunto completo a partir de los que hay (por ejemplo, si se usan IDs de habitaciones)
        // Se asume que el conjunto completo es el mismo que el de parent, que se puede obtener de  parent.getIntGenes()
        // Para este ejemplo, se asume que los genes están en el rango [0, n-1]
        Set<Integer> complete = new HashSet<>();
        for (int i = 0; i < n; i++) {
            complete.add(i);
        }
        
        complete.removeAll(present);
        List<Integer> missing = new ArrayList<>(complete);
        
        int[] repaired = perm.clone();
        for (int i = 0; i < n; i++) {
            // Si hay duplicado o valor -1, se asigna uno de los que faltan
            int count = 0;
            for (int j = 0; j < n; j++) {
                if (i != j && repaired[i] == repaired[j]) {
                    count++;
                }
            }
            if (repaired[i] == -1 || count > 0) {
                if (!missing.isEmpty()) {
                    repaired[i] = missing.remove(0);
                }
            }
        }
        return repaired;
    }
}