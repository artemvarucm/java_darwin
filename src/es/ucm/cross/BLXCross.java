package es.ucm.cross;

import es.ucm.individuos.Individuo;
import es.ucm.factories.IndividuoFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class BLXCross extends AbstractCross {
    private double alpha;

    public BLXCross(IndividuoFactory factory, double alpha) {
        super(factory);
        this.alpha = alpha;
    }

    @Override
    public List<Individuo> cross(Individuo parent1, Individuo parent2) {
        List<Individuo> children = new ArrayList<>();
        Individuo child1 = factory.createOne();
        Individuo child2 = factory.createOne();

        /*for (int i = 0; i < parent1.getGenotipoLength(); i++) {
            double value1 = parent1.getGenotipoElem(i);
            double value2 = parent2.getGenotipoElem(i);
            double min = Math.min(value1, value2) - alpha * Math.abs(value2 - value1);
            double max = Math.max(value1, value2) + alpha * Math.abs(value2 - value1);

            child1.setGenotipoElem(i, ThreadLocalRandom.current().nextDouble(min, max));
            child2.setGenotipoElem(i, ThreadLocalRandom.current().nextDouble(min, max));
        }*/

        children.add(child1);
        children.add(child2);
        return children;
    }
}