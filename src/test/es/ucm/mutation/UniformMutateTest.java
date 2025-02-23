package es.ucm.mutation;

import es.ucm.individuos.Individuo;
import es.ucm.individuos.Individuo1;
import org.junit.Test;

import static org.junit.Assert.*;

public class UniformMutateTest {

    @Test
    public void mutate() {
        UniformMutate mutator = new UniformMutate(0.2);
        Individuo ind1 = new Individuo1(0.0001);
        System.out.println("GENOTIPO INICIAL");
        ind1.printGenotipo();
        mutator.mutate(ind1);
        System.out.println("GENOTIPO MUTADO");
        ind1.printGenotipo();
    }
}