package es.ucm.mutation;

import es.ucm.individuos.Individuo;
import es.ucm.individuos.IndividuoHormiga;
import es.ucm.individuos.arbol.*;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class ContractionMutate extends AbstractMutate {
    public ContractionMutate(double mutateProbability) {
        super(mutateProbability);
    }

    @Override
    public Individuo mutate(Individuo ind) {
        IndividuoHormiga indMutado = (IndividuoHormiga) ind.copy();
        double p = ThreadLocalRandom.current().nextDouble();
        if (p < mutateProbability) {
            AbstractNode root = indMutado.getRootNode();
            List<AbstractNode> funcNodes = root.getAllFunctionalNodes();
            
            if (!funcNodes.isEmpty()) {
                int selected = ThreadLocalRandom.current().nextInt(0, funcNodes.size());
                AbstractNode toContract = funcNodes.get(selected);
                
                if (!toContract.getChildNodes().isEmpty()) {
                    int childSelected = ThreadLocalRandom.current().nextInt(0, toContract.getChildrenSize());
                    AbstractNode child = toContract.getChildNode(childSelected);
                    
                    if (toContract != root) {
                        AbstractNode parent = findParent(root, toContract);
                        int parentIndex = getChildIndex(parent, toContract);
                        parent.setChildNode(parentIndex, child.clone());
                    }
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