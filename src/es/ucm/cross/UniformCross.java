package es.ucm.cross;

import es.ucm.individuos.Individuo;
import es.ucm.factories.IndividuoFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class UniformCross extends AbstractCross {
    public UniformCross(IndividuoFactory factory) {
        super(factory);
    }

    @Override
    public List<Individuo> cross(Individuo parent1, Individuo parent2) {
        List<Individuo> children = new ArrayList<>();
        Individuo child1 = factory.createOne();
        Individuo child2 = factory.createOne();

        for (int i = 0; i < parent1.getGenotipoLength(); i++) {
            if (ThreadLocalRandom.current().nextBoolean()) {
                child1.fillGenotypeElem(i, parent1);
                child2.fillGenotypeElem(i, parent2);
            } else {
                child1.fillGenotypeElem(i, parent2);
                child2.fillGenotypeElem(i, parent1);
            }
        }

        children.add(child1);
        children.add(child2);
        return children;
    }
}