package es.ucm.mutation;

import es.ucm.cross.AbstractCross;
import es.ucm.individuos.Individuo;
import es.ucm.individuos.IndividuoAspiradora;
import es.ucm.mansion.MansionMap;
import org.junit.Test;

import java.util.List;

public abstract class AbstractMutateTest {
    public void mutateTest(AbstractMutate mutator) {
        // 1000 mutaciones
        for (int i = 0; i < 1000; i++) {
            oneMutate(mutator);
        }
    }

    private void oneMutate(AbstractMutate mutator) {
        Individuo ind1 = new IndividuoAspiradora(new MansionMap());
        System.out.println("GENOTIPO INICIAL");
        ind1.printGenotipo();
        mutator.mutate(ind1);
        System.out.println("GENOTIPO MUTADO");
        ind1.printGenotipo();
    }
}