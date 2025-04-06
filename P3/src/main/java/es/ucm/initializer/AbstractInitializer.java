package es.ucm.initializer;

import es.ucm.individuos.arbol.*;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public abstract class AbstractInitializer {
    protected int maxDepth;
    protected List<AbstractNode> functions;
    protected List<AbstractNode> terminals;
    
    public AbstractInitializer(int maxDepth) {
        this.maxDepth = maxDepth;
        this.functions = List.of(new Prog2Node(), new Prog3Node(), new IfFoodNode());
        this.terminals = List.of(new LeftNode(), new RightNode(), new ForwardNode());
    }

    public abstract AbstractNode initialize();

    public int getMaxDepth() {
        return this.maxDepth;
    }

    protected AbstractNode selectRandomFunction() {
        int selected = ThreadLocalRandom.current().nextInt(0, this.functions.size());
        return this.functions.get(selected).clone();
    }

    protected AbstractNode selectRandomTerminal() {
        int selected = ThreadLocalRandom.current().nextInt(0, this.terminals.size());
        return this.terminals.get(selected).clone();
    }
}