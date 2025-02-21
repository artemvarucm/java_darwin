package es.ucm.selection;

import es.ucm.factories.IndividuoFactory;
import es.ucm.individuos.Individuo;

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
}
