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

        double totalFitness = poblacion.stream().mapToDouble(Individuo::getFitness).sum();

        // Fitness normalizado, para que sume 1 la probabilidad de todos
        List<Double> normalizedFitness = poblacion.stream()
                .map(individuo -> individuo.getFitness() / totalFitness)
                .toList();

        double distance = totalFitness / poblacion.size();
        double pointer = ThreadLocalRandom.current().nextDouble(distance);
        double acumulado = 0;
        int lastInd = 0;
        for (int i = 0; i < poblacion.size(); i++) {
            pointer += distance;

            for (int ind = lastInd; ind < poblacion.size(); ind++) {
                if ((acumulado + normalizedFitness.get(ind)) >= pointer) {
                    seleccionados.add(poblacion.get(ind).copy());
                    lastInd = ind;
                    break;
                }
                acumulado += normalizedFitness.get(ind);
            }
        }

        return seleccionados;
    }
}