package es.ucm.cross;

import es.ucm.individuos.Individuo;
import es.ucm.factories.IndividuoFactory;
import java.util.ArrayList;
import java.util.List;

/**
 * IMPORTANTE: SOLO CRUZA GENES REALES, LOS BOOLEANS LOS INICIALIZA RANDOM !
 */
public class ArithmeticCross extends AbstractCross {
    private double alpha;
    public ArithmeticCross(IndividuoFactory factory, double alpha) {
        super(factory);
        this.alpha = alpha;
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

            child1.getRealGenes().get(i).set(i, alpha * value1 + (1 - alpha) * value2);
            child2.getRealGenes().get(i).set(i, (1 - alpha) * value1 + alpha * value2);
        }

        result.add(child1);
        result.add(child2);
        return result;
    }
}