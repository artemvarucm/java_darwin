package es.ucm.cross;

import es.ucm.individuos.Individuo;
import es.ucm.factories.IndividuoFactory;

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

        Individuo child1 = this.factory.createOne();
        Individuo child2 = this.factory.createOne();

        int nCrossPts = parent1.getNumberOfCrossPoints();
        int selectedPoint = ThreadLocalRandom.current().nextInt(0, nCrossPts);

        //System.out.println("PUNTO DE CRUCE: " + selectedPoint);

        for (int i = 0; i <= selectedPoint; i++) {
            child1.fillGenotypeElem(i, parent1);
            child2.fillGenotypeElem(i, parent2);
        }

        for (int i = selectedPoint + 1; i <= nCrossPts; i++) {
            child1.fillGenotypeElem(i, parent2);
            child2.fillGenotypeElem(i, parent1);
        }

        result.add(child1);
        result.add(child2);
        return result;
    }
}
