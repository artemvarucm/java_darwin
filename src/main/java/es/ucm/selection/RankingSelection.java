package es.ucm.selection;

import es.ucm.factories.IndividuoFactory;
import es.ucm.individuos.Individuo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RankingSelection extends AbstractSelection {

    public RankingSelection(IndividuoFactory factory) {
        super(factory);
    }

    @Override
    public List<Individuo> select(List<Individuo> poblacion) {
        List<Individuo> seleccionados = new ArrayList<>();
        if (poblacion.isEmpty()) {
            return seleccionados;
        } else if (poblacion.size() == 1) {
            seleccionados.add(poblacion.get(0).copy());
            return seleccionados;
        }

        // Ordenamos por el fitness
        if (poblacion.get(0).getMaximizar()) {
            poblacion.sort(Comparator.comparingDouble(Individuo::getFitness).reversed());
        } else {
            poblacion.sort(Comparator.comparingDouble(Individuo::getFitness));
        }

        List<Double> normalizedFitness = getAdjustedAndNormalizedFitness(poblacion);
        double totalFitness = normalizedFitness.stream().mapToDouble(ind -> ind).sum();
        double avgFitness = totalFitness / poblacion.size();
        double presionSelectiva = normalizedFitness.get(0) / avgFitness; // best / avg

        for (int i = 0; i < poblacion.size(); i++) {
            double randomValue = ThreadLocalRandom.current().nextDouble(); // random entre 0 y 1
            double acumulado = 0;

            for (int ind = 0; ind < poblacion.size(); ind++) {
                // Probabilidad segun ranking
                acumulado += rankingProb(ind, poblacion.size(), presionSelectiva);
                if (acumulado >= randomValue || ind == poblacion.size() - 1) {
                    seleccionados.add(poblacion.get(ind).copy());
                    break;
                }
            }
        }


        return seleccionados;
    }

    private double rankingProb(int i, int size, double presion) {
        return (presion - 2 * (presion - 1) * (i - 1) / (size - 1)) / size;
    }
}