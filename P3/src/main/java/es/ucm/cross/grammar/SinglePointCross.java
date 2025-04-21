package es.ucm.cross.grammar;

import es.ucm.cross.AbstractCross;
import es.ucm.individuos.Individuo;
import es.ucm.factories.IndividuoFactory;
import es.ucm.individuos.IndividuoHormigaGramatica;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Cruce de un punto
 */
public class SinglePointCross extends AbstractCross {
    public SinglePointCross(IndividuoFactory factory) {
        super(factory);
    }

    /**
     * Recibe los 2 padres, genera un punto de cruce aleatorio
     * Devuelve 2 hijos,
     * el primero tiene la primera parte del padre1, la segunda del padre2
     * el segundo tiene la primera parte del padre2, la segunda del padre1
     *
     * Ej.
     * padre -> 0010
     * padre2 -> 1000
     * punto cruce aleatorio -> 1
     * Resultado:
     * hijo1 -> 0000
     * hijo2 -> 1010
     *
     */
    public List<Individuo> cross(Individuo parent1, Individuo parent2) {
        List<Individuo> result = new ArrayList<>(2);

        Individuo child1 = parent1.copy();
        Individuo child2 = parent2.copy();

        int nCrossPts = parent1.getIntGenes().size() - 1;
        int selectedPoint = ThreadLocalRandom.current().nextInt(0, nCrossPts);

        //System.out.println("PUNTO DE CRUCE: " + selectedPoint);

        for (int i = selectedPoint + 1; i <= nCrossPts; i++) {
            // invertimos genes
            child1.getIntGenes().get(i).set(0, parent2.getIntGenes().get(i).getFenotipo());
            child2.getIntGenes().get(i).set(0, parent1.getIntGenes().get(i).getFenotipo());
        }

        result.add(child1);
        result.add(child2);
        return result;
    }
}
