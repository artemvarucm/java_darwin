package es.ucm.cross;

import es.ucm.factories.IndividuoFactory;
import es.ucm.individuos.Individuo;

import java.util.List;

public abstract class AbstractCrossTest {
    public void crossTest(AbstractCross crosser, IndividuoFactory factory) {
        // 100 cruces
        for (int i = 0; i < 100; i++) {
            oneCross(crosser, factory);
        }
    }

    private void oneCross(AbstractCross crosser, IndividuoFactory factory) {
        Individuo par1 = factory.createOne();
        Individuo par2 = factory.createOne();
        System.out.println("PADRES");
        par1.printGenotipo();
        par2.printGenotipo();
        List<Individuo> hijos = crosser.cross(par1, par2);
        System.out.println("HIJOS");
        hijos.get(0).printGenotipo();
        hijos.get(1).printGenotipo();
    }
}