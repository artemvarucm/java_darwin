package es.ucm.individuos;

import es.ucm.genes.TreeGen;
import es.ucm.individuos.tree.AbstractNode;
import es.ucm.individuos.tree.Hormiga;
import es.ucm.mapa.AbstractFoodMap;
import es.ucm.individuos.tree.Coord;
import es.ucm.individuos.tree.DirectionEnum;

import static java.util.Objects.isNull;

import java.util.LinkedList;
import java.util.List;

/**
 * Maximizamos el número de trozos comidos
 */
public class IndividuoHormigaArbol extends Individuo {
    protected AbstractFoodMap map;
    protected Double fitnessCache;
    protected Integer stepsLimit; // numero maximo de pasos (giros o avances)
    // protected String genotipoStrCache; NO HACE FALTA, SIEMPRE SE HACEN COPIAS. MEJORA SUSTANCIALMENTE EL RENDIMIENTO
    protected double bloatingFactor;
    public IndividuoHormigaArbol(AbstractFoodMap map, Integer stepsLimit, Double bloatingFactor) {
        this(map, stepsLimit, bloatingFactor, null);
    }

    public IndividuoHormigaArbol(AbstractFoodMap map, Integer stepsLimit, Double bloatingFactor, AbstractNode node) {
        super(null, true);
        this.map = map;
        this.stepsLimit = stepsLimit;
        this.bloatingFactor = bloatingFactor;

        if (!isNull(node)) {
            this.addTreeGen(node);
        }
    }

    private transient List<Coord> lastPath; // Para guardar el último camino simulado
    private transient int lastSteps;
    private transient Coord lastPosition;
    private transient DirectionEnum lastDirection;

    public double getOriginalFitness() {
        if (!isNull(fitnessCache)) {
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

        return this.fitnessCache;
    }

    @Override
    public double getFitness() {
        double fitness = getOriginalFitness();
        if (bloatingFactor != 0) {
            // penalizamos el fitness restando
            fitness -= bloatingFactor * this.getRootNode().getTreeSize();
        }

        return fitness;
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

    public int getStepsTaken() {
        if (lastPath == null) getFitness();
        return lastSteps;
    }

    @Override
    public Individuo copy() {
        Individuo clon = new IndividuoHormigaArbol(this.map, this.stepsLimit, this.bloatingFactor);
        this.copyToClone(clon);
        return clon;
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