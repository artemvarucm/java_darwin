package es.ucm.selection;

import es.ucm.factories.Individuo1Factory;
import es.ucm.factories.IndividuoFactory;
import es.ucm.individuos.Individuo;
import es.ucm.individuos.Individuo1;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class RouletteSelectionTest {
    @Test
    public void select1() {
        selectN(1);
    }

    @Test
    public void select4() {
        selectN(4);
    }

    private void selectN(int N) {
        List<Individuo> poblacion = new ArrayList<>();

        System.out.println("POBLACION INICIAL");
        for (int i = 0; i < N; i++) {
            Individuo ind = new Individuo1();
            ind.printGenotipo();
            System.out.println(ind.getFitness());
            poblacion.add(ind);
        }

        IndividuoFactory factory = new Individuo1Factory();
        RouletteSelection selector = new RouletteSelection(factory);
        List<Individuo> seleccion = selector.select(poblacion);
        System.out.println("INDIVIDUOS SELECCIONADOS");
        for (Individuo ind: seleccion) {
            ind.printGenotipo();
        }
        assertEquals(seleccion.size(), poblacion.size());
    }
}