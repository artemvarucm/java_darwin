package es.ucm.individuos.tree;

import java.util.LinkedList;
import java.util.List;

import static java.util.Objects.isNull;

/**
 * Acepta 2 nodos (terminales o no)
 * Si hay comida en la posicion de delante expande el primero,
 * en caso constrario - el segundo
 */
public class IfFoodNode extends AbstractNode {
    public IfFoodNode() {
    }
    public IfFoodNode(AbstractNode node1, AbstractNode node2) {
        node1.setParentNode(this);
        node2.setParentNode(this);

        this.childNodes.add(node1);
        this.childNodes.add(node2);
    }
    @Override
    public List<Coord> walkAndReturnCoords(Hormiga hormiga) {
        if (hormiga.shouldStop()) {
            return new LinkedList<>();
        }

        List<Coord> path = new LinkedList<>();
        if (hormiga.hasFoodInFront())
            path.addAll(this.childNodes.get(0).walkAndReturnCoords(hormiga));
        else
            path.addAll(this.childNodes.get(1).walkAndReturnCoords(hormiga));

        return path;
    }

    public AbstractNode clone() {
        AbstractNode clon = new IfFoodNode();
        this.copyChildrenToClone(clon);
        return clon;
    }
    
    @Override
    public String getNodeName() {
        return "IF_FOOD";
    }

    @Override
    public Integer getChildrenSize() {
        return 2;
    }
}