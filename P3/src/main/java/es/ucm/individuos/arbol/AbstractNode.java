package es.ucm.individuos.arbol;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

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

    public AbstractNode getChildNode(int i) {
        return this.childNodes.get(i);
    }

    /**
     * Mueve la hormiga (incluyendo cambios de direccion)
     * y devuelve la ruta como coordenadas (no incluye posición incial)
     */
    public abstract List<Coord> walkAndReturnCoords(Hormiga hormiga);


    public abstract AbstractNode clone();

    /**
     * Copia recursiva de los hijos al clon del parametro
     */
    public void copyToClone(AbstractNode clone) {
        clone.childNodes.clear();
        for (AbstractNode child: childNodes)
            clone.childNodes.add(
                    isNull(child) ? null : child.clone()
            );
    }

    public abstract String getNodeName();
    public String toString() {
        return toStringStartWith("");
    }

    private String toStringStartWith(String start) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(start);
        stringBuilder.append(getNodeName());
        for (AbstractNode child: childNodes) {
            stringBuilder.append("\n");
            stringBuilder.append(child.toStringStartWith(start + "\t"));
        }

        return stringBuilder.toString();
    }
}
