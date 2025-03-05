package es.ucm.cross;

import es.ucm.individuos.Individuo;
import es.ucm.individuos.Individuo1;
import es.ucm.factories.Individuo1Factory;
import es.ucm.factories.IndividuoFactory;
import org.junit.Test;

import java.util.List;

public class SinglePointCrossTest {

    @Test
    public void crossTest() {
        Individuo par1 = new Individuo1(0.0001);
        Individuo par2 = new Individuo1(0.0001);
        par1.printGenotipo();
        par2.printGenotipo();
        IndividuoFactory factory = new Individuo1Factory(0.0001);

        AbstractCross crosser = new SinglePointCross(factory);
        List<Individuo> hijos = crosser.cross(par1, par2);
        hijos.get(0).printGenotipo();
        hijos.get(1).printGenotipo();
    }
}