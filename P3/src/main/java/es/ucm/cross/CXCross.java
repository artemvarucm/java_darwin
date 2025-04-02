package es.ucm.cross;

import es.ucm.factories.IndividuoFactory;
import es.ucm.individuos.Individuo;

import java.util.*;

public class CXCross extends AbstractCross {

    public CXCross(IndividuoFactory factory) {
        super(factory);
    }

    @Override
    public List<Individuo> cross(Individuo parent1, Individuo parent2) {
        List<Individuo> result = new ArrayList<>(2);
        // empiezan igual que los padres (solo tenemos que aplicar el ciclo)
        Individuo child1 = parent2.copy();
        Individuo child2 = parent1.copy();

        // rellenamos los hijos
        fillCycle(parent2, parent1, child1);
        fillCycle(parent1, parent2, child2);

        result.add(child1);
        result.add(child2);
        return result;
    }

    /**
     *
     * @param parentMap - el padre que se usa para el mapping
     * @param parentFrom - el padre del cual se cogen los valores
     * @param child - el hijo que se actualiza
     */
    private void fillCycle(Individuo parentMap, Individuo parentFrom, Individuo child) {
        // diccionario inverso dado el valor devuelve la posicion en la que está (hecho para parentFrom)
        Map<Integer, Integer> inversePar = new HashMap<>();
        int nIntGenes = parentFrom.getIntGenes().size();
        for (int i = 0; i < nIntGenes; i++) {
            int gen = parentFrom.getIntGenes().get(i).getFenotipo();
            inversePar.put(gen, i);
        }

        int cycleInd = 0;
        Set<Integer> childSet = new HashSet<>();
        while (!childSet.contains(cycleInd)) {
            // el gen del valor que vamos a guardar
            int geneValue = parentFrom.getIntGenes().get(cycleInd).getFenotipo();
            child.getIntGenes().get(cycleInd).set(0, geneValue);

            // el gen mapeado del otro padre
            int geneMap = parentMap.getIntGenes().get(cycleInd).getFenotipo();
            childSet.add(cycleInd);


            cycleInd = inversePar.get(geneMap);
        }
    }
}