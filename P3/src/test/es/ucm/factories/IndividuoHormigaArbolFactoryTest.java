package es.ucm.factories;

import es.ucm.individuos.Individuo;
import es.ucm.individuos.IndividuoHormigaArbol;
import es.ucm.initializer.FULLInitializer;
import es.ucm.initializer.GrowInitializer;
import es.ucm.mapa.SantaFeMap;
import org.junit.Test;


public class IndividuoHormigaArbolFactoryTest {
    @Test
    public void test1() {
        IndividuoHormigaArbolFactory facktory = new IndividuoHormigaArbolFactory(new SantaFeMap(), 400, 0., new FULLInitializer(2));
        for (Individuo ind: facktory.createMany(10)) {
            System.out.println(((IndividuoHormigaArbol) ind).getRootNode().toString());
        }
    }

    @Test
    public void test2() {
        IndividuoHormigaArbolFactory facktory = new IndividuoHormigaArbolFactory(new SantaFeMap(), 400, 0., new FULLInitializer(5));
        for (Individuo ind: facktory.createMany(10)) {
            System.out.println(((IndividuoHormigaArbol) ind).getRootNode().toString());
        }
    }

    @Test
    public void test3() {
        IndividuoHormigaArbolFactory facktory = new IndividuoHormigaArbolFactory(new SantaFeMap(), 400, 0., new GrowInitializer(5));
        for (Individuo ind: facktory.createMany(10)) {
            System.out.println(((IndividuoHormigaArbol) ind).getRootNode().toString());
        }
    }
}