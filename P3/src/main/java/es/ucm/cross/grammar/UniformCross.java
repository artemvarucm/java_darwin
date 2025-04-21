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
        Individuo child1 = parent1.copy();
        Individuo child2 = parent2.copy();

        for (int i = 0; i < parent1.getIntGenes().size(); i++) {
            double randomVal = ThreadLocalRandom.current().nextDouble();
            Integer parent1Gen = parent1.getIntGenes().get(i).getFenotipo();
            Integer parent2Gen = parent2.getIntGenes().get(i).getFenotipo();
            if (randomVal < swap_probability) {
                // intercambiamos
                child1.getIntGenes().get(i).set(0, parent2Gen);
                child2.getIntGenes().get(i).set(0, parent1Gen);
            }
        }

        result.add(child1);
        result.add(child2);
        return result;
    }
}