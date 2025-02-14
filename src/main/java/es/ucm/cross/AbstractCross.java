package es.ucm.cross;

import es.ucm.individuos.Individuo;
import es.ucm.factories.IndividuoFactory;

import java.util.List;

abstract class AbstractCross {
    protected IndividuoFactory factory;
    public AbstractCross(IndividuoFactory factory) {
        this.factory = factory;
    }

    /**
     * Cruza los 2 padres para devolver 2 hijos generados
     */
    public abstract List<Individuo> cross(Individuo parent1, Individuo parent2);
}
