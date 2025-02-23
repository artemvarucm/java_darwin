package es.ucm.individuos;

import es.ucm.individuos.Individuo4;
import org.junit.Test;

public class Individuo4Test {

    @Test
    public void getFitness() {
        Individuo4 a = new Individuo4(0.000001, 5); // d = 5
        System.out.println(a.getFitness());
    }
}