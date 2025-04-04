package es.ucm.genes;


import es.ucm.individuos.arbol.AbstractNode;

/**
 * El gen que representa un arbol
 * GENOTIPO: Arbol
 */
public class TreeGen extends Gen<AbstractNode, AbstractNode> {
    private AbstractNode genotipo;

    public TreeGen(AbstractNode node) {
        this.tamGen = 1;
        this.genotipo = node;
    }

    public TreeGen(TreeGen gen) {
        this(gen.genotipo);
    }

    @Override
    public Gen clone() {
        AbstractNode clone = this.genotipo.clone();
        return new TreeGen(clone);
    }

    public AbstractNode getFenotipo() {
        return genotipo;
    }

    public String toString() {
        return genotipo.toString();
    }

    public void set(int index, AbstractNode value) {
        this.genotipo = value;
    }

    public AbstractNode get(int index) {
        return this.genotipo;
    }
}
