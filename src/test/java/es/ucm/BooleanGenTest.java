package es.ucm;

import es.ucm.genes.BooleanGen;
import org.junit.Test;

import static org.junit.Assert.*;

public class BooleanGenTest {

    @Test
    public void testTamGen1() {
        BooleanGen a = new BooleanGen(0., 10., 1.);
        a.printGenotipo();
        assertEquals(a.getTamGen(), 4);
    }

    @Test
    public void testTamGen2() {
        BooleanGen a = new BooleanGen(-2., 30., 1.);
        a.printGenotipo();
        assertEquals(a.getTamGen(), 6);
    }

    @Test
    public void testTamGen3() {
        BooleanGen a = new BooleanGen(-1.1, 2.5, 0.2);
        a.printGenotipo();
    }
}