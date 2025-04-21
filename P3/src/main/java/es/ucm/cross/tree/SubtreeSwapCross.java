package es.ucm.cross.tree;

import es.ucm.cross.AbstractCross;
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


        // terminal1 ? 0 : 1 para evitar seleccionar el nodo raiz (porque si terminal = false -> se incluye la raiz)
        AbstractNode selNode1 = nodes1.get(ThreadLocalRandom.current().nextInt(terminal1 ? 0 : 1, nodes1.size()));
        AbstractNode selNode2 = nodes2.get(ThreadLocalRandom.current().nextInt(terminal2 ? 0 : 1, nodes2.size()));

        // Los padres (cuyos hijos intercambiaremos)
        AbstractNode tempParent1 = selNode1.getParentNode();
        AbstractNode tempParent2 = selNode2.getParentNode();
        // Clonamos los hijos
        AbstractNode tempSubtree1 = selNode1.clone();
        AbstractNode tempSubtree2 = selNode2.clone();

        // Reemplazamos el hijo existente con el intercambiado en cada padre
        int index1 = tempParent1.getChildNodes().indexOf(selNode1);
        int index2 = tempParent2.getChildNodes().indexOf(selNode2);

        tempParent1.setChildNode(index1, tempSubtree2);
        tempParent2.setChildNode(index2, tempSubtree1);

        // Devolvemos
        List<Individuo> result = new ArrayList<>(2);
        result.add(child1);
        result.add(child2);
        return result;
    }
}
