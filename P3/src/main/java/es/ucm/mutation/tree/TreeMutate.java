package es.ucm.mutation.tree;

import es.ucm.individuos.Individuo;
import es.ucm.individuos.IndividuoHormiga;
import es.ucm.individuos.tree.*;
import es.ucm.initializer.GrowInitializer;
import es.ucm.mutation.AbstractMutate;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class TreeMutate extends AbstractMutate {
    private final int maxDepth;
    
    public TreeMutate(double mutateProbability, int maxDepth) {
        super(mutateProbability);
        this.maxDepth = maxDepth;
    }

    @Override
    public Individuo mutate(Individuo ind) {
        IndividuoHormiga indMutado = (IndividuoHormiga) ind.copy();
        double p = ThreadLocalRandom.current().nextDouble();
        if (p < mutateProbability) {
            AbstractNode root = indMutado.getRootNode();
            List<AbstractNode> allNodes = root.getNodesOfType(false);

            int selected = ThreadLocalRandom.current().nextInt(0, allNodes.size());
            AbstractNode toReplace = allNodes.get(selected);
            AbstractNode newSubtree = new GrowInitializer(maxDepth).initialize();

            if (toReplace != root) {
                AbstractNode parent = toReplace.getParentNode();
                int childIndex = getChildIndex(parent, toReplace);
                parent.setChildNode(childIndex, newSubtree);
            } else {
                indMutado.getRootNode().getChildNodes().clear();
                indMutado.getRootNode().getChildNodes().addAll(newSubtree.getChildNodes());
            }
        }
        return indMutado;
    }

    private int getChildIndex(AbstractNode parent, AbstractNode child) {
        return parent.getChildNodes().indexOf(child);
    }
}