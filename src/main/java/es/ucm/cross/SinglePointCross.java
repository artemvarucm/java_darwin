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

    public List<Individuo> cross(Individuo parent1, Individuo parent2) {
        List<Individuo> result = new ArrayList<>(2);

        Individuo child1 = this.factory.createOne();
        Individuo child2 = this.factory.createOne();

        int nCrossPts = parent1.getNumberOfCrossPoints();
        int selectedPoint = ThreadLocalRandom.current().nextInt(0, nCrossPts);

        System.out.println("PUNTO DE CRUCE: " + selectedPoint);

        for (int i = 0; i <= selectedPoint; i++) {
            child1.copyGenomeElem(i, parent1);
            child2.copyGenomeElem(i, parent2);
        }

        for (int i = selectedPoint + 1; i <= nCrossPts; i++) {
            child1.copyGenomeElem(i, parent2);
            child2.copyGenomeElem(i, parent1);
        }

        result.add(child1);
        result.add(child2);
        return result;
    }
}
