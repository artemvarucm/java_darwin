package es.ucm.mutation;

import es.ucm.individuos.Individuo;
import es.ucm.individuos.IndividuoHormiga;
import es.ucm.individuos.arbol.AbstractNode;
import es.ucm.individuos.arbol.Prog2Node;
import es.ucm.individuos.arbol.Prog3Node;
import es.ucm.initializer.AbstractInitializer;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Reemplaza un subárbol completo por uno nuevo generado aleatoriamente
 */
public class TreeMutate extends AbstractMutate {
    private AbstractInitializer initializer;
    private int maxDepth;
    
    public TreeMutate(double mutateProbability, AbstractInitializer initializer, int maxDepth) {
        super(mutateProbability);
        this.initializer = initializer;
        this.maxDepth = maxDepth;
    }

    @Override
    public Individuo mutate(Individuo ind) {
        IndividuoHormiga indMutado = (IndividuoHormiga) ind.copy();
        double p = ThreadLocalRandom.current().nextDouble();
        if (p < mutateProbability) {
            List<AbstractNode> allNodes = indMutado.getRootNode().getAllFunctionalNodes();
            allNodes.add(indMutado.getRootNode());
            
            if(!allNodes.isEmpty()) {
                int selected = ThreadLocalRandom.current().nextInt(0, allNodes.size());
                AbstractNode toReplace = allNodes.get(selected);
                
                AbstractNode newTree = initializer.initialize();
                toReplace.copyToClone(newTree);
            }
        }
        return indMutado;
    }
}