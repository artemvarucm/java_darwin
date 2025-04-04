package es.ucm.individuos.arbol;


import java.util.List;

import static org.junit.Assert.assertEquals;

public class AbstractNodeTest {
    public void checkCorrectPath(AbstractNode node, Coord startCoord, DirectionEnum startDir, Coord endCoord, DirectionEnum endDir) {
        Hormiga h = new Hormiga(startCoord, startDir);
        List<Coord> cords = node.walkAndReturnCoords(h);
        System.out.println("RUTA");
        for (Coord c: cords)
            System.out.println(c.getRow() + ", " + c.getCol());

        assertEquals(h.getCoord().getRow(), endCoord.getRow());
        assertEquals(h.getCoord().getCol(), endCoord.getCol());
        assertEquals(h.getDir(), endDir);
    }
}