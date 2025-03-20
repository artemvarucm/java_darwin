package es.ucm.mansion.busqueda;

import es.ucm.mansion.AbstractMansionMap;
import es.ucm.mansion.objects.Obstacle;

import java.util.ArrayList;
import java.util.List;

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
        List<NodoCamino> nodosAbiertos = new ArrayList<>();
        List<NodoCamino> nodosCerrados = new ArrayList<>();
        // Nodo inicial
        NodoCamino init = new NodoCamino(rowA, colA, rowB, colB, prevNode);
        init.addPenalty(mapa.getPenalty(init));
        nodosAbiertos.add(init);

        //System.out.println("Objetivo: " + rowA + ", " + colA + " -> " + rowB + ", " + colB);
        while (!nodosAbiertos.isEmpty()) {
            NodoCamino current = getNodeWithMinCost(nodosAbiertos);
            if (current.checkGoal()) {
                // Devolvemos la solución

                List<NodoCamino> camino = current.reconstructPath();
                if (!isNull(prevNode))
                    // el primer elemento sobra (es el nodo anterior al punto A)
                    camino.remove(0);

                return camino;
            }
            //System.out.println("Expandiendo: " + current.getRow() + ", " + current.getCol());
            nodosAbiertos.remove(current);
            nodosCerrados.add(current);
            // 4 movimientos posibles (left, right, up, down)
            for (MovementEnum move: MovementEnum.values()) {
                int row = current.getRow() + move.rowDelta;
                int col = current.getCol() + move.colDelta;
                if (
                    (col >= mapa.getNCols() || col < 0)
                        ||
                    (row >= mapa.getNRows() || row < 0)
                        ||
                    (!isNull(mapa.getGrid()[row][col]) && mapa.getGrid()[row][col].isObstacle())
                ) {
                    // movimiento inválido, saltar
                    continue;
                }

                NodoCamino nodo = new NodoCamino(row, col, rowB, colB, current);
                // penalizacion del nodo
                nodo.addPenalty(mapa.getPenalty(nodo));

                // si el nodo está cerrado, NO lo añadimos
                if (!nodosCerrados.contains(nodo)) {
                    if (nodosAbiertos.contains(nodo)) {
                        // si ya está abierto, vemos si el coste es menor
                        NodoCamino existente = nodosAbiertos.get(nodosAbiertos.indexOf(nodo));
                        if (nodo.getTotalEstimatedCost() < existente.getTotalEstimatedCost()) {
                            nodosAbiertos.remove(existente);
                            nodosAbiertos.add(nodo);
                            //System.out.println("Añadido: " + nodo.getRow() + ", " + nodo.getCol());
                        }
                    } else {
                        nodosAbiertos.add(nodo);
                        //System.out.println("Añadido: " + nodo.getRow() + ", " + nodo.getCol());
                    }
                }
            }
        }

        return null; // no hay ningún camino
    }

    /**
     * Devuelve el nodo del minimo coste de la lista (en caso de empate, se coge el posterior)
      */
    private NodoCamino getNodeWithMinCost(List<NodoCamino> nodos) {
        NodoCamino minimo = nodos.get(0);
        for (NodoCamino n: nodos) {
            if (minimo.getTotalEstimatedCost() >= n.getTotalEstimatedCost()) {
                minimo = n;
            }
        }

        return minimo;
    }
}
