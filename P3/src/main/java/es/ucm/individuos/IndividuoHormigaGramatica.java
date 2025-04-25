package es.ucm.individuos;

import es.ucm.genes.IntegerGen;
import es.ucm.genes.TreeGen;
import es.ucm.individuos.grammar.AbstractGrammarElem;
import es.ucm.individuos.grammar.Grammar;
import es.ucm.individuos.tree.AbstractNode;
import es.ucm.individuos.tree.Coord;
import es.ucm.individuos.tree.DirectionEnum;
import es.ucm.individuos.tree.Hormiga;
import es.ucm.mapa.AbstractFoodMap;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static java.util.Objects.isNull;

/**
 * Maximizamos el número de trozos comidos
 */
public class IndividuoHormigaGramatica extends Individuo {
    private Integer numCodones; // el numero de genes
    protected AbstractFoodMap map;
    protected Double fitnessCache;
    protected Integer stepsLimit; // numero maximo de pasos (giros o avances)
    // protected String genotipoStrCache; NO HACE FALTA, SIEMPRE SE HACEN COPIAS. MEJORA SUSTANCIALMENTE EL RENDIMIENTO
    protected Integer maxWraps;
    public IndividuoHormigaGramatica(AbstractFoodMap map, Integer stepsLimit, Integer maxWraps, Integer numCodones) {
        super(null, true);
        this.map = map;
        this.numCodones = numCodones;
        this.maxWraps = maxWraps;
        this.stepsLimit = stepsLimit;
        initialize();
    }

    /**
     * Inicialización aleatoria (cada gen vale entre 0 y 255)
     */
    private void initialize() {
        for (int i = 0; i < numCodones; i++) {
            this.addIntegerGen(ThreadLocalRandom.current().nextInt(256));
        }
    }

    private List<Coord> lastPath; // Para guardar el último camino simulado
    private int lastSteps;
    private Coord lastPosition;
    private DirectionEnum lastDirection;
    private List<AbstractGrammarElem> decodedCode; // solucion decodificada

    @Override
    public double getFitness() {
        if (!isNull(fitnessCache)) {// && this.genotipoToString().equals(genotipoStrCache)) {
            return this.fitnessCache;
        }

        Hormiga hormiga = new Hormiga(this.map, stepsLimit);
        lastPath = new LinkedList<>();
        lastPath.add(hormiga.getPosition()); // Posición inicial

        List<Integer> intValues = new ArrayList<>();
        for (IntegerGen gen: this.getIntGenes()) {
            intValues.add(gen.getFenotipo());
        }

        decodedCode = (new Grammar(maxWraps)).decode(intValues);
        if (decodedCode != null) {
            while (!hormiga.shouldStop()) {
                for (AbstractGrammarElem el : decodedCode) {
                    List<Coord> newPositions = el.walkAndReturnCoords(hormiga);
                    lastPath.addAll(newPositions);
                }
            }
        }

        // Guardar resultados de la simulación
        this.lastSteps = hormiga.getSteps();
        this.lastPosition = hormiga.getPosition();
        this.lastDirection = hormiga.getDir();

        this.fitnessCache = (double) hormiga.getEatenFood();

        //this.genotipoStrCache = this.genotipoToString();

        return this.fitnessCache;
    }

    @Override
    public Individuo copy() {
        Individuo clon = new IndividuoHormigaGramatica(this.map, this.stepsLimit, this.maxWraps, this.numCodones);
        this.copyToClone(clon);
        return clon;
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

    public String getExpressionString() {
        StringBuilder res = new StringBuilder();
        for (AbstractGrammarElem el: decodedCode) {
            res.append(el.toString());
        }

        return res.toString();
    }

    public Integer numLinesOfCode() {
        return getExpressionString().split("\r\n|\r|\n").length;
    }
}