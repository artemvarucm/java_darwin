package es.ucm.individuos.arbol;

import org.junit.Test;


public class LeftNodeTest extends AbstractNodeTest{

    @Test
    public void test1() {
        this.checkCorrectPath(new LeftNode(), new Coord(0, 0), DirectionEnum.EAST, new Coord(0, 0), DirectionEnum.NORTH);
        this.checkCorrectPath(new LeftNode(), new Coord(0, 0), DirectionEnum.NORTH, new Coord(0, 0), DirectionEnum.WEST);
        this.checkCorrectPath(new LeftNode(), new Coord(0, 0), DirectionEnum.WEST, new Coord(0, 0), DirectionEnum.SOUTH);
        this.checkCorrectPath(new LeftNode(), new Coord(0, 0), DirectionEnum.SOUTH, new Coord(0, 0), DirectionEnum.EAST);
    }
}