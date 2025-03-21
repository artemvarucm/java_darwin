package es.ucm.mansion.busqueda;

import es.ucm.mansion.AbstractMansionMap;

import java.util.*;

import static java.util.Objects.isNull;

/**
 * Algoritmo A*
 */
public class AEstrellaBusquedaCamino {
    AbstractMansionMap mapa;
    public AEstrellaBusquedaCamino(AbstractMansionMap mapa) {
        this.mapa = mapa;
    }

    /**
     * Calcula el de !MINIMO! coste camino del punto A al punto B
     * prevNode -> nodo anterior al punto A
     */
    public List<NodoCamino> calculatePathFromAtoB(int rowA, int colA, int rowB, int colB, NodoCamino prevNode) {
        PriorityQueue<NodoCamino> nodosAbiertos = new PriorityQueue<>(Comparator.comparingDouble(NodoCamino::getTotalEstimatedCost));
        Map<NodoCamino, NodoCamino> nodoMap = new HashMap<>(); // Stores the best known path for each node
        Set<NodoCamino> nodosCerrados = new HashSet<>();

        // Create the initial node
        NodoCamino init = new NodoCamino(rowA, colA, rowB, colB, prevNode);
        init.addPenalty(mapa.getPenalty(init));
        nodosAbiertos.add(init);
        nodoMap.put(init, init); // Store by unique key

        while (!nodosAbiertos.isEmpty()) {
            // Devuelve el nodo del minimo coste de la lista
            NodoCamino current = nodosAbiertos.poll();

            if (current.checkGoal()) {
                // Devolvemos la solución (solo el tramo de A a B)

                return current.reconstructPath();
            }
            //System.out.println("Expandiendo: " + current.getRow() + ", " + current.getCol());
            nodosAbiertos.remove(current);
            nodosCerrados.add(current);
            // 4 movimientos posibles (left, right, up, down)
            for (MovementEnum move: MovementEnum.values()) {
                int row = current.getRow() + move.rowDelta;
                int col = current.getCol() + move.colDelta;

                if (isInvalidMove(row, col))
                    continue;

                NodoCamino nodo = new NodoCamino(row, col, rowB, colB, current);
                // penalizacion del nodo
                nodo.addPenalty(mapa.getPenalty(nodo));

                // si el nodo está cerrado, NO lo añadimos
                if (!nodosCerrados.contains(nodo)) {
                    // If the node is in the open list and has a higher cost, update it
                    if (nodoMap.containsKey(nodo)) {
                        NodoCamino existente = nodoMap.get(nodo);
                        if (nodo.getTotalEstimatedCost() < existente.getTotalEstimatedCost()) {
                            nodosAbiertos.remove(existente);
                            nodosAbiertos.add(nodo);
                            nodoMap.put(nodo, nodo);
                        }
                    } else {
                        nodosAbiertos.add(nodo);
                        nodoMap.put(nodo, nodo);
                    }
                }
            }
        }

        return null; // no hay ningún camino
    }

    private boolean isInvalidMove(int row, int col) {
        return row < 0 || row >= mapa.getNRows() ||
                col < 0 || col >= mapa.getNCols() ||
                (mapa.getGrid()[row][col] != null && mapa.getGrid()[row][col].isObstacle());
    }
}
