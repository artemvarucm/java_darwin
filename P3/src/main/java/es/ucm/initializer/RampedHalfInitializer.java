package es.ucm.initializer;

import es.ucm.individuos.arbol.AbstractNode;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RampedHalfInitializer extends AbstractInitializer {
    private final int targetDepth;
    private final boolean useFull;
    
    public RampedHalfInitializer(int maxDepth, int targetDepth, boolean useFull) {
        super(maxDepth);
        if (targetDepth < 2 || targetDepth > maxDepth) {
            throw new IllegalArgumentException("La profundidad objetivo debe estar entre 2 y " + maxDepth);
        }
        this.targetDepth = targetDepth;
        this.useFull = useFull;
    }

    @Override
    public AbstractNode initialize() {
        if (useFull) {
            return initializeFull(targetDepth);
        } else {
            return initializeGrow(targetDepth);
        }
    }

    private AbstractNode initializeFull(int depthLimit) {
        return initializeFullRecursive(1, depthLimit);
    }

    private AbstractNode initializeFullRecursive(int currentDepth, int depthLimit) {
        if (currentDepth < depthLimit) {
            AbstractNode func = selectRandomFunction();
            for (int i = 0; i < func.getChildrenSize(); i++) {
                func.setChildNode(i, initializeFullRecursive(currentDepth + 1, depthLimit));
            }
            return func;
        } else {
            return selectRandomTerminal();
        }
    }

    private AbstractNode initializeGrow(int depthLimit) {
        return initializeGrowRecursive(1, depthLimit);
    }

    private AbstractNode initializeGrowRecursive(int currentDepth, int depthLimit) {
        if (currentDepth >= depthLimit || (currentDepth > 1 && ThreadLocalRandom.current().nextBoolean())) {
            return selectRandomTerminal();
        } else {
            AbstractNode func = selectRandomFunction();
            for (int i = 0; i < func.getChildrenSize(); i++) {
                func.setChildNode(i, initializeGrowRecursive(currentDepth + 1, depthLimit));
            }
            return func;
        }
    }

    // Método estático para crear la población inicial
    public static List<AbstractNode> createInitialPopulation(int populationSize, int maxDepth) {
        List<AbstractNode> population = new ArrayList<>();
        int groups = maxDepth - 1;
        int groupSize = populationSize / groups;
        int remainder = populationSize % groups;
        
        for (int depth = 2; depth <= maxDepth; depth++) {
            int currentGroupSize = groupSize + (depth == maxDepth ? remainder : 0);
            int fullCount = currentGroupSize / 2;
            int growCount = currentGroupSize - fullCount;
            
            // Añadir árboles con método completo
            for (int i = 0; i < fullCount; i++) {
                RampedHalfInitializer initializer = 
                    new RampedHalfInitializer(maxDepth, depth, true);
                population.add(initializer.initialize());
            }
            
            // Añadir árboles con método creciente
            for (int i = 0; i < growCount; i++) {
                RampedHalfInitializer initializer = 
                    new RampedHalfInitializer(maxDepth, depth, false);
                population.add(initializer.initialize());
            }
        }
        
        return population;
    }
}