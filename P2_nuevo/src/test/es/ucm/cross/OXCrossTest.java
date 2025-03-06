package es.ucm.cross;

import es.ucm.factories.IndividuoAspiradoraFactory;
import es.ucm.factories.IndividuoFactory;
import es.ucm.individuos.Individuo;
import es.ucm.individuos.IndividuoAspiradora;
import es.ucm.mansion.MansionMap;
import org.junit.Test;

import java.util.List;


public class OXCrossTest {

    @Test
    public void crossTest1() {
        Individuo par1 = new IndividuoAspiradora(new MansionMap());
        Individuo par2 = new IndividuoAspiradora(new MansionMap());
        par1.printGenotipo();
        par2.printGenotipo();
        IndividuoFactory factory = new IndividuoAspiradoraFactory();

        AbstractCross crosser = new OXCross(factory);
        List<Individuo> hijos = crosser.cross(par1, par2);
        hijos.get(0).printGenotipo();
        hijos.get(1).printGenotipo();
    }

}