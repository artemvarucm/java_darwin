package es.ucm.mutation.tree;

import es.ucm.individuos.Individuo;
import es.ucm.individuos.IndividuoHormigaArbol;
import es.ucm.individuos.tree.AbstractNode;
import es.ucm.individuos.tree.ForwardNode;
import es.ucm.individuos.tree.LeftNode;
import es.ucm.individuos.tree.RightNode;
import es.ucm.mutation.AbstractMutate;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Se selecciona al azar un símbolo terminal dentro del individuo,
 * y se sustituye por otro diferente del conjunto de símbolos terminales posibles
 * REPRESENTACIÓN: ARBOL
 */
public class TerminalMutate extends AbstractMutate {
    public TerminalMutate(double mutateProbability) {
        super(mutateProbability);
    }

    @Override
    public Individuo mutate(Individuo ind) {
        IndividuoHormigaArbol indMutado = (IndividuoHormigaArbol) ind.copy();
        double p = ThreadLocalRandom.current().nextDouble();
        if (p < mutateProbability) {
            List<AbstractNode> terminals = indMutado.getRootNode().getNodesOfType(true);

            int selectedChild = ThreadLocalRandom.current().nextInt(0, terminals.size());
            AbstractNode selected = terminals.get(selectedChild);
            AbstractNode parent = selected.getParentNode();
            int indexInParent = parent.getChildNodes().indexOf(selected);

            parent.setChildNode(indexInParent, selectRandomTerminal(selected.getNodeName()));
        }

        return indMutado;
    }

    /**
     * Seleccionar aleatoriamente un nodo terminal que no sea excluding
     */
    private AbstractNode selectRandomTerminal(String excluding) {
        List<AbstractNode> terminalSet = List.of(new LeftNode(), new RightNode(), new ForwardNode());
        terminalSet = terminalSet.stream().filter(node -> !node.getNodeName().equals(excluding)).toList();

        int selected = ThreadLocalRandom.current().nextInt(0, terminalSet.size());

        return terminalSet.get(selected);
    }
}