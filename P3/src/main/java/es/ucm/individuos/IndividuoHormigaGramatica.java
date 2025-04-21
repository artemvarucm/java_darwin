package es.ucm.individuos;

import es.ucm.genes.TreeGen;
import es.ucm.individuos.tree.AbstractNode;
import es.ucm.individuos.tree.Coord;
import es.ucm.individuos.tree.DirectionEnum;
import es.ucm.individuos.tree.Hormiga;
import es.ucm.mapa.AbstractFoodMap;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static java.util.Objects.isNull;

/**
 * Maximizamos el número de trozos comidos
 */
public class IndividuoHormigaGramatica extends Individuo {
    private static Integer NUM_CODONES = 20; // el numero de genes
    protected AbstractFoodMap map;
    protected Double fitnessCache;
    protected Integer stepsLimit; // numero maximo de pasos (giros o avances)
    protected String genotipoStrCache;
    protected Integer nWraps;
    public IndividuoHormigaGramatica(AbstractFoodMap map, Integer stepsLimit, Integer nWraps) {
        super(null, true);
        this.map = map;
        this.nWraps = nWraps;
        this.stepsLimit = stepsLimit;
        initialize();
    }

    /**
     * Inicialización aleatoria (cada gen vale entre 0 y 255)
     */
    private void initialize() {
        for (int i = 0; i < NUM_CODONES; i++) {
            this.addIntegerGen(ThreadLocalRandom.current().nextInt(256));
        }
    }

    private List<Coord> lastPath; // Para guardar el último camino simulado
    private int lastSteps;
    private Coord lastPosition;
    private DirectionEnum lastDirection;

    @Override
    public double getFitness() {
        if (!isNull(fitnessCache) && this.genotipoToString().equals(genotipoStrCache)) {
            return this.fitnessCache;
        }

        Hormiga hormiga = new Hormiga(this.map, stepsLimit);
        lastPath = new LinkedList<>();
        lastPath.add(hormiga.getPosition()); // Posición inicial

        /*while (!hormiga.shouldStop()) {
            List<Coord> newPositions = this.getRootNode().walkAndReturnCoords(hormiga);
            lastPath.addAll(newPositions);
        }*/

        // Guardar resultados de la simulación
        this.lastSteps = hormiga.getSteps();
        this.lastPosition = hormiga.getPosition();
        this.lastDirection = hormiga.getDir();

        this.fitnessCache = (double) hormiga.getEatenFood();

        this.genotipoStrCache = this.genotipoToString();

        return this.fitnessCache;
    }

    @Override
    public Individuo copy() {
        Individuo clon = new IndividuoHormigaGramatica(this.map, this.stepsLimit, this.nWraps);
        this.copyToClone(clon);
        return clon;
    }

    // Métodos para la visualización
    /*
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
    }*/
}