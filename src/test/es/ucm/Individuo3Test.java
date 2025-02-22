package es.ucm;

import es.ucm.individuos.Individuo3;
import org.junit.Test;

public class Individuo3Test {

    @Test
    public void getFitness() {
        Individuo3 a = new Individuo3();
        System.out.println(a.getFitness());
    }
}