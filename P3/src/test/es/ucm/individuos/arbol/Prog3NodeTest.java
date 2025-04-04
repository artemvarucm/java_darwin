package es.ucm.individuos.arbol;

import org.junit.Test;


public class Prog3NodeTest extends AbstractNodeTest {
    @Test
    public void test1() {
        AbstractNode node1 = new LeftNode();
        AbstractNode node2 = new ForwardNode();
        AbstractNode node3 = new LeftNode();

        this.checkCorrectPath(new Prog3Node(node1, node2, node3), new Coord(1, 1), DirectionEnum.EAST, new Coord(0, 1), DirectionEnum.WEST);
    }
}