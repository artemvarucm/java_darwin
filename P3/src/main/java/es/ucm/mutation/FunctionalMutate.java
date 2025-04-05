package es.ucm.mutation;

import es.ucm.individuos.Individuo;
import es.ucm.individuos.IndividuoHormiga;
import es.ucm.individuos.arbol.AbstractNode;
import es.ucm.individuos.arbol.IfFoodNode;
import es.ucm.individuos.arbol.Prog2Node;
import es.ucm.individuos.arbol.Prog3Node;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Reemplaza un nodo funcional por otro del mismo aridad
 */
public class FunctionalMutate extends AbstractMutate {
    public FunctionalMutate(double mutateProbability) {
        super(mutateProbability);
    }

    @Override
    public Individuo mutate(Individuo ind) {
        IndividuoHormiga indMutado = (IndividuoHormiga) ind.copy();
        double p = ThreadLocalRandom.current().nextDouble();
        if (p < mutateProbability) {
            List<AbstractNode> funcNodes = indMutado.getRootNode().getAllFunctionalNodes();
            if(!funcNodes.isEmpty()) {
                int selected = ThreadLocalRandom.current().nextInt(0, funcNodes.size());
                AbstractNode toReplace = funcNodes.get(selected);
                
                AbstractNode replacement;
                if(toReplace.getChildrenSize() == 2) {
                    replacement = new Prog2Node();
                } else if(toReplace.getChildrenSize() == 3) {
                    replacement = new Prog3Node();
                } else {
                    replacement = new IfFoodNode();
                }
                
                replacement.copyToClone(toReplace);
            }
        }
        return indMutado;
    }
}