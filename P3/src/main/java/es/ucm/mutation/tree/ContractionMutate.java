package es.ucm.mutation.tree;

import es.ucm.individuos.Individuo;
import es.ucm.individuos.IndividuoHormiga;
import es.ucm.individuos.tree.*;
import es.ucm.mutation.AbstractMutate;

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
            List<AbstractNode> funcNodes = root.getNodesOfType(false);
            
            if (!funcNodes.isEmpty()) {
                int selected = ThreadLocalRandom.current().nextInt(0, funcNodes.size());
                AbstractNode toContract = funcNodes.get(selected);
                
                if (!toContract.getChildNodes().isEmpty()) {
                    int childSelected = ThreadLocalRandom.current().nextInt(0, toContract.getChildrenSize());
                    AbstractNode child = toContract.getChildNode(childSelected);
                    
                    if (toContract != root) {
                        AbstractNode parent = toContract.getParentNode();
                        int parentIndex = getChildIndex(parent, toContract);
                        parent.setChildNode(parentIndex, child.clone());
                    }
                }
            }
        }
        return indMutado;
    }

    private int getChildIndex(AbstractNode parent, AbstractNode child) {
        return parent.getChildNodes().indexOf(child);
    }
}