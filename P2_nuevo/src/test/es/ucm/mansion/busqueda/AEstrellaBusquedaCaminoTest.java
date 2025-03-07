package es.ucm.mansion.busqueda;

import es.ucm.mansion.MansionMap;
import org.junit.Test;

import static org.junit.Assert.*;

public class AEstrellaBusquedaCaminoTest {

    @Test
    public void calculateCostAtoB() {
        AEstrellaBusquedaCamino busqueda = new AEstrellaBusquedaCamino(new MansionMap());
        assertEquals(7.0, busqueda.calculateCostAtoB(7, 7, 7, 0), 0.00000000001);
    }
}