package es.ucm.mutation;

import es.ucm.individuos.Individuo;
import es.ucm.individuos.IndividuoAspiradora;
import es.ucm.mansion.MansionMap;
import org.junit.Test;

public class SwapMutateTest extends AbstractMutateTest{

    @Test
    public void mutate() {
        AbstractMutate mutator = new SwapMutate(1);
        this.mutateTest(mutator);
    }
}