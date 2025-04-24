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
 * Expande un nodo terminal con un arbol aleatorio inicializado con GrowInitializer de tamaño especificado
 */
public class ExpansionMutate extends AbstractMutate {
    private AbstractInitializer initializer;
    
    public ExpansionMutate(double mutateProbability, int subtreeDepth) {
        super(mutateProbability);
        this.initializer = new GrowInitializer(subtreeDepth);
    }

    @Override
    public Individuo mutate(Individuo ind) {
        IndividuoHormigaArbol indMutado = (IndividuoHormigaArbol) ind.copy();
        double p = ThreadLocalRandom.current().nextDouble();
        
        if (p < mutateProbability) {
            AbstractNode root = indMutado.getRootNode();
            
            List<AbstractNode> terminals = root.getNodesOfType(true);

            int selected = ThreadLocalRandom.current().nextInt(terminals.size());
            AbstractNode terminal = terminals.get(selected);
            AbstractNode parent = terminal.getParentNode();
            // reemplazamos con un arbol aleatorio
            parent.setChildNode(getChildIndex(parent, terminal), initializer.initialize());
            this.successfulMutate();
        }
        return indMutado;
    }

    private int getChildIndex(AbstractNode parent, AbstractNode child) {
        return parent.getChildNodes().indexOf(child);
    }
}