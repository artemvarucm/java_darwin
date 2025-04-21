package es.ucm.mutation.tree;

import es.ucm.individuos.Individuo;
import es.ucm.individuos.IndividuoHormigaArbol;
import es.ucm.individuos.tree.*;
import es.ucm.mutation.AbstractMutate;

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
        IndividuoHormigaArbol indMutado = (IndividuoHormigaArbol) ind.copy();
        double p = ThreadLocalRandom.current().nextDouble();
        
        if (p < mutateProbability) {
            AbstractNode root = indMutado.getRootNode();
            
            List<AbstractNode> terminals = root.getNodesOfType(true);
            
            if (!terminals.isEmpty() && root.getDepth() < maxDepth) {
                // Seleccionar un terminal aleatorio para expandir
                int selected = ThreadLocalRandom.current().nextInt(0, terminals.size());
                AbstractNode terminal = terminals.get(selected);
                AbstractNode parent = terminal.getParentNode();
                
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