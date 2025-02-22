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
        List<Individuo> result = new ArrayList<>();
        Individuo child1 = factory.createOne();
        Individuo child2 = factory.createOne();

        int nRealGenes = parent1.getRealGenes().size();
        for (int i = 0; i < nRealGenes; i++) {
            double value1 = parent1.getRealGenes().get(i).get(0);
            double value2 = parent2.getRealGenes().get(i).get(0);

            double power = 1. / (distributionIndex + 1);
            double randomVal = ThreadLocalRandom.current().nextDouble();
            double beta = (randomVal <= 0.5) ?
                    Math.pow(2 * randomVal, power) :
                    Math.pow(1 / (2 * (1 - randomVal)), power);


            child1.getRealGenes().get(i).set(0, 0.5 * ((1 + beta) * value1 + (1 - beta) * value2));
            child1.getRealGenes().get(i).set(0, 0.5 * ((1 - beta) * value1 + (1 + beta) * value2));
        }

        result.add(child1);
        result.add(child2);
        return result;
    }
}