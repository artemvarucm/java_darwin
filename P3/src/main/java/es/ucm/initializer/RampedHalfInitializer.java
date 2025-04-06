package es.ucm.initializer;

import es.ucm.individuos.arbol.AbstractNode;
import java.util.concurrent.ThreadLocalRandom;

public class RampedHalfInitializer extends AbstractInitializer {
    public RampedHalfInitializer(int maxDepth) {
        super(maxDepth);
    }

    @Override
    public AbstractNode initialize() {
        // Determinar aleatoriamente si usamos Full o Grow (50/50)
        boolean useFull = ThreadLocalRandom.current().nextBoolean();
        
        if (useFull) {
            return initializeFull();
        } else {
            return initializeGrow();
        }
    }

    private AbstractNode initializeFull() {
        return initializeFullRecursive(1);
    }

    private AbstractNode initializeFullRecursive(int depth) {
        if (depth < maxDepth) {
            AbstractNode func = selectRandomFunction();
            for (int i = 0; i < func.getChildrenSize(); i++) {
                func.setChildNode(i, initializeFullRecursive(depth + 1));
            }
            return func;
        } else {
            return selectRandomTerminal();
        }
    }

    private AbstractNode initializeGrow() {
        return initializeGrowRecursive(1);
    }

    private AbstractNode initializeGrowRecursive(int depth) {
        if (depth >= maxDepth || (depth > 1 && ThreadLocalRandom.current().nextBoolean())) {
            return selectRandomTerminal();
        } else {
            AbstractNode func = selectRandomFunction();
            for (int i = 0; i < func.getChildrenSize(); i++) {
                func.setChildNode(i, initializeGrowRecursive(depth + 1));
            }
            return func;
        }
    }
}