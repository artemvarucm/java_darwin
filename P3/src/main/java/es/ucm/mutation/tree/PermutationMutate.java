package es.ucm.mutation.tree;

import es.ucm.individuos.Individuo;
import es.ucm.individuos.IndividuoHormiga;
import es.ucm.individuos.tree.*;
import es.ucm.mutation.AbstractMutate;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class PermutationMutate extends AbstractMutate {
    public PermutationMutate(double mutateProbability) {
        super(mutateProbability);
    }

    @Override
    public Individuo mutate(Individuo ind) {
        IndividuoHormiga indMutado = (IndividuoHormiga) ind.copy();
        double p = ThreadLocalRandom.current().nextDouble();
        if (p < mutateProbability) {
            AbstractNode root = indMutado.getRootNode();
            List<AbstractNode> funcNodes = root.getNodesOfType(false);
            
            if (!funcNodes.isEmpty()) {
                int selected = ThreadLocalRandom.current().nextInt(0, funcNodes.size());
                AbstractNode toPermute = funcNodes.get(selected);
                Collections.shuffle(toPermute.getChildNodes());
            }
        }
        return indMutado;
    }
}