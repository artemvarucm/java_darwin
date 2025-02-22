package es.ucm.individuos;

import es.ucm.individuos.Individuo5;
import org.junit.Test;

public class Individuo5Test {

    @Test
    public void getFitness() {
        Individuo5 a = new Individuo5(5); // d = 5
        System.out.println(a.getFitness());
    }
}