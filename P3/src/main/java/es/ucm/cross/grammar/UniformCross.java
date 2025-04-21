package es.ucm.cross.grammar;

import es.ucm.cross.AbstractCross;
import es.ucm.individuos.Individuo;
import es.ucm.factories.IndividuoFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class UniformCross extends AbstractCross {
    private double swap_probability; // probabilidad de intercambio
    public UniformCross(IndividuoFactory factory, double swap_probability) {
        super(factory);
        this.swap_probability = swap_probability;
    }

    @Override
    public List<Individuo> cross(Individuo parent1, Individuo parent2) {
        List<Individuo> result = new ArrayList<>();
        Individuo child1 = factory.createOne();
        Individuo child2 = factory.createOne();

        /*for (int i = 0; i < parent1.getGenotipoLength(); i++) {
            double randomVal = ThreadLocalRandom.current().nextDouble();
            if (randomVal < swap_probability) {
                // intercambiamos
                child1.fillGenotypeElem(i, parent2);
                child2.fillGenotypeElem(i, parent1);
            } else {
                // dejamos igual
                child1.fillGenotypeElem(i, parent1);
                child2.fillGenotypeElem(i, parent2);
            }
        }*/

        result.add(child1);
        result.add(child2);
        return result;
    }
}