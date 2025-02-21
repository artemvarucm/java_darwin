package es.ucm.cross;

import es.ucm.individuos.Individuo;
import es.ucm.factories.IndividuoFactory;
import java.util.ArrayList;
import java.util.List;

public class ArithmeticCross extends AbstractCross {
    public ArithmeticCross(IndividuoFactory factory) {
        super(factory);
    }

    @Override
    public List<Individuo> cross(Individuo parent1, Individuo parent2) {
        List<Individuo> children = new ArrayList<>();
        Individuo child1 = factory.createOne();
        Individuo child2 = factory.createOne();

        for (int i = 0; i < parent1.getGenotipoLength(); i++) {
            double value1 = parent1.getGenotipoElem(i);
            double value2 = parent2.getGenotipoElem(i);
            double alpha = 0.5; // Factor de mezcla

            child1.setGenotipoElem(i, alpha * value1 + (1 - alpha) * value2);
            child2.setGenotipoElem(i, alpha * value2 + (1 - alpha) * value1);
        }

        children.add(child1);
        children.add(child2);
        return children;
    }
}