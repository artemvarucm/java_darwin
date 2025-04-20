package es.ucm.mutation;

import es.ucm.individuos.Individuo;
import es.ucm.individuos.IndividuoHormiga;
import es.ucm.individuos.arbol.*;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class HoistMutate extends AbstractMutate {
    public HoistMutate(double mutateProbability) {
        super(mutateProbability);
    }

    @Override
    public Individuo mutate(Individuo ind) {
        IndividuoHormiga indMutado = (IndividuoHormiga) ind.copy();
        double p = ThreadLocalRandom.current().nextDouble();
        if (p < mutateProbability) {
            AbstractNode root = indMutado.getRootNode();
            List<AbstractNode> funcNodes = root.getNodesOfType(false);

            if (!funcNodes.isEmpty()) {
                // Seleccionar un nodo funcional aleatorio (que no sea la raíz)
                AbstractNode selected = funcNodes.get(
                        ThreadLocalRandom.current().nextInt(funcNodes.size())
                );

                // Seleccionar un subárbol funcional dentro del nodo seleccionado
                List<AbstractNode> subFuncNodes = selected.getNodesOfType(false);
                if (!subFuncNodes.isEmpty()) {
                    AbstractNode subTree = subFuncNodes.get(
                            ThreadLocalRandom.current().nextInt(subFuncNodes.size())
                    );

                    // Reemplazar el nodo seleccionado con el subárbol
                    if (selected != root) {
                        AbstractNode parent = selected.getParentNode();
                        int childIndex = getChildIndex(parent, selected);
                        parent.setChildNode(childIndex, subTree.clone());
                    }
                }
            }
        }
        return indMutado;
    }

    private int getChildIndex(AbstractNode parent, AbstractNode child) {
        return parent.getChildNodes().indexOf(child);
    }
}