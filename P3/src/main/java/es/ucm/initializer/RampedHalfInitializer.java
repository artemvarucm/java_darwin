package es.ucm.initializer;

import es.ucm.individuos.arbol.AbstractNode;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RampedHalfInitializer extends AbstractInitializer {
    public RampedHalfInitializer(int maxDepth) {
        super(maxDepth);
    }

    public List<AbstractNode> initializeN(int N) {
        List<AbstractNode> population = new ArrayList<>();
        int groups = maxDepth - 1;
        int groupSize = N / groups;
        int remainder = N % groups;
        for (int depth = 2; depth <= maxDepth; depth++) {
            int currentGroupSize = groupSize + (depth == maxDepth ? remainder : 0);
            int fullCount = currentGroupSize / 2;
            int growCount = currentGroupSize - fullCount;
            FULLInitializer fullInitializer = new FULLInitializer(depth);
            GrowInitializer growInitializer = new GrowInitializer(depth);
            population.addAll(fullInitializer.initializeN(fullCount));
            population.addAll(growInitializer.initializeN(growCount));
        }
        
        return population;
    }
}