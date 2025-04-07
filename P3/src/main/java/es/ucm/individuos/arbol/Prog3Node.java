package es.ucm.individuos.arbol;

import java.util.LinkedList;
import java.util.List;

import static java.util.Objects.isNull;

/**
 * Parecido a {@link Prog2Node}, pero con 3 nodos
 */
public class Prog3Node extends AbstractNode {
    public Prog3Node() {
        this(null, null, null);
    }
    public Prog3Node(AbstractNode node1, AbstractNode node2, AbstractNode node3) {
        if (!isNull(node1)) node1.setParentNode(this);
        if (!isNull(node2)) node2.setParentNode(this);
        if (!isNull(node3)) node3.setParentNode(this);

        this.childNodes.add(node1);
        this.childNodes.add(node2);
        this.childNodes.add(node3);
    }
    @Override
    public List<Coord> walkAndReturnCoords(Hormiga hormiga) {
        if (hormiga.shouldStop()) {
            return new LinkedList<>();
        }

        List<Coord> path = new LinkedList<>();

        if (childNodes.get(0) != null) {
            path.addAll(childNodes.get(0).walkAndReturnCoords(hormiga));
        }
        
        if (childNodes.get(1) != null) {
            path.addAll(childNodes.get(1).walkAndReturnCoords(hormiga));
        }
        
        if (childNodes.get(2) != null) {
            path.addAll(childNodes.get(2).walkAndReturnCoords(hormiga));
        }
        
        return path;
    }

    public AbstractNode clone() {
        AbstractNode clon = new Prog3Node();
        this.copyChildrenToClone(clon);
        return clon;
    }

    @Override
    public String getNodeName() {
        return "PROG_3";
    }
}