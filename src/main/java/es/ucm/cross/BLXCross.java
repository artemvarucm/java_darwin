package es.ucm.cross;

import es.ucm.individuos.Individuo;
import es.ucm.factories.IndividuoFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * IMPORTANTE: SOLO CRUZA GENES REALES, LOS BOOLEANS LOS INICIALIZA RANDOM !
 */
public class BLXCross extends AbstractCross {
    private double alpha;

    public BLXCross(IndividuoFactory factory, double alpha) {
        super(factory);
        this.alpha = alpha;
    }

    @Override
    public List<Individuo> cross(Individuo parent1, Individuo parent2) {
        List<Individuo> result = new ArrayList<>();
        Individuo child1 = factory.createOne();
        Individuo child2 = factory.createOne();
        System.out.println("CRUCE");
        int nRealGenes = parent1.getRealGenes().size();
        for (int i = 0; i < nRealGenes; i++) {
            double value1 = parent1.getRealGenes().get(i).get(0);
            double value2 = parent2.getRealGenes().get(i).get(0);

            double cMin = Math.min(value1, value2);
            double cMax = Math.max(value1, value2);

            double lowerBound = cMin - (cMax - cMin) * alpha;
            double upperBound = cMax + (cMax - cMin) * alpha;
            child1.getRealGenes().get(i).set(0, lowerBound == upperBound ? lowerBound : ThreadLocalRandom.current().nextDouble(lowerBound, upperBound));
            child2.getRealGenes().get(i).set(0, lowerBound == upperBound ? lowerBound : ThreadLocalRandom.current().nextDouble(lowerBound, upperBound));
        }

        result.add(child1);
        result.add(child2);
        return result;
    }
}