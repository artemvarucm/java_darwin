package es.ucm.factories;

import es.ucm.individuos.Individuo;
import es.ucm.individuos.IndividuoHormigaArbol;
import es.ucm.individuos.IndividuoHormigaGramatica;
import es.ucm.individuos.tree.AbstractNode;
import es.ucm.initializer.AbstractInitializer;
import es.ucm.mapa.AbstractFoodMap;

import java.util.ArrayList;
import java.util.List;


/**
 * Individuos con genotipo como cadena de codones
 */
public class IndividuoHormigaGramaticaFactory extends IndividuoFactory {
    private Integer stepsLimit;
    private Integer nWraps;
    private Integer numCodones;
    public IndividuoHormigaGramaticaFactory(AbstractFoodMap map, Integer stepsLimit, Integer nWraps, Integer numCodones) {
        super(map);
        this.stepsLimit = stepsLimit;
        this.nWraps = nWraps;
        this.numCodones = numCodones;
    }

    public Individuo createOne() {
        return new IndividuoHormigaGramatica(this.map, this.stepsLimit, nWraps, numCodones);
    }

    @Override
    public List<Individuo> createMany(int tamPoblacion) {
        List<Individuo> individuos = new ArrayList<>(tamPoblacion);

        for (int i = 0; i < tamPoblacion; i++) {
            IndividuoHormigaGramatica individuo = new IndividuoHormigaGramatica(this.map, this.stepsLimit, nWraps, numCodones);
            individuos.add(individuo);
        }

        return individuos;
    }
}