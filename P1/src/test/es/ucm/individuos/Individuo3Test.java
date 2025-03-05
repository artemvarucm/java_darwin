package es.ucm.individuos;

import es.ucm.individuos.Individuo3;
import org.junit.Test;

public class Individuo3Test {

    @Test
    public void getFitness() {
        Individuo3 a = new Individuo3(0.0001);
        System.out.println(a.getFitness());
    }
}