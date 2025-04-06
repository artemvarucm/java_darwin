package es.ucm.initializer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import es.ucm.individuos.arbol.AbstractNode;

public class GrowInitializer extends AbstractInitializer {
    private static double MIN_DEPTH = 2; // profundidad minima
    public GrowInitializer(int maxDepth) {
        super(maxDepth);
    }

    public List<AbstractNode> initializeN(int N) {
        List<AbstractNode> result = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            result.add(initializeRecursive(1));
        }

        return result;
    }

    private AbstractNode initializeRecursive(int depth) {
        if (
                depth > (MIN_DEPTH - 1) && // el nodo raiz no puede ser terminal
                (depth >= maxDepth || ThreadLocalRandom.current().nextBoolean())
        ) {
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