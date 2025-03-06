package es.ucm.mutation;

import es.ucm.individuos.Individuo;
import es.ucm.individuos.IndividuoAspiradora;
import es.ucm.mansion.MansionMap;
import org.junit.Test;

public class SwapMutateTest {

    @Test
    public void mutate() {
        SwapMutate mutator = new SwapMutate();
        Individuo ind1 = new IndividuoAspiradora(new MansionMap());
        System.out.println("GENOTIPO INICIAL");
        ind1.printGenotipo();
        mutator.mutate(ind1);
        System.out.println("GENOTIPO MUTADO");
        ind1.printGenotipo();
    }
}