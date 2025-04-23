package es.ucm.mutation.tree;

import es.ucm.individuos.Individuo;
import es.ucm.individuos.IndividuoHormigaArbol;
import es.ucm.individuos.tree.*;
import es.ucm.mutation.AbstractMutate;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static java.util.Objects.isNull;

/**
 * Se selecciona al azar un símbolo functional dentro del individuo,
 * y se sustituye (PROG_2 por IF_FOOD, IF_FOOD por PROG_2)
 *
 * - NO muta el nodo Prog3Node (tiene 3 nodos)
 * - NO muta el nodo raiz
 */
public class FunctionalMutate extends AbstractMutate {
    public FunctionalMutate(double mutateProbability) {
        super(mutateProbability);
    }

    @Override
    public Individuo mutate(Individuo ind) {
        IndividuoHormigaArbol indMutado = (IndividuoHormigaArbol) ind.copy();
        double p = ThreadLocalRandom.current().nextDouble();
        if (p < mutateProbability) {
            AbstractNode root = indMutado.getRootNode();
            List<AbstractNode> funcNodes = root.getNodesOfType(false);

            // NO muta el nodo raiz
            if (funcNodes.size() > 1) {
                int selectedChild = ThreadLocalRandom.current().nextInt(1, funcNodes.size());
                AbstractNode selected = funcNodes.get(selectedChild);
                // NO muta el nodo Prog3Node (tiene 3 nodos)
                if (!selected.getNodeName().equals("PROG_3")) {

                    AbstractNode parent = selected.getParentNode();
                    AbstractNode newFunc = null;
                    // switcheamos función
                    if (selected.getNodeName().equals("PROG_2")) {
                        newFunc = new IfFoodNode();
                    } else {
                        newFunc = new Prog2Node();
                    }

                    // copiamos los nodos a la nueva función
                    selected.copyChildrenToClone(newFunc);
                    parent.setChildNode(getChildIndex(parent, selected), newFunc);
                }
            }
        }
        return indMutado;
    }

    private int getChildIndex(AbstractNode parent, AbstractNode child) {
        return parent.getChildNodes().indexOf(child);
    }
}