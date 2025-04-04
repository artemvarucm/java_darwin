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

    public Integer getChildrenSize() {
        return this.childNodes.size();
    }

    public void setChildNode(int i, AbstractNode node) {
        this.childNodes.set(i, node);
    }

    /**
     * Mueve la hormiga (incluyendo cambios de direccion)
     * y devuelve la ruta como coordenadas (no incluye posición incial)
     */
    public List<Coord> walkAndReturnCoords(Hormiga hormiga) {
        return null;
    }
}
