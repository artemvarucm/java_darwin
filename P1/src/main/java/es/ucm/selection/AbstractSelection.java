package es.ucm.selection;

import es.ucm.factories.IndividuoFactory;
import es.ucm.individuos.Individuo;

import java.util.Collections;
import java.util.List;

public abstract class AbstractSelection {
    protected IndividuoFactory factory;
    public AbstractSelection(IndividuoFactory factory) {
        this.factory = factory;
    }

    /**
     * Selecciona individuos de la población, pudiendo ser con repetición
     * Devuelve una lista de individuos con el mismo tamaño que la lista pasada por parámetro
     */
    public abstract List<Individuo> select(List<Individuo> poblacion);

    /**
     * Devuelve el fitness desplazado y normalizado
     */
    public List<Double> getAdjustedAndNormalizedFitness(List<Individuo> poblacion) {
        List<Double> fitness = poblacion.stream().map(Individuo::getFitness).toList();
        double minFitness = Collections.min(fitness);
        double maxFitness = Collections.max(fitness);

        List<Double> positiveFitness = poblacion.stream().map(ind -> ind.getAdjustedFitness(minFitness, maxFitness)).toList();

        // Fitness normalizado, para que sume 1 la probabilidad de todos
        double totalFitness = positiveFitness.stream().mapToDouble(ind -> ind).sum();
        return positiveFitness.stream().map(ind -> ind / totalFitness).toList();
    }
}
