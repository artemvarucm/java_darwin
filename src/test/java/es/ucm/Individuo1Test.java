package es.ucm;

import es.ucm.individuos.Individuo1;
import org.junit.Test;


public class Individuo1Test {

    @Test
    public void getFitness() {
        Individuo1 a = new Individuo1();
        System.out.println(a.getFitness());
    }
}