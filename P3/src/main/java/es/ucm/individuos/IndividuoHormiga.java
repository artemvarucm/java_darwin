package es.ucm.individuos;

import es.ucm.genes.TreeGen;
import es.ucm.individuos.arbol.AbstractNode;
import es.ucm.individuos.arbol.ForwardNode;
import es.ucm.individuos.arbol.LeftNode;
import es.ucm.individuos.arbol.Prog2Node;
import es.ucm.initializer.AbstractInitializer;
import es.ucm.mapa.AbstractFoodMap;


import static java.util.Objects.isNull;

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

    public AbstractNode getRootNode() {
        return ((TreeGen) this.genes.get(0)).getFenotipo();
    }
}