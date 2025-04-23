package es.ucm.mutation.tree;

import es.ucm.individuos.Individuo;
import es.ucm.individuos.IndividuoHormigaArbol;
import es.ucm.individuos.tree.*;
import es.ucm.initializer.AbstractInitializer;
import es.ucm.initializer.GrowInitializer;
import es.ucm.mutation.AbstractMutate;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Parecido al expansion mutate, solo que reemplaza un nodo funcional (puede reducir el árbol)
 */
public class TreeMutate extends AbstractMutate {
    private AbstractInitializer initializer;

    public TreeMutate(double mutateProbability, int subtreeDepth) {
        super(mutateProbability);
        this.initializer = new GrowInitializer(subtreeDepth);
    }

    @Override
    public Individuo mutate(Individuo ind) {
        IndividuoHormigaArbol indMutado = (IndividuoHormigaArbol) ind.copy();
        double p = ThreadLocalRandom.current().nextDouble();
        if (p < mutateProbability) {
            AbstractNode root = indMutado.getRootNode();
            List<AbstractNode> functional = root.getNodesOfType(false);
            if (functional.size() > 1) {
                // NO muta el nodo raiz
                int selected = ThreadLocalRandom.current().nextInt(1, functional.size());
                AbstractNode selFunc = functional.get(selected);
                AbstractNode parent = selFunc.getParentNode();
                // reemplazamos con un arbol aleatorio
                parent.setChildNode(getChildIndex(parent, selFunc), initializer.initialize());
            }
        }
        return indMutado;
    }

    private int getChildIndex(AbstractNode parent, AbstractNode child) {
        return parent.getChildNodes().indexOf(child);
    }
}