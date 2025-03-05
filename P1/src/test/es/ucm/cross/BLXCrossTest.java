package es.ucm.cross;

import es.ucm.factories.Individuo5Factory;
import es.ucm.factories.IndividuoFactory;
import es.ucm.individuos.Individuo;
import es.ucm.individuos.Individuo5;
import org.junit.Test;

import java.util.List;


public class BLXCrossTest {

    @Test
    public void crossTest1() {
        Individuo par1 = new Individuo5(2);
        Individuo par2 = new Individuo5(2);
        par1.printGenotipo();
        par2.printGenotipo();
        IndividuoFactory factory = new Individuo5Factory(2);

        AbstractCross crosser = new BLXCross(factory, 0.5);
        List<Individuo> hijos = crosser.cross(par1, par2);
        hijos.get(0).printGenotipo();
        hijos.get(1).printGenotipo();
    }

    @Test
    public void crossTest2() {
        Individuo par1 = new Individuo5(2);
        Individuo par2 = new Individuo5(2);
        par1.printGenotipo();
        par2.printGenotipo();
        IndividuoFactory factory = new Individuo5Factory(2);

        AbstractCross crosser = new BLXCross(factory, 0.);
        List<Individuo> hijos = crosser.cross(par1, par2);
        hijos.get(0).printGenotipo();
        hijos.get(1).printGenotipo();
    }
}