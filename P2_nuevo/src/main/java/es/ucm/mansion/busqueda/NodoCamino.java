package es.ucm.mansion.busqueda;


import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.isNull;

/**
 * Representa un nodo del camino para que se puedan reanudar (usado en A*)
 */
public class NodoCamino {
    private int row, col;
    private int objectiveRow, objectiveCol;
    private double realCostFromStart; // coste acumulado (suma de costes hasta llegar al nodo)
    private NodoCamino prevNode;
    public NodoCamino(int row, int col, int objectiveRow, int objectiveCol) {
        this(row, col, objectiveRow, objectiveCol, null);
    }

    public NodoCamino(int row, int col, int objectiveRow, int objectiveCol, NodoCamino prevNode) {
        this.row = row;
        this.col = col;
        this.objectiveRow = objectiveRow;
        this.objectiveCol = objectiveCol;
        this.prevNode = prevNode;
        this.realCostFromStart = isNull(prevNode) ? 0 : prevNode.realCostFromStart + manhattanDist(row, col, prevNode.row, prevNode.col);
    }

    public Boolean checkGoal() {
        return row == objectiveRow && col == objectiveCol;
    }

    public Double getTotalEstimatedCost() {
        // suma el coste acumulado de ir del comienzo al nodo actual y la heuristica para ir al objetivo
        return this.getHeuristica() + this.getAcumulatedCostFromStart();
    }


    public double getAcumulatedCostFromStart() {
        return this.realCostFromStart;
    }

    public double getHeuristica() {
        return manhattanDist(objectiveRow, objectiveCol, row, col);
    }

    private double manhattanDist(int row1, int col1, int row2, int col2) {
        return Math.abs(row1 - row2) + Math.abs(col1 - col2);
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

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public NodoCamino getPrevNode() {
        return prevNode;
    }

    @Override
    public boolean equals(Object obj) {
        // para ver si el nodo en la lista de cerrados o abiertos
        if (!(obj instanceof NodoCamino)) {
            return false;
        }

        NodoCamino nodo = (NodoCamino) obj;
        return nodo.row == this.row && nodo.col == this.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }
}
