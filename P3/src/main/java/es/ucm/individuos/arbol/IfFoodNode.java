package es.ucm.individuos.arbol;

import java.util.LinkedList;
import java.util.List;

/**
 * Acepta 2 nodos (terminales o no)
 * Si hay comida en la posicion actual expande el primero,
 * en caso constrario - el segundo
 */
public class IfFoodNode extends AbstractNode {
    public IfFoodNode() {
        this(null, null);
    }
    public IfFoodNode(AbstractNode node1, AbstractNode node2) {
        this.childNodes.add(node1);
        this.childNodes.add(node2);
    }
    @Override
    public List<Coord> walkAndReturnCoords(Hormiga hormiga) {
        List<Coord> path = new LinkedList<>();
        boolean food = true;

        if (food)
            path.addAll(this.childNodes.get(0).walkAndReturnCoords(hormiga));
        else
            path.addAll(this.childNodes.get(1).walkAndReturnCoords(hormiga));

        return path;
    }

    public AbstractNode clone() {
        AbstractNode clon = new IfFoodNode();
        this.copyToClone(clon);
        return clon;
    }

    @Override
    public String getNodeName() {
        return "IF_FOOD";
    }
}