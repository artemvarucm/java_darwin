package es.ucm.mutation.tree;

import es.ucm.individuos.Individuo;
import es.ucm.individuos.IndividuoHormigaArbol;
import es.ucm.individuos.tree.*;
import es.ucm.mutation.AbstractMutate;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Reduce un subárbol a un terminal
 * (no muta el nodo raiz porque necesitamos profundidad minima de 2)
 */
public class ContractionMutate extends AbstractMutate {
    public ContractionMutate(double mutateProbability) {
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
                int selected = ThreadLocalRandom.current().nextInt(1, funcNodes.size());
                AbstractNode toContract = funcNodes.get(selected);

                AbstractNode parent = toContract.getParentNode();
                int parentIndex = getChildIndex(parent, toContract);
                // reemplazamos nodo funcional por uno terminal aleatorio
                parent.setChildNode(parentIndex, selectRandomTerminal());
                this.successfulMutate();
            }
        }

        return indMutado;
    }

    private int getChildIndex(AbstractNode parent, AbstractNode child) {
        return parent.getChildNodes().indexOf(child);
    }

    /**
     * Seleccionar aleatoriamente un nodo terminal
     */
    private AbstractNode selectRandomTerminal() {
        List<AbstractNode> terminalSet = List.of(new LeftNode(), new RightNode(), new ForwardNode());
        int selected = ThreadLocalRandom.current().nextInt(terminalSet.size());
        return terminalSet.get(selected);
    }
}