package es.ucm.cross;

import es.ucm.factories.IndividuoFactory;
import es.ucm.individuos.Individuo;
import es.ucm.individuos.IndividuoHormiga;
import es.ucm.individuos.arbol.AbstractNode;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class SubtreeSwapCross extends AbstractCross {
    public SubtreeSwapCross(IndividuoFactory factory) {
        super(factory);
    }

    public List<Individuo> cross(Individuo parent1, Individuo parent2) {
        IndividuoHormiga child1 = (IndividuoHormiga) parent1.copy();
        IndividuoHormiga child2 = (IndividuoHormiga) parent2.copy();
        AbstractNode rootNode1 = child1.getRootNode();
        AbstractNode rootNode2 = child2.getRootNode();

        // Seleccionamos primero el nodo padre (reemplazaremos alguno de sus hijos)
        List<AbstractNode> funcNodes1 = rootNode1.getAllFunctionalNodes();
        List<AbstractNode> funcNodes2 = rootNode2.getAllFunctionalNodes();
        int selParent1 = ThreadLocalRandom.current().nextInt(0, funcNodes1.size());
        int selParent2 = ThreadLocalRandom.current().nextInt(0, funcNodes2.size());

        // De cada padre anterior seleccionamos el subarbol que reemplazaremos
        // fixme cambiar de probabilidad si es un nodo terminal o no
        int selChild1 = ThreadLocalRandom.current().nextInt(0, funcNodes1.get(selParent1).getChildrenSize());
        int selChild2 = ThreadLocalRandom.current().nextInt(0, funcNodes2.get(selParent2).getChildrenSize());

        // Intercambiamos
        AbstractNode tempSubtree1 = funcNodes1.get(selParent1).getChildNode(selChild1).clone();
        AbstractNode tempSubtree2 = funcNodes2.get(selParent2).getChildNode(selChild2).clone();

        funcNodes1.get(selParent1).setChildNode(selChild1, tempSubtree2);
        funcNodes2.get(selParent2).setChildNode(selChild2, tempSubtree1);

        // Devolvemos
        List<Individuo> result = new ArrayList<>(2);
        result.add(child1);
        result.add(child2);
        return result;
    }
}
