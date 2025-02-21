package es.ucm.selection;

import es.ucm.factories.IndividuoFactory;
import es.ucm.individuos.Individuo;
import java.util.ArrayList;
import java.util.List;

public class RemainderSelection extends AbstractSelection {
    public RemainderSelection(IndividuoFactory factory) {
        super(factory);
    }

    @Override
    public List<Individuo> select(List<Individuo> poblacion) {
        double totalFitness = poblacion.stream().mapToDouble(Individuo::getFitness).sum();
        List<Individuo> seleccionados = new ArrayList<>();

        for (Individuo ind : poblacion) {
            int copies = (int) (ind.getFitness() / totalFitness * poblacion.size());
            for (int i = 0; i < copies; i++) {
                seleccionados.add(ind.copy());
            }
        }

        return seleccionados;
    }
}