package es.ucm.cross;

import es.ucm.factories.IndividuoFactory;
import es.ucm.individuos.Individuo;
import es.ucm.individuos.IndividuoHormiga;
import es.ucm.individuos.arbol.AbstractNode;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class SubtreeSwapCross extends AbstractCross {
    private double crossTermProb; // probabilidad de seleccionar un nodo terminal en vez del funcional

    public SubtreeSwapCross(IndividuoFactory factory, double crossTermProb) {
        super(factory);
        this.crossTermProb = crossTermProb;
    }

    public List<Individuo> cross(Individuo parent1, Individuo parent2) {
        IndividuoHormiga child1 = (IndividuoHormiga) parent1.copy();
        IndividuoHormiga child2 = (IndividuoHormiga) parent2.copy();
        AbstractNode rootNode1 = child1.getRootNode();
        AbstractNode rootNode2 = child2.getRootNode();

        // listas de indices de los nodos que podremos usar en la seleccion aleatoria
        // (seran o todos terminales o todos funcionales)
        boolean terminal1 = ThreadLocalRandom.current().nextDouble() < crossTermProb;
        boolean terminal2 = ThreadLocalRandom.current().nextDouble() < crossTermProb;
        List<AbstractNode> nodes1 = rootNode1.getNodesOfType(terminal1);
        List<AbstractNode> nodes2 = rootNode2.getNodesOfType(terminal2);
        if (nodes1.isEmpty() || (!terminal1 && nodes1.size() == 1))
            nodes1 = rootNode1.getNodesOfType(!terminal1);

        if (nodes2.isEmpty() || (!terminal2 && nodes2.size() == 1))
            nodes2 = rootNode2.getNodesOfType(!terminal2);


        // terminal1 ? 0 : 1 para evitar seleccionar el nodo raiz
        AbstractNode selNode1 = nodes1.get(ThreadLocalRandom.current().nextInt(terminal1 ? 0 : 1, nodes1.size()));
        AbstractNode selNode2 = nodes2.get(ThreadLocalRandom.current().nextInt(terminal2 ? 0 : 1, nodes2.size()));

        // Intercambiamos
        AbstractNode tempParent1 = selNode1.getParentNode();
        AbstractNode tempParent2 = selNode2.getParentNode();
        AbstractNode tempSubtree1 = selNode1.clone();
        AbstractNode tempSubtree2 = selNode2.clone();
        tempSubtree1.setParentNode(tempParent2);
        tempSubtree2.setParentNode(tempParent1);

        int index = tempParent1.getChildNodes().indexOf(selNode1);
        tempParent1.setChildNode(index, tempSubtree2);

        index = tempParent2.getChildNodes().indexOf(selNode2);
        tempParent2.setChildNode(index, tempSubtree1);

        // Devolvemos
        List<Individuo> result = new ArrayList<>(2);
        result.add(child1);
        result.add(child2);
        return result;
    }

    /**
     * Devuelve los indices de los hijos que son:
     *  Si terminal = true -> terminales
     *  Si terminal = false -> funcionales
     * @param parNode
     * @param terminal
     * @return
     */

    private List<Integer> getFilteredIndices(AbstractNode parNode, boolean terminal) {
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < parNode.getChildNodes().size(); i++) {
            if (
                    (terminal && parNode.getChildNodes().get(i).isTerminal())
                    ||
                    (!terminal && !parNode.getChildNodes().get(i).isTerminal())
            ) {
                indices.add(i);
            }
        }

            return indices;
    }
}
