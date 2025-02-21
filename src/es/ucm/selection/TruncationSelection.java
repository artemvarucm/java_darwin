package es.ucm.selection;

import es.ucm.factories.IndividuoFactory;
import es.ucm.individuos.Individuo;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TruncationSelection extends AbstractSelection {
    private double truncationThreshold;

    public TruncationSelection(IndividuoFactory factory, double truncationThreshold) {
        super(factory);
        this.truncationThreshold = truncationThreshold;
    }

    @Override
    public List<Individuo> select(List<Individuo> poblacion) {
        int numSeleccionados = (int) (poblacion.size() * truncationThreshold);
        return poblacion.stream()
                .sorted(Comparator.comparingDouble(Individuo::getFitness).reversed())
                .limit(numSeleccionados)
                .map(Individuo::copy)
                .collect(Collectors.toList());
    }
}