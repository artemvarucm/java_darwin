package es.ucm.initializer;

import es.ucm.individuos.tree.*;

import java.util.ArrayList;
import java.util.List;


public class FULLInitializer extends AbstractInitializer{
    public FULLInitializer(int maxDepth) {
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
