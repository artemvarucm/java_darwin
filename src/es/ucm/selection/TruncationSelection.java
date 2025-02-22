package es.ucm.selection;

import es.ucm.factories.IndividuoFactory;
import es.ucm.individuos.Individuo;

import java.util.ArrayList;
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
        List<Individuo> seleccionados = new ArrayList<>();
        int limitePosicion = (int) (poblacion.size() * truncationThreshold);

        // Ordenamos por el fitness
        poblacion.sort(Comparator.comparingDouble(Individuo::getFitness).reversed());

        for (int i = 0; i < poblacion.size(); i++) {
            // Solo seleccionamos individuos por encima del límite
            // (ej. si es al 50%, solo de la primera mitad)
            seleccionados.add(poblacion.get(i % limitePosicion).copy());
        }

        return seleccionados;
    }
}