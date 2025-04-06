package es.ucm.mutation;

import es.ucm.individuos.Individuo;
import es.ucm.individuos.IndividuoHormiga;
import es.ucm.individuos.arbol.*;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class ExpansionMutate extends AbstractMutate {
    private final int maxDepth;
    
    public ExpansionMutate(double mutateProbability, int maxDepth) {
        super(mutateProbability);
        this.maxDepth = maxDepth;
    }

    @Override
    public Individuo mutate(Individuo ind) {
        IndividuoHormiga indMutado = (IndividuoHormiga) ind.copy();
        double p = ThreadLocalRandom.current().nextDouble();
        
        if (p < mutateProbability) {
            AbstractNode root = indMutado.getRootNode();
            
            // Verificar que el árbol no sea nulo
            if (root == null) {
                return indMutado;
            }
            
            List<AbstractNode> terminals = getAllTerminalNodes(root);
            
            if (!terminals.isEmpty() && root.getDepth() < maxDepth) {
                // Seleccionar un terminal aleatorio para expandir
                int selected = ThreadLocalRandom.current().nextInt(0, terminals.size());
                AbstractNode terminal = terminals.get(selected);
                AbstractNode parent = findParent(root, terminal);
                
                if (parent != null) {
                    int childIndex = getChildIndex(parent, terminal);
                    
                    // Crear nueva función con el terminal como hijo
                    AbstractNode newFunction = selectRandomFunction(1);
                    newFunction.setChildNode(0, terminal.clone());
                    
                    // Reemplazar el terminal con la nueva función
                    parent.setChildNode(childIndex, newFunction);
                }
            }
        }
        return indMutado;
    }

    private List<AbstractNode> getAllTerminalNodes(AbstractNode root) {
        List<AbstractNode> terminals = new java.util.ArrayList<>();
        collectTerminals(root, terminals);
        return terminals;
    }

    private void collectTerminals(AbstractNode node, List<AbstractNode> terminals) {
        if (node.isTerminal()) {
            terminals.add(node);
        } else {
            for (AbstractNode child : node.getChildNodes()) {
                collectTerminals(child, terminals);
            }
        }
    }

    private AbstractNode findParent(AbstractNode root, AbstractNode target) {
        if (root.getChildNodes().contains(target)) {
            return root;
        }
        
        for (AbstractNode child : root.getChildNodes()) {
            if (!child.isTerminal()) {
                AbstractNode found = findParent(child, target);
                if (found != null) {
                    return found;
                }
            }
        }
        return null;
    }

    private int getChildIndex(AbstractNode parent, AbstractNode child) {
        return parent.getChildNodes().indexOf(child);
    }

    private AbstractNode selectRandomFunction(int requiredArgs) {
        // Lista de funciones disponibles con sus aridades
        List<AbstractNode> candidates = List.of(
            new Prog2Node(),
            new Prog3Node(),
            new IfFoodNode()
        );
        
        // Filtrar por funciones con al menos los argumentos requeridos
        List<AbstractNode> validFunctions = candidates.stream()
            .filter(f -> f.getChildrenSize() >= requiredArgs)
            .toList();
        
        if (validFunctions.isEmpty()) {
            // Fallback: usar Prog2 si no hay funciones adecuadas
            return new Prog2Node();
        }
        
        return validFunctions.get(ThreadLocalRandom.current().nextInt(validFunctions.size())).clone();
    }
}