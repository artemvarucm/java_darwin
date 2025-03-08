package es.ucm.cross;

import es.ucm.individuos.Individuo;
import es.ucm.individuos.IndividuoAspiradora;
import es.ucm.mansion.MansionMap;

import java.util.List;

public abstract class AbstractCrossTest {
    public void crossTest(AbstractCross crosser) {
        // 10000 cruces
        for (int i = 0; i < 10000; i++) {
            oneCross(crosser);
        }
    }

    private void oneCross(AbstractCross crosser) {
        Individuo par1 = new IndividuoAspiradora(new MansionMap());
        Individuo par2 = new IndividuoAspiradora(new MansionMap());
        par1.printGenotipo();
        par2.printGenotipo();
        List<Individuo> hijos = crosser.cross(par1, par2);
        hijos.get(0).printGenotipo();
        hijos.get(1).printGenotipo();
    }
}