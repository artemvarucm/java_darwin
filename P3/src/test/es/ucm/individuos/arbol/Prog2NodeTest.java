package es.ucm.individuos.arbol;

import org.junit.Test;


public class Prog2NodeTest extends AbstractNodeTest {
    @Test
    public void test1() {
        AbstractNode node1 = new LeftNode();
        AbstractNode node2 = new ForwardNode();

        this.checkCorrectPath(new Prog2Node(node1, node2), new Coord(1, 1), DirectionEnum.EAST, new Coord(0, 1), DirectionEnum.NORTH);
    }
}