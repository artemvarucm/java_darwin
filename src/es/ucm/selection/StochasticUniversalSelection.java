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
        double totalFitness = poblacion.stream().mapToDouble(Individuo::getFitness).sum();
        double distance = totalFitness / poblacion.size();
        double start = ThreadLocalRandom.current().nextDouble() * distance;
        List<Individuo> seleccionados = new ArrayList<>();

        double acumulado = 0;
        int index = 0;

        for (int i = 0; i < poblacion.size(); i++) {
            double pointer = start + i * distance;

            while (acumulado < pointer) {
                acumulado += poblacion.get(index).getFitness();
                index = (index + 1) % poblacion.size();
            }

            seleccionados.add(poblacion.get(index).copy());
        }

        return seleccionados;
    }
}