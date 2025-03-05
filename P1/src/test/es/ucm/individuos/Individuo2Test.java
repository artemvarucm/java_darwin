package es.ucm.individuos;

import es.ucm.individuos.Individuo2;
import org.junit.Test;

public class Individuo2Test {

    @Test
    public void getFitness() {
        Individuo2 a = new Individuo2(0.0001);
        System.out.println(a.getFitness());
    }
}