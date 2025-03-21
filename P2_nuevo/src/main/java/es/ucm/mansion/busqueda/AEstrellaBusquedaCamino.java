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
     * prevNode -> nodo anterior al punto A (para que el último nodo tenga el coste desde el origen, no desde A)
     */
    public List<NodoCamino> calculatePathFromAtoB(int rowA, int colA, int rowB, int colB, NodoCamino prevNode) {
        PriorityQueue<NodoCamino> nodosAbiertos = new PriorityQueue<>(Comparator.comparingDouble(NodoCamino::getTotalEstimatedCost));
        // guarda el nodo con el mejor coste (porque pueden haber nodos con mismas coordenadas, pero diferente coste)
        Map<Integer, NodoCamino> nodoMap = new HashMap<>();
        Set<NodoCamino> nodosCerrados = new HashSet<>();

        NodoCamino init = new NodoCamino(rowA, colA, rowB, colB, prevNode);
        init.addPenalty(mapa.getPenalty(init));
        nodosAbiertos.add(init);
        nodoMap.put(init.hashCode(), init);

        while (!nodosAbiertos.isEmpty()) {
            // Devuelve el nodo del minimo coste de la lista
            NodoCamino current = nodosAbiertos.poll();

            if (current.checkGoal()) {
                // Devolvemos la solución (solo el tramo de A a B)
                return current.reconstructPath();
            }
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

                // Si el nodo está cerrado, NO lo añadimos
                if (!nodosCerrados.contains(nodo)) {
                    // Si el nodo está en nodoMap y no está en cerrados -> está abierto
                    if (nodoMap.containsKey(nodo.hashCode())) {
                        // Si está abierto, y es mejor que el existente, se reemplaza
                        NodoCamino existente = nodoMap.get(nodo.hashCode());
                        if (nodo.getTotalEstimatedCost() < existente.getTotalEstimatedCost()) {
                            nodosAbiertos.remove(existente);
                            nodosAbiertos.add(nodo);
                            nodoMap.put(nodo.hashCode(), nodo);
                        }
                    } else {
                        // si no está abierto, se añade
                        nodosAbiertos.add(nodo);
                        nodoMap.put(nodo.hashCode(), nodo);
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
