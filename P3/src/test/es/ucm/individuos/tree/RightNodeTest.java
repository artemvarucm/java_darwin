package es.ucm.individuos.tree;

import org.junit.Test;

public class RightNodeTest extends AbstractNodeTest{

    @Test
    public void test1() {
        this.checkCorrectPath(new RightNode(), new Coord(0, 0), DirectionEnum.EAST, new Coord(0, 0), DirectionEnum.SOUTH);
        this.checkCorrectPath(new RightNode(), new Coord(0, 0), DirectionEnum.SOUTH, new Coord(0, 0), DirectionEnum.WEST);
        this.checkCorrectPath(new RightNode(), new Coord(0, 0), DirectionEnum.WEST, new Coord(0, 0), DirectionEnum.NORTH);
        this.checkCorrectPath(new RightNode(), new Coord(0, 0), DirectionEnum.NORTH, new Coord(0, 0), DirectionEnum.EAST);
    }
}