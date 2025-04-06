package es.ucm.factories;

import java.util.*;

import es.ucm.individuos.Individuo;
import es.ucm.individuos.IndividuoHormiga;
import es.ucm.individuos.arbol.AbstractNode;
import es.ucm.initializer.AbstractInitializer;
import es.ucm.initializer.RampedHalfInitializer;
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
    
    @Override
    public List<Individuo> createMany(int tamPoblacion) {
        List<Individuo> individuos = new ArrayList<>(tamPoblacion);
        
        if (initializer instanceof RampedHalfInitializer) {
            // Usar el método especial para Ramped Half-and-Half
            List<AbstractNode> arboles = RampedHalfInitializer.createInitialPopulation(
                tamPoblacion, initializer.getMaxDepth());
            
            for (AbstractNode arbol : arboles) {
                IndividuoHormiga individuo = new IndividuoHormiga(this.map, this.stepsLimit);
                individuo.addTreeGen(arbol);
                individuos.add(individuo);
            }
        } else {
            // Método normal para otros inicializadores
            for (int i = 0; i < tamPoblacion; i++) {
                individuos.add(createOne());
            }
        }
        
        return individuos;
    }
}