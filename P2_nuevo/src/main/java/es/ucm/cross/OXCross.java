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
    // Constructor que recibe una fábrica de individuos
    public OXCross(IndividuoFactory factory) {
        super(factory); // Llama al constructor de la clase padre (AbstractCross)
    }

    // Método para realizar el cruce entre dos padres
    public List<Individuo> cross(Individuo parent1, Individuo parent2) {
        // Lista para almacenar los dos hijos resultantes del cruce
        List<Individuo> result = new ArrayList<>(2);

        // Crear dos nuevos individuos (hijos) utilizando la fábrica
        Individuo child1 = this.factory.createOne();
        Individuo child2 = this.factory.createOne();

        // Obtener el número de genes enteros de los padres
        int nIntGenes = parent1.getIntGenes().size();
        int nPossibleCuts = nIntGenes - 1;

        // Generar dos puntos de corte aleatorios para el cruce OX
        Integer randomValue1 = ThreadLocalRandom.current().nextInt(0, nPossibleCuts);
        Integer randomValue2 = ThreadLocalRandom.current().nextInt(0, nPossibleCuts);

        // Asegurarse de que los dos puntos de corte sean diferentes
        while (randomValue1.equals(randomValue2)) {
            randomValue2 = ThreadLocalRandom.current().nextInt(0, nPossibleCuts);
        }

        // Asegurarse de que randomValue1 sea el menor de los dos puntos de corte
        if (randomValue1 > randomValue2) {
            int temp = randomValue1;
            randomValue1 = randomValue2;
            randomValue2 = temp;
        }

        // Conjuntos para verificar qué números ya están asignados en los hijos
        Set<Integer> child1Set = new HashSet<>();
        Set<Integer> child2Set = new HashSet<>();

        // Intercambiar los genes entre los puntos de corte
        for (int i = randomValue1 + 1; i <= randomValue2; i++) {
            int parent1Int = parent1.getIntGenes().get(i).getFenotipo();
            int parent2Int = parent2.getIntGenes().get(i).getFenotipo();

            // Asignar los genes de parent2 a child1 y viceversa
            child1.getIntGenes().get(i).set(0, parent2Int);
            child1Set.add(parent2Int); // Registrar el número asignado en child1

            child2.getIntGenes().get(i).set(0, parent1Int);
            child2Set.add(parent1Int); // Registrar el número asignado en child2
        }

        // Completar los genes restantes en los hijos
        int parent1Pointer = randomValue2 + 1; // Puntero para recorrer los genes de parent1
        fillRemaining(parent1, child1, nIntGenes, child1Set, parent1Pointer);

        int parent2Pointer = randomValue2 + 1; // Puntero para recorrer los genes de parent2
        fillRemaining(parent2, child2, nIntGenes, child2Set, parent2Pointer);

        // Agregar los hijos a la lista de resultados
        result.add(child1);
        result.add(child2);

        // Devolver la lista de hijos
        return result;
    }

    // Método para completar los genes restantes en los hijos
    private void fillRemaining(Individuo parent, Individuo child, int nIntGenes, Set<Integer> childSet, int parentPointer) {
        int childPointer = parentPointer; // Puntero para recorrer los genes del hijo
        while (childSet.size() < nIntGenes) {
            int parentInt = parent.getIntGenes().get(parentPointer).getFenotipo();
            if (childSet.contains(parentInt)) {
                // Si el número ya está en el hijo, avanzar al siguiente gen del padre
                parentPointer = (parentPointer + 1) % nIntGenes;
            } else {
                // Asignar el gen del padre al hijo
                child.getIntGenes().get(childPointer).set(0, parentInt);
                childSet.add(parentInt); // Registrar el número asignado
                childPointer = (childPointer + 1) % nIntGenes; // Avanzar al siguiente gen del hijo
            }
        }
    }
}