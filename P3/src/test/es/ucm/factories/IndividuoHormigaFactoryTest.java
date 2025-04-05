package es.ucm.factories;

import es.ucm.individuos.Individuo;
import es.ucm.individuos.IndividuoHormiga;
import es.ucm.initializer.FULLInitializer;
import es.ucm.initializer.GrowInitializer;
import es.ucm.mapa.SantaFeMap;
import org.junit.Test;


public class IndividuoHormigaFactoryTest {
    @Test
    public void test1() {
        IndividuoHormigaFactory facktory = new IndividuoHormigaFactory(new SantaFeMap(), 400, new FULLInitializer(2));
        for (Individuo ind: facktory.createMany(10)) {
            System.out.println(((IndividuoHormiga) ind).getRootNode().toString());
        }
    }

    @Test
    public void test2() {
        IndividuoHormigaFactory facktory = new IndividuoHormigaFactory(new SantaFeMap(), 400, new FULLInitializer(5));
        for (Individuo ind: facktory.createMany(10)) {
            System.out.println(((IndividuoHormiga) ind).getRootNode().toString());
        }
    }

    @Test
    public void test3() {
        IndividuoHormigaFactory facktory = new IndividuoHormigaFactory(new SantaFeMap(), 400, new GrowInitializer(5));
        for (Individuo ind: facktory.createMany(10)) {
            System.out.println(((IndividuoHormiga) ind).getRootNode().toString());
        }
    }
}