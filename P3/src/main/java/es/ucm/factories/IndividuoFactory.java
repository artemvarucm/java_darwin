package es.ucm.factories;

import es.ucm.individuos.Individuo;
import es.ucm.mapa.AbstractFoodMap;

import java.util.ArrayList;
import java.util.List;

/**
 * Fábrica de Individuos, puede crear Individuos de tipo determinado, ej. Individuo1
 *
 * Se usa en el opeardor cruce y al crear la población inicial
 */
public abstract class IndividuoFactory {
    protected AbstractFoodMap map;
    public IndividuoFactory(AbstractFoodMap map) {
        this.map = map;
    }

    public abstract Individuo createOne();

    public List<Individuo> createMany(int tam) {
        List<Individuo> individuos = new ArrayList<>(tam);
        for (int i = 0; i < tam; i++) {
            individuos.add(createOne());
        }

        return individuos;
    }
}
