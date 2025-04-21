package es.ucm.individuos.tree;

import java.util.LinkedList;
import java.util.List;

import static java.util.Objects.isNull;

/**
 * Acepta 2 nodos (terminales o no)
 * expandiendo primero uno (en profundidad), luego otro
 */
public class Prog2Node extends AbstractNode {
    public Prog2Node() {
        this(null, null);
    }
    public Prog2Node(AbstractNode node1, AbstractNode node2) {
        if (!isNull(node1)) node1.setParentNode(this);
        if (!isNull(node2)) node2.setParentNode(this);

        this.childNodes.add(node1);
        this.childNodes.add(node2);
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
        
        return path;
    }

    public AbstractNode clone() {
        AbstractNode clon = new Prog2Node();
        this.copyChildrenToClone(clon);
        return clon;
    }
    @Override
    public String getNodeName() {
        return "PROG_2";
    }
}