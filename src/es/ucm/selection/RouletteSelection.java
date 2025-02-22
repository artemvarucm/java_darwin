package es.ucm.selection;

import es.ucm.factories.IndividuoFactory;
import es.ucm.individuos.Individuo;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class RouletteSelection extends AbstractSelection {
    public RouletteSelection(IndividuoFactory factory) {
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

        for (int i = 0; i < poblacion.size(); i++) {
            double randomValue = ThreadLocalRandom.current().nextDouble(); // random entre 0 y 1
            double acumulado = 0;

            for (int ind = 0; ind < poblacion.size(); ind++) {
                acumulado += normalizedFitness.get(ind);
                if (acumulado >= randomValue) {
                    seleccionados.add(poblacion.get(ind).copy());
                    break;
                }
            }
        }

        return seleccionados;
    }
}