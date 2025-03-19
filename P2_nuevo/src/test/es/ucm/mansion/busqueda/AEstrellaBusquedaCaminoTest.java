package es.ucm.mansion.busqueda;

import es.ucm.mansion.AbstractMansionMap;
import es.ucm.mansion.MansionMap;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class AEstrellaBusquedaCaminoTest {

    @Test
    public void calculateCostAtoB() {
        AEstrellaBusquedaCamino busqueda = new AEstrellaBusquedaCamino(new MansionMap());
        assertEquals(7.0, busqueda.calculateCostAtoB(7, 7, 7, 0), 0.00000000001);
    }

    @Test
    public void busquedaTest() {
        AbstractMansionMap map = new MansionMap();
        List<Number> optimum = List.of(19,3,15,10,6,17,1,13,9,5,2,14,18,7,12,20,4,16,11,8);
        assertEquals(92.0, map.calculateFitness(optimum), 0.00000000001);
        for (NodoCamino nodo: map.calculatePath(optimum))
            System.out.println(nodo.getRow() + ", " + nodo.getCol());
    }
}

