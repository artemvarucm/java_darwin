package es.ucm.mutation;

import es.ucm.individuos.Individuo;
import es.ucm.individuos.Individuo1;
import org.junit.Test;

import static org.junit.Assert.*;

public class SimpleMutateTest {

    @Test
    public void mutate() {
        SimpleMutate mutator = new SimpleMutate(0.2);
        Individuo ind1 = new Individuo1();
        System.out.println("GENOTIPO INICIAL");
        ind1.printGenotipo();
        mutator.mutate(ind1);
        System.out.println("GENOTIPO MUTADO");
        ind1.printGenotipo();
    }
}