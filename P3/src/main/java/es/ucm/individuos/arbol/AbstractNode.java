package es.ucm.individuos.arbol;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

public abstract class AbstractNode {
    private AbstractNode parentNode;
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
        node.setParentNode(this);
        this.childNodes.set(i, node);
    }

    public AbstractNode getChildNode(int i) {
        return this.childNodes.get(i);
    }

    /**
     * Devuelve la lista con todos los nodos que son
     * Si terminal = true - terminales
     * Si terminal = false - funcionales
     * que engloba tanto al nodo mismo y a todos sus descendientes
     */
    public List<AbstractNode> getNodesOfType(boolean terminal) {
        List<AbstractNode> result = new ArrayList<>();
        if (terminal && isTerminal()) {
            result.add(this);
        } else if (!terminal && !isTerminal()) {
            result.add(this);
        } else {
            for (AbstractNode child : childNodes) {
                if (child != null)
                    result.addAll(child.getNodesOfType(terminal));
            }
        }

        return result;
    }

    public List<AbstractNode> getChildNodes() {
        return new ArrayList<>(childNodes);
    }

    /**
     * Devuelve la profundidad total del árbol (número de nodos)
     */
    public int getDepth() {
        if (isTerminal()) return 1;
        int maxChildDepth = 0;
        for (AbstractNode child : childNodes) {
            maxChildDepth = Math.max(maxChildDepth, child.getDepth());
        }
        return 1 + maxChildDepth;
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
    public void copyChildrenToClone(AbstractNode clone) {
        this.parentNode = null; // en caso contrario, más abajo
        clone.childNodes.clear();
        for (AbstractNode child: childNodes) {
            if (isNull(child)) {
                clone.childNodes.add(null);
            } else {
                AbstractNode childClone = child.clone();
                childClone.setParentNode(clone);
                clone.childNodes.add(childClone);
            }
        }
    }

    public AbstractNode getParentNode() {
        return parentNode;
    }

    public void setParentNode(AbstractNode parentNode) {
        this.parentNode = parentNode;
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
            if (child != null) {
                stringBuilder.append(child.toStringStartWith(start + "\t"));
            } else {
                stringBuilder.append(start + "\t").append("NULL");
            }
        }

        return stringBuilder.toString();
    }
}
