package es.ucm.mansion.busqueda;

import es.ucm.mansion.AbstractMansionMap;
import es.ucm.mansion.objects.Obstacle;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

public class AEstrellaBusquedaCamino {
    AbstractMansionMap mapa;
    public AEstrellaBusquedaCamino(AbstractMansionMap mapa) {
        this.mapa = mapa;
    }


    public double calculateCostAtoB(int rowA, int colA, int rowB, int colB) {
        List<NodoCamino> nodos = calculatePathFromAtoB(rowA, colA, rowB, colB);
        if (isNull(nodos)) {
            // no existe camino entre los dos puntos
            return Integer.MAX_VALUE;
        }

        // el ultimo nodo tiene el coste completo
        return nodos.get(nodos.size() - 1).getRealCostFromStart();
    }

    /**
     * Calcula el de !MINIMO! coste camino del punto A al punto B
     */
    public List<NodoCamino> calculatePathFromAtoB(int rowA, int colA, int rowB, int colB) {
        List<NodoCamino> nodosAbiertos = new ArrayList<>();
        List<NodoCamino> nodosCerrados = new ArrayList<>();
        nodosAbiertos.add(new NodoCamino(rowA, colA, rowB, colB)); // nodo inicial

        while (!nodosAbiertos.isEmpty()) {

            NodoCamino current = new NodoCamino(rowA - 1, colA - 1, rowB, colB);
            if (current.checkGoal()) {
                // devolver la solución y el coste real
                return current.reconstructPath();
            }

            nodosAbiertos.remove(current);
            nodosCerrados.add(current);

            /* 4 movimientos posibles (derecha, izquierda, arriba, abajo)
            heuristica(rowA - 1, colA - 1, rowB, colB);
            heuristica(rowA - 1, colA + 1, rowB, colB);
            heuristica(rowA + 1, colA - 1, rowB, colB);
            heuristica(rowA + 1, colA + 1, rowB, colB);
             */
        }

        return null; // no hay ningún camino
    }

    private double getTotalEstimatedCost(NodoCamino nodo) {
        int row = nodo.getCurrentRow();
        int col = nodo.getCurrentCol();
        if (col >= mapa.getNCols() || col < 0) {
            // col fuera del tablero - invalido
            return Integer.MAX_VALUE;
        }
        else if (row >= mapa.getNCols() || row < 0) {
            // row fuera del tablero - invalido
            return Integer.MAX_VALUE;
        }
        else if (!isNull(mapa.getGrid()[row][col]) && mapa.getGrid()[row][col] instanceof Obstacle) {
            // si es obstaculo, no puede pasar por ahí
            return Integer.MAX_VALUE;
        }

        // suma el coste real de ir del comienzo al nodo actual y la heuristica para ir al objetivo
        return nodo.getHeuristica() + nodo.getRealCostFromStart();
    }
}
