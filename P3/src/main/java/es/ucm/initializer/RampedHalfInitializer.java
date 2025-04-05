package es.ucm.initializer;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

import es.ucm.factories.IndividuoFactory;
import es.ucm.individuos.Individuo;

public class RampedHalfInitializer {
    private final int maxDepth;
    private final AbstractInitializer fullInitializer;
    private final AbstractInitializer growInitializer;
    
    public RampedHalfInitializer(int maxDepth) {
        this.maxDepth = maxDepth;
        this.fullInitializer = new FULLInitializer(maxDepth);
        this.growInitializer = new GrowInitializer(maxDepth);
    }
    
    public List<Individuo> initializePopulation(int populationSize, IndividuoFactory factory) {
        List<Individuo> population = new ArrayList<>();
        int half = populationSize / 2;
        
        // Mitad con inicialización FULL
        for(int i=0; i<half; i++) {
            int depth = 2 + (i % (maxDepth - 1)); // Profundidades entre 2 y maxDepth
            FULLInitializer init = new FULLInitializer(depth);
            Individuo ind = factory.createOne();
            population.add(ind);
        }
        
        // Mitad con inicialización Grow
        for(int i=half; i<populationSize; i++) {
            int depth = 2 + (i % (maxDepth - 1)); // Profundidades entre 2 y maxDepth
            GrowInitializer init = new GrowInitializer(depth);
            Individuo ind = factory.createOne();
            population.add(ind);
        }
        
        Collections.shuffle(population);
        return population;
    }
}