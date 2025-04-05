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

import java.util.List;

public class IndividuoHormiga extends Individuo {
    protected AbstractFoodMap map;
    protected Double fitnessCache;
    protected String genotipoStrCache;

    public IndividuoHormiga(AbstractFoodMap map, AbstractInitializer initializer) {
        this(map);
        this.addTreeGen(initializer.initialize());
    }

    public IndividuoHormiga(AbstractFoodMap map) {
        super(null, false);
        this.map = map;
    }

    @Override
    public double getFitness() {
        if (!isNull(fitnessCache) && this.genotipoToString().equals(genotipoStrCache)) {
            return this.fitnessCache;
        }
        /*
        // Resetear el mapa y crear nueva hormiga
        this.map.reset();
        Hormiga hormiga = new Hormiga(this.map);
        
        // Ejecutar el programa
        List<Coord> path = this.getRootNode().walkAndReturnCoords(hormiga);
        
        // Limitar a 400 pasos
        int steps = Math.min(path.size(), 400);
        this.fitnessCache = (double) (map.getInitialFoodCount() - map.getCurrentFoodCount());
         */
        this.fitnessCache = 0.;
        this.genotipoStrCache = this.genotipoToString();

        return this.fitnessCache;
    }

    @Override
    public Individuo copy() {
        Individuo clon = new IndividuoHormiga(this.map);
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