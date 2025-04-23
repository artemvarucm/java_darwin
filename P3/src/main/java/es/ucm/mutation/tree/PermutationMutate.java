package es.ucm.mutation.tree;

import es.ucm.individuos.Individuo;
import es.ucm.individuos.IndividuoHormigaArbol;
import es.ucm.individuos.tree.*;
import es.ucm.mutation.AbstractMutate;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Intercambia el orden de la lista de argumentos de una función
 */
public class PermutationMutate extends AbstractMutate {
    public PermutationMutate(double mutateProbability) {
        super(mutateProbability);
    }

    @Override
    public Individuo mutate(Individuo ind) {
        IndividuoHormigaArbol indMutado = (IndividuoHormigaArbol) ind.copy();
        double p = ThreadLocalRandom.current().nextDouble();
        if (p < mutateProbability) {
            AbstractNode root = indMutado.getRootNode();
            List<AbstractNode> funcNodes = root.getNodesOfType(false);
            int selectedChild = ThreadLocalRandom.current().nextInt(funcNodes.size());
            AbstractNode toPermute = funcNodes.get(selectedChild);
            toPermute.shuffleChildNodes();
        }
        return indMutado;
    }
}