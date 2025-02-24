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
        List<Individuo> seleccionados = new ArrayList<>();
        List<Individuo> segunda_vuelta = new ArrayList<>(); // los que no se seleccionaron por restos
        List<Double> normalizedFitness = getAdjustedAndNormalizedFitness(poblacion);
        // SELECCION POR RESTOS DE LOS QUE SE PUEDA
        for (int i = 0; i < poblacion.size(); i++) {
            int nCopias = (int) (normalizedFitness.get(i) * poblacion.size());
            if (nCopias == 0) {
                segunda_vuelta.add(poblacion.get(i));
            } else {
                for (int c = 0; c < nCopias; c++) {
                    seleccionados.add(poblacion.get(i).copy());
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