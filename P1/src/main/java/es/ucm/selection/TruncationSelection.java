package es.ucm.selection;

import es.ucm.factories.IndividuoFactory;
import es.ucm.individuos.Individuo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TruncationSelection extends AbstractSelection {
    private double truncationThreshold;

    public TruncationSelection(IndividuoFactory factory, double truncationThreshold) {
        super(factory);
        this.truncationThreshold = truncationThreshold;
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

        int limitePosicion = (int) (poblacion.size() * truncationThreshold);

        // Ordenamos por el fitness
        if (poblacion.get(0).getMaximizar()) {
            poblacion.sort(Comparator.comparingDouble(Individuo::getFitness).reversed());
        } else {
            poblacion.sort(Comparator.comparingDouble(Individuo::getFitness));
        }

        for (int i = 0; i < poblacion.size(); i++) {
            // Solo seleccionamos individuos por encima del límite
            // (ej. si es al 50%, solo de la primera mitad)
            seleccionados.add(poblacion.get(i % limitePosicion).copy());
        }

        return seleccionados;
    }
}