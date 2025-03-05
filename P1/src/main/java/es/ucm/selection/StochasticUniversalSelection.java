package es.ucm.selection;

import es.ucm.factories.IndividuoFactory;
import es.ucm.individuos.Individuo;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


public class StochasticUniversalSelection extends AbstractSelection {
    public StochasticUniversalSelection(IndividuoFactory factory) {
        super(factory);
    }

    @Override
    public List<Individuo> select(List<Individuo> poblacion) {
        List<Individuo> seleccionados = new ArrayList<>();
        List<Double> normalizedFitness = getAdjustedAndNormalizedFitness(poblacion);

        double distance = 1. / poblacion.size();
        double pointer = ThreadLocalRandom.current().nextDouble(distance);
        double acumulado = 0;
        int lastInd = 0;
        for (int i = 0; i < poblacion.size(); i++) {
            for (int ind = lastInd; ind < poblacion.size(); ind++) {
                if ((acumulado + normalizedFitness.get(ind)) >= pointer) {
                    seleccionados.add(poblacion.get(ind).copy());
                    lastInd = ind;
                    break;
                }
                acumulado += normalizedFitness.get(ind);
            }

            pointer += distance;
        }

        return seleccionados;
    }
}