package es.ucm.cross;

import es.ucm.Individuo;
import es.ucm.Individuo1;
import es.ucm.factories.Individuo1Factory;
import es.ucm.factories.IndividuoFactory;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class SinglePointCrossTest {

    @Test
    public void crossTest() {
        Individuo par1 = new Individuo1();
        Individuo par2 = new Individuo1();
        par1.printGenotipo();
        par2.printGenotipo();
        IndividuoFactory factory = new Individuo1Factory();

        AbstractCross crosser = new SinglePointCross(factory);
        List<Individuo> hijos = crosser.cross(par1, par2);
        hijos.get(0).printGenotipo();
        hijos.get(1).printGenotipo();
    }
}