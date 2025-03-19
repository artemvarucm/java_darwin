package es.ucm.cross;

import es.ucm.factories.IndividuoFactory;
import es.ucm.individuos.Individuo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class PMXCross extends AbstractCross {
    // Constructor que recibe una fábrica de individuos
    public PMXCross(IndividuoFactory factory) {
        super(factory); // Llama al constructor de la clase padre (AbstractCross)
    }

    @Override
    public List<Individuo> cross(Individuo parent1, Individuo parent2) {
        // Lista para almacenar los dos hijos resultantes del cruce
        List<Individuo> result = new ArrayList<>(2);

        // Crear dos nuevos individuos (hijos) utilizando la fábrica
        Individuo child1 = this.factory.createOne();
        Individuo child2 = this.factory.createOne();

        // Obtener el número de genes enteros de los padres
        int nIntGenes = parent1.getIntGenes().size();
        int nPossibleCuts = nIntGenes - 1;

        // Generar dos puntos de corte aleatorios para el cruce PMX
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

        // Mapeo de los segmentos cruzados
        // Estos mapas almacenan las correspondencias entre los genes de los padres
        Map<Integer, Integer> mapping1 = new HashMap<>();
        Map<Integer, Integer> mapping2 = new HashMap<>();

        // Recorrer el segmento entre los dos puntos de corte
        for (int i = randomValue1 + 1; i <= randomValue2; i++) {
            // Obtener los genes de los padres en la posición i
            int gene1 = parent1.getIntGenes().get(i).getFenotipo();
            int gene2 = parent2.getIntGenes().get(i).getFenotipo();

            // Asignar los genes cruzados a los hijos
            child1.getIntGenes().get(i).set(0, gene2);
            child2.getIntGenes().get(i).set(0, gene1);

            // Almacenar las correspondencias en los mapas
            mapping1.put(gene2, gene1);
            mapping2.put(gene1, gene2);
        }

        // Completar el resto de los genes fuera del segmento cruzado
        for (int i = 0; i < nIntGenes; i++) {
            if (i <= randomValue1 || i > randomValue2) {
                // Obtener los genes de los padres en la posición i
                int gene1 = parent1.getIntGenes().get(i).getFenotipo();
                int gene2 = parent2.getIntGenes().get(i).getFenotipo();

                // Resolver conflictos utilizando los mapas de correspondencia
                while (mapping1.containsKey(gene1)) {
                    gene1 = mapping1.get(gene1);
                }
                while (mapping2.containsKey(gene2)) {
                    gene2 = mapping2.get(gene2);
                }

                // Asignar los genes resueltos a los hijos
                child1.getIntGenes().get(i).set(0, gene1);
                child2.getIntGenes().get(i).set(0, gene2);
            }
        }

        // Agregar los hijos a la lista de resultados
        result.add(child1);
        result.add(child2);

        // Devolver la lista de hijos
        return result;
    }
}