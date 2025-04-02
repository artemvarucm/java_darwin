package es.ucm.individuos.arbol;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ForwardNodeTest {

    @Test
    public void getActionList() {
        Hormiga h = new Hormiga(new Coord(0, 0), DirectionEnum.EAST);
        AbstractNode node = new ForwardNode();
        List<Coord> cords = node.getActionList(h);
        System.out.println("RUTA");
        for (Coord c: cords)
            System.out.println(c.getRow() + ", " + c.getCol());
        System.out.println("HORMIGA");
        System.out.println(h.getCoord().getRow() + ", " + h.getCoord().getCol());
        System.out.println(h.getDir());
    }
}