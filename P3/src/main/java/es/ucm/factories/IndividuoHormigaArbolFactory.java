package es.ucm.factories;

import java.util.*;

import es.ucm.individuos.Individuo;
import es.ucm.individuos.IndividuoHormigaArbol;
import es.ucm.individuos.tree.AbstractNode;
import es.ucm.initializer.AbstractInitializer;
import es.ucm.mapa.AbstractFoodMap;


/**
 * Fábrica de Individuos, puede crear Individuos de tipo determinado, ej. Individuo1
 *
 * Se usa en el opeardor cruce y al crear la población inicial
 */
public class IndividuoHormigaArbolFactory extends IndividuoFactory {
    private AbstractInitializer initializer;
    private Integer stepsLimit;
    private Double bloatingFactor;
    public IndividuoHormigaArbolFactory(AbstractFoodMap map, Integer stepsLimit, Double bloatingFactor, AbstractInitializer initializer) {
        super(map);
        this.initializer = initializer;
        this.stepsLimit = stepsLimit;
        this.bloatingFactor = bloatingFactor;
    }

    public Individuo createOne() {
        AbstractNode node = initializer.initialize();

        return new IndividuoHormigaArbol(this.map, this.stepsLimit, bloatingFactor, node);
    }
    
    @Override
    public List<Individuo> createMany(int tamPoblacion) {
        List<Individuo> individuos = new ArrayList<>(tamPoblacion);

        for (AbstractNode node : initializer.initializeN(tamPoblacion)) {
            IndividuoHormigaArbol individuo = new IndividuoHormigaArbol(this.map, this.stepsLimit, bloatingFactor, node);
            individuos.add(individuo);
        }

        return individuos;
    }
}