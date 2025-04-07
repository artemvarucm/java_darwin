package es.ucm.mutation;

import es.ucm.individuos.Individuo;
import es.ucm.individuos.IndividuoHormiga;
import es.ucm.individuos.arbol.*;
import es.ucm.initializer.GrowInitializer;

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
            allNodes.add(root);
            
            if (!allNodes.isEmpty()) {
                int selected = ThreadLocalRandom.current().nextInt(0, allNodes.size());
                AbstractNode toReplace = allNodes.get(selected);
                AbstractNode newSubtree = new GrowInitializer(maxDepth).initialize();
                
                if (toReplace != root) {
                    AbstractNode parent = findParent(root, toReplace);
                    int childIndex = getChildIndex(parent, toReplace);
                    parent.setChildNode(childIndex, newSubtree);
                } else {
                    indMutado.getRootNode().getChildNodes().clear();
                    indMutado.getRootNode().getChildNodes().addAll(newSubtree.getChildNodes());
                }
            }
        }
        return indMutado;
    }
    
    private AbstractNode findParent(AbstractNode root, AbstractNode child) {
        if (root.getChildNodes().contains(child)) return root;
        for (AbstractNode node : root.getChildNodes()) {
            if (!node.isTerminal()) {
                AbstractNode found = findParent(node, child);
                if (found != null) return found;
            }
        }
        return null;
    }

    private int getChildIndex(AbstractNode parent, AbstractNode child) {
        return parent.getChildNodes().indexOf(child);
    }
}