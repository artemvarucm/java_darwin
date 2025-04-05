package es.ucm.factories;

import es.ucm.individuos.Individuo;
import es.ucm.individuos.IndividuoHormiga;
import es.ucm.initializer.AbstractInitializer;
import es.ucm.mapa.AbstractFoodMap;


/**
 * Fábrica de Individuos, puede crear Individuos de tipo determinado, ej. Individuo1
 *
 * Se usa en el opeardor cruce y al crear la población inicial
 */
public class IndividuoHormigaFactory extends IndividuoFactory {
    private AbstractInitializer initializer;
    private Integer stepsLimit;
    public IndividuoHormigaFactory(AbstractFoodMap map, Integer stepsLimit, AbstractInitializer initializer) {
        super(map);
        this.initializer = initializer;
        this.stepsLimit = stepsLimit;
    }

    public Individuo createOne() {
        return new IndividuoHormiga(this.map, stepsLimit, initializer);
    }
}