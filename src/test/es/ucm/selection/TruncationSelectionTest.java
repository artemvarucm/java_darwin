package es.ucm.selection;

import es.ucm.factories.Individuo1Factory;
import es.ucm.factories.Individuo2Factory;
import es.ucm.factories.IndividuoFactory;
import es.ucm.individuos.Individuo;
import es.ucm.individuos.Individuo1;
import es.ucm.individuos.Individuo2;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TruncationSelectionTest {
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
            Individuo ind = new Individuo2(0.0001);
            ind.printGenotipo();
            System.out.println(ind.getFitness());
            poblacion.add(ind);
        }

        IndividuoFactory factory = new Individuo2Factory(0.0001);
        TruncationSelection selector = new TruncationSelection(factory, 0.5);
        List<Individuo> seleccion = selector.select(poblacion);
        System.out.println("INDIVIDUOS SELECCIONADOS");
        for (Individuo ind: seleccion) {
            ind.printGenotipo();
        }
        assertEquals(seleccion.size(), poblacion.size());
    }
}