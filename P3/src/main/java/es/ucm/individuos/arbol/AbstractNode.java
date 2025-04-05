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
    
    public int getDepth() {
        if (isTerminal()) return 1;
        int maxChildDepth = 0;
        for (AbstractNode child : childNodes) {
            maxChildDepth = Math.max(maxChildDepth, child.getDepth());
        }
        return 1 + maxChildDepth;
    }

    public int getNodeCount() {
        if (isTerminal()) return 1;
        int count = 1;
        for (AbstractNode child : childNodes) {
            count += child.getNodeCount();
        }
        return count;
    }
    
    /**
     * Devuelve el tamaño total del árbol (número de nodos)
     */
    public int getTreeSize() {
        int size = 1; // contamos este nodo
        for (AbstractNode child : childNodes) {
            if (child != null) {
                size += child.getTreeSize();
            }
        }
        return size;
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

    /**
     * Devuelve la lista con todos los nodos que son funciones del conjunto
     * que engloba tanto al nodo mismo y a todos sus descendientes
     */
    public List<AbstractNode> getAllFunctionalNodes() {
        List<AbstractNode> funcNodes = new ArrayList<>();
        if (!isTerminal()) {
            funcNodes.add(this);
            for (AbstractNode child : childNodes) {
                funcNodes.addAll(child.getAllFunctionalNodes());
            }
        }

        return funcNodes;
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
