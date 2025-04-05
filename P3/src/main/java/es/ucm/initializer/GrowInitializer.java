package es.ucm.initializer;

import java.util.concurrent.ThreadLocalRandom;

import es.ucm.individuos.arbol.AbstractNode;

public class GrowInitializer extends AbstractInitializer {
    public GrowInitializer(int maxDepth) {
        super(maxDepth);
    }

    public AbstractNode initialize() {
        return initializeRecursive(1);
    }
    
    private AbstractNode initializeRecursive(int depth) {
        if (depth >= maxDepth || ThreadLocalRandom.current().nextBoolean()) {
            return selectRandomTerminal();
        } else {
            AbstractNode func = selectRandomFunction();
            for (int i = 0; i < func.getChildrenSize(); i++) {
                func.setChildNode(i, initializeRecursive(depth + 1));
            }
            return func;
        }
    }
}