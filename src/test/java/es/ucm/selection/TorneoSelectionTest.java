package es.ucm.selection;

import es.ucm.factories.Individuo1Factory;
import es.ucm.factories.IndividuoFactory;
import es.ucm.individuos.Individuo;
import es.ucm.individuos.Individuo1;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TorneoSelectionTest {

    @Test
    public void select() {
        List<Individuo> poblacion = new ArrayList<>();
        Individuo par1 = new Individuo1();
        Individuo par2 = new Individuo1();
        System.out.println("POBLACION INICIAL");
        par1.printGenotipo();
        par2.printGenotipo();
        poblacion.add(par1);
        poblacion.add(par2);

        IndividuoFactory factory = new Individuo1Factory();
        TorneoSelection torneo = new TorneoSelection(factory);
        List<Individuo> seleccion = torneo.select(poblacion);
        System.out.println("INDIVIDUOS SELECCIONADOS");
        for (Individuo ind: seleccion) {
            ind.printGenotipo();
        }
        assertEquals(seleccion.size(), poblacion.size());
    }
}