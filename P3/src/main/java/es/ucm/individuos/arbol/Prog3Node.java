package es.ucm.individuos.arbol;

import java.util.LinkedList;
import java.util.List;

/**
 * Parecido a {@link Prog2Node}, pero con 3 nodos
 */
public class Prog3Node extends AbstractNode {
    public Prog3Node() {
        this(null, null, null);
    }
    public Prog3Node(AbstractNode node1, AbstractNode node2, AbstractNode node3) {
        this.childNodes.add(node1);
        this.childNodes.add(node2);
        this.childNodes.add(node3);
    }
    @Override
    public List<Coord> walkAndReturnCoords(Hormiga hormiga) {
        List<Coord> path = new LinkedList<>();

        path.addAll(this.childNodes.get(0).walkAndReturnCoords(hormiga));

        path.addAll(this.childNodes.get(1).walkAndReturnCoords(hormiga));

        path.addAll(this.childNodes.get(2).walkAndReturnCoords(hormiga));

        return path;
    }

    public AbstractNode clone() {
        AbstractNode clon = new Prog3Node();
        this.copyToClone(clon);
        return clon;
    }

    @Override
    public String getNodeName() {
        return "PROG_3";
    }
}