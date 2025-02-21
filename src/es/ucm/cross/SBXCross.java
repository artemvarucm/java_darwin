package es.ucm.cross;

import es.ucm.individuos.Individuo;
import es.ucm.factories.IndividuoFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class SBXCross extends AbstractCross {
    private double distributionIndex;

    public SBXCross(IndividuoFactory factory, double distributionIndex) {
        super(factory);
        this.distributionIndex = distributionIndex;
    }

    @Override
    public List<Individuo> cross(Individuo parent1, Individuo parent2) {
        List<Individuo> children = new ArrayList<>();
        Individuo child1 = factory.createOne();
        Individuo child2 = factory.createOne();

        for (int i = 0; i < parent1.getGenotipoLength(); i++) {
            double u = ThreadLocalRandom.current().nextDouble();
            double beta = (u <= 0.5) ?
                Math.pow(2 * u, 1 / (distributionIndex + 1)) :
                Math.pow(1 / (2 * (1 - u)), 1 / (distributionIndex + 1));

            double value1 = parent1.getGenotipoElem(i);
            double value2 = parent2.getGenotipoElem(i);

            child1.setGenotipoElem(i, 0.5 * ((value1 + value2) - beta * Math.abs(value2 - value1)));
            child2.setGenotipoElem(i, 0.5 * ((value1 + value2) + beta * Math.abs(value2 - value1)));
        }

        children.add(child1);
        children.add(child2);
        return children;
    }
}