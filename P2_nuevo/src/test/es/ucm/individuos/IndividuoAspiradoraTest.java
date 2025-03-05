package es.ucm.individuos;

import org.junit.Test;


public class IndividuoAspiradoraTest {

    @Test
    public void getFitness() {
        IndividuoAspiradora a = new IndividuoAspiradora();
        System.out.println(a.getFitness());
    }
}