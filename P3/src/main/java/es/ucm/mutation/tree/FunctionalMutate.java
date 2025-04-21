package es.ucm.mutation.tree;

import es.ucm.individuos.Individuo;
import es.ucm.individuos.IndividuoHormiga;
import es.ucm.individuos.tree.*;
import es.ucm.mutation.AbstractMutate;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class FunctionalMutate extends AbstractMutate {
    public FunctionalMutate(double mutateProbability) {
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
                AbstractNode toReplace = funcNodes.get(selected);
                AbstractNode newNode = selectRandomFunction(toReplace.getChildrenSize());
                
                // Copiar los hijos del nodo original al nuevo
                for (int i = 0; i < toReplace.getChildrenSize(); i++) {
                    newNode.setChildNode(i, toReplace.getChildNode(i).clone());
                }
                
                // Reemplazar en el padre
                if (toReplace != root) {
                    AbstractNode parent = toReplace.getParentNode();
                    int childIndex = getChildIndex(parent, toReplace);
                    parent.setChildNode(childIndex, newNode);
                } else {
                    indMutado.getRootNode().getChildNodes().clear();
                    indMutado.getRootNode().getChildNodes().addAll(newNode.getChildNodes());
                }
            }
        }
        return indMutado;
    }

    private AbstractNode selectRandomFunction(int requiredArgs) {
        List<AbstractNode> candidates = List.of(
            new Prog2Node(),
            new Prog3Node(),
            new IfFoodNode()
        ).stream()
         .filter(n -> n.getChildrenSize() == requiredArgs)
         .toList();
         
        return candidates.get(ThreadLocalRandom.current().nextInt(candidates.size())).clone();
    }

    private int getChildIndex(AbstractNode parent, AbstractNode child) {
        return parent.getChildNodes().indexOf(child);
    }
}