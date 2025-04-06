package es.ucm.mutation;

import es.ucm.individuos.Individuo;
import es.ucm.individuos.IndividuoHormiga;
import es.ucm.individuos.arbol.*;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class ExpansionMutate extends AbstractMutate {
    private final int maxDepth;
    
    public ExpansionMutate(double mutateProbability, int maxDepth) {
        super(mutateProbability);
        this.maxDepth = maxDepth;
    }

    @Override
    public Individuo mutate(Individuo ind) {
		return null;
        
    }
}