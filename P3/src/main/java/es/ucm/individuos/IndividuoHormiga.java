package es.ucm.individuos;

import es.ucm.genes.TreeGen;
import es.ucm.individuos.arbol.AbstractNode;
import es.ucm.individuos.arbol.Hormiga;
import es.ucm.individuos.arbol.ForwardNode;
import es.ucm.individuos.arbol.LeftNode;
import es.ucm.initializer.AbstractInitializer;
import es.ucm.mapa.AbstractFoodMap;
import es.ucm.mapa.SantaFeMap;
import es.ucm.individuos.arbol.Coord;
import es.ucm.individuos.arbol.DirectionEnum;

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

    private transient List<Coord> lastPath; // Para guardar el último camino simulado
    private transient int lastSteps;
    private transient Coord lastPosition;
    private transient DirectionEnum lastDirection;

    @Override
    public double getFitness() {
        if (!isNull(fitnessCache) && this.genotipoToString().equals(genotipoStrCache)) {
            return this.fitnessCache;
        }

        
        Hormiga hormiga = new Hormiga(this.map, stepsLimit);
        lastPath = new LinkedList<>();
        lastPath.add(hormiga.getPosition()); // Posición inicial
        
        while (!hormiga.shouldStop()) {
            List<Coord> newPositions = this.getRootNode().walkAndReturnCoords(hormiga);
            lastPath.addAll(newPositions);
        }

        // Guardar resultados de la simulación
        this.lastSteps = hormiga.getSteps();
        this.lastPosition = hormiga.getPosition();
        this.lastDirection = hormiga.getDir();
        
        this.fitnessCache = (double) hormiga.getEatenFood();
        this.genotipoStrCache = this.genotipoToString();

        return this.fitnessCache;
    }

    // Métodos para la visualización
    public Coord getCurrentPosition() {
        if (lastPath == null) getFitness(); // Fuerza simulación si es necesario
        return lastPosition;
    }

    public DirectionEnum getCurrentDirection() {
        if (lastPath == null) getFitness();
        return lastDirection;
    }

    public List<Coord> getPathHistory() {
        if (lastPath == null) getFitness();
        return new LinkedList<>(lastPath);
    }

    public int getFoodCollected() {
        if (lastPath == null) getFitness();
        return (int) getFitness(); // Usamos el fitness que ya es la comida recolectada
    }

    public int getStepsTaken() {
        if (lastPath == null) getFitness();
        return lastSteps;
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
    
    public int getTreeDepth() {
        return getRootNode().getDepth();
    }
    
    public int getNodeCount() {
        return getRootNode().getTreeSize();
    }
    
    public String getExpressionString() {
        return getRootNode().toString();
    }

    public AbstractNode getRootNode() {
        return ((TreeGen) this.genes.get(0)).getFenotipo();
    }
}