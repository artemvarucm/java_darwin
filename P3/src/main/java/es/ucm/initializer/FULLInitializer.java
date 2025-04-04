package es.ucm.initializer;

import es.ucm.individuos.arbol.*;


public class FULLInitializer extends AbstractInitializer{
    public FULLInitializer(int maxDepth) {
        super(maxDepth);
    }

    public AbstractNode initialize() {
        return initializeRecursive(1);
    }
    private AbstractNode initializeRecursive(int depth) {
        if (depth < maxDepth) {
            AbstractNode func = selectRandomFunction();
            for (int i = 0; i < func.getChildrenSize(); i++) {
                func.setChildNode(i, initializeRecursive(depth + 1));
            }
            return func;
        } else {
            return selectRandomTerminal();
        }
    }
}
