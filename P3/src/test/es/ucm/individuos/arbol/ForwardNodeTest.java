package es.ucm.individuos.arbol;

import org.junit.Test;


public class ForwardNodeTest extends AbstractNodeTest {
    @Test
    public void test1() {
        this.checkCorrectPath(new ForwardNode(), new Coord(1, 1), DirectionEnum.EAST, new Coord(1, 2), DirectionEnum.EAST);
        this.checkCorrectPath(new ForwardNode(), new Coord(1, 1), DirectionEnum.NORTH, new Coord(0, 1), DirectionEnum.NORTH);
        this.checkCorrectPath(new ForwardNode(), new Coord(1, 1), DirectionEnum.WEST, new Coord(1, 0), DirectionEnum.WEST);
        this.checkCorrectPath(new ForwardNode(), new Coord(1, 1), DirectionEnum.SOUTH, new Coord(2, 1), DirectionEnum.SOUTH);
    }
}