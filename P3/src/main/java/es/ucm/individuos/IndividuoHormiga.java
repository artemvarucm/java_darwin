package es.ucm.individuos;

import es.ucm.genes.TreeGen;
import es.ucm.individuos.arbol.AbstractNode;
import es.ucm.individuos.arbol.Hormiga;
import es.ucm.individuos.arbol.ForwardNode;
import es.ucm.individuos.arbol.LeftNode;
import es.ucm.individuos.arbol.Prog2Node;
import es.ucm.initializer.AbstractInitializer;
import es.ucm.mapa.AbstractFoodMap;
import es.ucm.mapa.SantaFeMap;
import es.ucm.individuos.arbol.Coord;


import static java.util.Objects.isNull;

import java.util.LinkedList;
import java.util.List;

/**
 * Maximizamos el número de trozos comidos
 */
public class IndividuoHormiga extends Individuo {
    protected AbstractFoodMap map;
    protected Double fitnessCache;
    protected Integer stepsLimit; // numero maximo de pasos (giros o avances)
    protected String genotipoStrCache;

    public IndividuoHormiga(AbstractFoodMap map, Integer stepsLimit) {
        this(map, stepsLimit, null);
    }

    public IndividuoHormiga(AbstractFoodMap map, Integer stepsLimit, AbstractInitializer initializer) {
        super(null, true);
        this.map = map;
        this.stepsLimit = stepsLimit;

        if (!isNull(initializer)) {
            this.addTreeGen(initializer.initialize());
        }
    }

    @Override
    public double getFitness() {
        if (!isNull(fitnessCache) && this.genotipoToString().equals(genotipoStrCache)) {
            return this.fitnessCache;
        }

        // Ejecutar el programa
        Hormiga hormiga = new Hormiga(this.map, stepsLimit);
        List<Coord> path = new LinkedList<>();
        while (!hormiga.shouldStop()) {
            path.addAll(this.getRootNode().walkAndReturnCoords(hormiga));
        }

        this.fitnessCache = (double) (hormiga.getEatenFood());

        this.genotipoStrCache = this.genotipoToString();

        return this.fitnessCache;
    }

    @Override
    public Individuo copy() {
        Individuo clon = new IndividuoHormiga(this.map, this.stepsLimit);
        this.copyToClone(clon);
        return clon;
    }
    
    /**
     * Establece el valor de fitness (usado para el control de bloating)
     */
    public void setFitness(double fitness) {
        this.fitnessCache = fitness;
        // Invalida la caché del genotipo para forzar recálculo
        this.genotipoStrCache = null; 
    }

    public AbstractNode getRootNode() {
        return ((TreeGen) this.genes.get(0)).getFenotipo();
    }
}