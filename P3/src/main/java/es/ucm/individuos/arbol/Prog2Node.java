package es.ucm.individuos.arbol;

import java.util.LinkedList;
import java.util.List;

/**
 * Acepta 2 nodos (terminales o no)
 * expandiendo primero uno (en profundidad), luego otro
 */
public class Prog2Node extends AbstractNode {
    public Prog2Node(AbstractNode node1, AbstractNode node2) {
        this.childNodes.add(node1);
        this.childNodes.add(node2);
    }
    @Override
    public List<Coord> walkAndReturnCoords(Hormiga hormiga) {
        List<Coord> path = new LinkedList<>();

        path.addAll(this.childNodes.get(0).walkAndReturnCoords(hormiga));

        path.addAll(this.childNodes.get(1).walkAndReturnCoords(hormiga));

        return path;
    }

    public AbstractNode clone() {
        AbstractNode clon = new Prog2Node(null, null);
        this.copyToClone(clon);
        return clon;
    }

    @Override
    public String getNodeName() {
        return "PROG_2";
    }
}
