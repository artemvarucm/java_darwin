package es.ucm.mansion.busqueda;

import es.ucm.mansion.AbstractMansionMap;

import java.util.LinkedList;
import java.util.List;

import static java.util.Objects.isNull;

/**
 * Representa un nodo del camino para que se puedan reanudar
 */
public class NodoCamino {
    private int currentRow, currentCol;
    private int objectiveRow, objectiveCol;
    private double realCostFromStart;
    private NodoCamino prevNode;

    public NodoCamino(int currentRow, int currentCol, int objectiveRow, int objectiveCol, NodoCamino prevNode) {
        this.currentRow = currentRow;
        this.currentCol = currentCol;
        this.objectiveRow = objectiveRow;
        this.objectiveCol = objectiveCol;
        this.prevNode = prevNode;
        this.realCostFromStart = isNull(prevNode) ? 0 : prevNode.realCostFromStart + 1;
    }

    public Boolean checkGoal() {
        return currentRow == objectiveRow && currentCol == objectiveCol;
    }

    public Double getTotalEstimatedCost() {
        // suma el coste real de ir del comienzo al nodo actual y la heuristica para ir al objetivo
        return this.getHeuristica() + this.getRealCostFromStart();
    }


    public double getRealCostFromStart() {
        return this.realCostFromStart;
    }

    public double getHeuristica() {
        return Math.abs(objectiveRow - currentRow) + Math.abs(objectiveCol - currentCol);
    }

    public List<NodoCamino> reconstructPath() {
        List<NodoCamino> result= new LinkedList<>(); // tiene que ser vacía
        recursiveReconstructPath(result);

        return result;
    }

    private void recursiveReconstructPath(List<NodoCamino> result) {
        if (!isNull(prevNode)) {
            // si hay un nodo anterior, lo añadimos a la lista ejecutando recursión sobre él
            prevNode.recursiveReconstructPath(result);
        }

        // se autoañade a la lista
        result.add(this);
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public int getCurrentCol() {
        return currentCol;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof NodoCamino)) {
            return false;
        }

        NodoCamino nodo = (NodoCamino) obj;
        return nodo.currentRow == this.currentRow && nodo.currentCol == this.currentCol;
    }
}
