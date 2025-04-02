package es.ucm.individuos.arbol;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractNode {
    protected List<AbstractNode> childNodes;
    public AbstractNode() {
        this.childNodes = new ArrayList<>();
    }

    public boolean isTerminal() {
        return childNodes.isEmpty();
    }

    /**
     * Calcula el camino teniendo en cuenta que
     * la hormiga está en la posición y con la dirección especificada
     * @return
     */
    public List<Coord> getActionList(Hormiga hormiga) {
        return null;
    }
}
