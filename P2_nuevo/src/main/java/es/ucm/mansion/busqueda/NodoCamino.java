package es.ucm.mansion.busqueda;


import java.util.LinkedList;
import java.util.List;

import static java.util.Objects.isNull;

/**
 * Representa un nodo del camino para que se puedan reanudar
 */
public class NodoCamino {
    private int row, col;
    private int objectiveRow, objectiveCol;
    private double realCostFromStart;
    private NodoCamino prevNode;

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

    /*
     * public double getHeuristica() {
        // Distancia Manhattan tradicional
        double manhattanDistance = Math.abs(objectiveRow - currentRow) + Math.abs(objectiveCol - currentCol);

        // Penalización por densidad de obstáculos
        double obstaclePenalty = calculateObstacleDensityPenalty();

        // Distancia euclidiana
        double euclideanDistance = Math.sqrt(Math.pow(objectiveRow - currentRow, 2) + Math.pow(objectiveCol - currentCol, 2));

        // Penalización si la distancia Manhattan es mayor que la euclidiana
        double deviationPenalty = Math.max(0, manhattanDistance - euclideanDistance) * 0.2;

        return manhattanDistance + obstaclePenalty + deviationPenalty;
    }

    private double calculateObstacleDensityPenalty() {
        double penalty = 0.0;
        int deltaRow = Integer.signum(objectiveRow - currentRow);
        int deltaCol = Integer.signum(objectiveCol - currentCol);

        // Verificar celdas en la dirección del objetivo
        int row = currentRow + deltaRow;
        int col = currentCol + deltaCol;
        while (row != objectiveRow || col != objectiveCol) {
            if (row >= 0 && row < mansionMap.getNRows() && col >= 0 && col < mansionMap.getNCols()) {
                if (mansionMap.getGrid()[row][col] instanceof Obstacle) {
                    penalty += 0.5; // Penalización por cada obstáculo en la dirección
                }
            }
            row += deltaRow;
            col += deltaCol;
        }

        return penalty;
    }
     *
     * */

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

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof NodoCamino)) {
            return false;
        }

        NodoCamino nodo = (NodoCamino) obj;
        return nodo.row == this.row && nodo.col == this.col;
    }
}
