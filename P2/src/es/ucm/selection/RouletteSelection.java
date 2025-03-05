package es.ucm.selection;

import es.ucm.factories.IndividuoFactory;
import es.ucm.individuos.Individuo;

import java.util.Collection;
import java.util.Collections;
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
        List<Double> normalizedFitness = getAdjustedAndNormalizedFitness(poblacion);

        for (int i = 0; i < poblacion.size(); i++) {
            double randomValue = ThreadLocalRandom.current().nextDouble(); // random entre 0 y 1
            double acumulado = 0;

            for (int ind = 0; ind < poblacion.size(); ind++) {
                acumulado += normalizedFitness.get(ind);
                if (acumulado >= randomValue || ind == poblacion.size() - 1) {
                    seleccionados.add(poblacion.get(ind).copy());
                    break;
                }
            }
        }

        return seleccionados;
    }
}