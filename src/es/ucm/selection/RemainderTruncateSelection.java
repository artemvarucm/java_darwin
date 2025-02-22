package es.ucm.selection;

import es.ucm.factories.IndividuoFactory;
import es.ucm.individuos.Individuo;
import java.util.ArrayList;
import java.util.List;

public class RemainderTruncateSelection extends AbstractSelection {

    private TruncationSelection truncateSelection;
    public RemainderTruncateSelection(IndividuoFactory factory, double truncationThreshold) {
        super(factory);
        this.truncateSelection = new TruncationSelection(factory, truncationThreshold);
    }

    @Override
    public List<Individuo> select(List<Individuo> poblacion) {
        double totalFitness = poblacion.stream().mapToDouble(Individuo::getFitness).sum();
        List<Individuo> seleccionados = new ArrayList<>();
        List<Individuo> segunda_vuelta = new ArrayList<>(); // los que no se seleccionaron por restos

        // SELECCION POR RESTOS DE LOS QUE SE PUEDA
        for (Individuo ind : poblacion) {
            double fitnessNormalizado = ind.getFitness() / totalFitness;
            int nCopias = (int) (fitnessNormalizado * poblacion.size());
            if (nCopias == 0) {
                segunda_vuelta.add(ind);
            } else {
                for (int i = 0; i < nCopias; i++) {
                    seleccionados.add(ind.copy());
                }
            }
        }

        // Truncamiento para rellenar la poblacion
        if (!segunda_vuelta.isEmpty()) {
            seleccionados.addAll(truncateSelection.select(segunda_vuelta));
        }

        return seleccionados;
    }
}