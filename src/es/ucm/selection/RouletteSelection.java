package es.ucm.selection;

import es.ucm.factories.IndividuoFactory;
import es.ucm.individuos.Individuo;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class RouletteSelection extends AbstractSelection {
    public RouletteSelection(IndividuoFactory factory) {
        super(factory);
    }

    @Override
    public List<Individuo> select(List<Individuo> poblacion) {
        double totalFitness = poblacion.stream().mapToDouble(Individuo::getFitness).sum();
        List<Individuo> seleccionados = new ArrayList<>();

        for (int i = 0; i < poblacion.size(); i++) {
            double randomValue = ThreadLocalRandom.current().nextDouble() * totalFitness;
            double acumulado = 0;

            for (Individuo ind : poblacion) {
                acumulado += ind.getFitness();
                if (acumulado >= randomValue) {
                    seleccionados.add(ind.copy());
                    break;
                }
            }
        }

        return seleccionados;
    }
}