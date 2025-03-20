package es.ucm.mansion;

import es.ucm.mansion.busqueda.AEstrellaBusquedaCamino;
import es.ucm.mansion.busqueda.MovementEnum;
import es.ucm.mansion.busqueda.NodoCamino;
import es.ucm.mansion.objects.AbstractMansionObject;
import es.ucm.mansion.objects.Obstacle;
import es.ucm.mansion.objects.Room;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;

/**
 * Clase abstract que se utiliza para representar una mansión, con comandos utiles como calcular el fitness.
 * A partir de ella se pueden crear mapas con diferente distribución de habitaciones y obstáculos
 */
public abstract class AbstractMansionMap {
    Map<Number, Room> rooms;// para saber donde está el room del id determinado
    private int nRows, nCols; // shape del mapa
    private int baseRow, baseCol; // coordenadas base
    private AbstractMansionObject[][] grid; // toma 3 valores: null(vacio), room, obstacle

    private AEstrellaBusquedaCamino buscadorCamino; // para calcular el fitness
    private double turnPenalty = 0; // penalización por giro
    private double obstaclePenalty = 0; // penalización por obstáculo

    public AbstractMansionMap(int nRows, int nCols, int baseRow, int baseCol) {
        this.nRows = nRows;
        this.nCols = nCols;
        this.baseRow = baseRow;
        this.baseCol = baseCol;
        this.rooms = new HashMap<>();
        this.grid = new AbstractMansionObject[nRows][nCols];
        this.buscadorCamino = new AEstrellaBusquedaCamino(this);
    }

    public void setTurnPenalty(double turnPenalty) {
        this.turnPenalty = turnPenalty;
    }

    public void setObstaclePenalty(double obstaclePenalty) {
        this.obstaclePenalty = obstaclePenalty;
    }

    public void addRoom(Room room) {
        if (isNull(grid[room.getRow()][room.getCol()])) {
            grid[room.getRow()][room.getCol()] = room;
            this.rooms.put(room.getId(), room);
        } else {
            throw new RuntimeException("LA HABITACIÓN NO PUEDE ESTAR EN EL MISMO SITIO QUE EL OBSTÁCULO.");
        }
    }

    public void addObstacle(int row, int col) {
        if (isNull(grid[row][col])) {
            grid[row][col] = new Obstacle(row, col);
        } else {
            throw new RuntimeException("EL OBSTÁCULO NO PUEDE ESTAR EN EL MISMO SITIO QUE LA HABITACIÓN.");
        }
    }

    public int getNRows() {
        return nRows;
    }

    public int getNCols() {
        return nCols;
    }

    public int getBaseRow() {
        return baseRow;
    }

    public int getBaseCol() {
        return baseCol;
    }

    public AbstractMansionObject[][] getGrid() {
        return grid;
    }


    public List<Room> getRooms() {
        List<Room> result = new ArrayList<>();
        for (Room value : rooms.values())
            result.add(value);

        return result;
    }


    public List<NodoCamino> calculatePath(List<Number> roomOrder) {
        List<NodoCamino> result = new ArrayList<>();
        int rowA = baseRow;
        int colA = baseCol;
        int rowB, colB;
        for (Number id: roomOrder) {
            rowB = rooms.get(id).getRow();
            colB = rooms.get(id).getCol();
            List<NodoCamino> ruta = this.buscadorCamino.calculatePathFromAtoB(rowA, colA, rowB, colB);
            if (isNull(ruta)) {
                throw new RuntimeException("RUTA NO ENCONTRADA");
            }
            result.addAll(ruta);
            // eliminamos el nodo final
            // porque lo meteremos como primero en la siguiente iteración
            result.remove(result.size() - 1);

            rowA = rowB;
            colA = colB;
        }

        // además tiene que volver a la base también
        rowB = baseRow;
        colB = baseCol;
        result.addAll(this.buscadorCamino.calculatePathFromAtoB(rowA, colA, rowB, colB));

        return result;
    }

    public double calculateFitness(List<Number> roomOrder) {
        double totalCost = 0;
        int rowA = baseRow;
        int colA = baseCol;
        int rowB, colB;
        for (Number id: roomOrder) {
            rowB = rooms.get(id).getRow();
            colB = rooms.get(id).getCol();
            totalCost += this.buscadorCamino.calculateCostAtoB(rowA, colA, rowB, colB);
            rowA = rowB;
            colA = colB;
        }

        // además tiene que volver a la base también
        rowB = baseRow;
        colB = baseCol;
        totalCost += this.buscadorCamino.calculateCostAtoB(rowA, colA, rowB, colB);

        return totalCost;
    }

    public double calculateFitnessWithPenalties(List<Number> roomOrder) {
        double rawFitness = calculateFitness(roomOrder);
        double penalizacion = 0;
        List<NodoCamino> ruta = calculatePath(roomOrder);
        for (NodoCamino nodo: ruta) {
            penalizacion += getPenalty(nodo);
        }

        return penalizacion + rawFitness;
    }

    public double getPenalty(NodoCamino nodo) {
        double penalty = 0;
        MovementEnum direccionAnterior = null;
        if (turnPenalty != 0 && !isNull(nodo.getPrevNode())) {
            // penalización por giros
            int row1 = nodo.getPrevNode().getRow();
            int col1 = nodo.getPrevNode().getCol();
            int row2 = nodo.getRow();
            int col2 = nodo.getCol();

            if (!isNull(nodo.getPrevNode().getPrevNode())) {
                int row0 = nodo.getPrevNode().getPrevNode().getRow();
                int col0 = nodo.getPrevNode().getPrevNode().getCol();
                direccionAnterior = getDireccion(row0, col0, row1, col1);
            }

            MovementEnum direccionActual = getDireccion(row1, col1, row2, col2);
            if (!isNull(direccionAnterior) && !direccionActual.equals(direccionAnterior)) {
                penalty += turnPenalty;
            }
        }

        // penalización por obstáculos cerca del camino
        penalty += obstaclePenalty * countObstaclesAroundPoint(nodo.getRow(), nodo.getCol());

        return penalty;
    }

    private MovementEnum getDireccion(int row1, int col1, int row2, int col2) {
        if (row1 - row2 == 0) {
            return col1 < col2 ? MovementEnum.RIGHT : MovementEnum.LEFT;
        } else {
            return row1 < row2 ? MovementEnum.DOWN : MovementEnum.UP;
        }
    }

    /**
     * Cuenta los obstáculos que hay en las celdas contiguas a la pasada por parametro
     */
    private int countObstaclesAroundPoint(int row, int col) {
        int count = 0;
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if (
                        (i == row && j == col)
                        || i < 0 || i >= nRows
                        || j < 0 || j >= nCols
                ) {
                    // posicion invalida (no se puede acceder)
                    continue;
                }

                if (!isNull(grid[i][j]) && grid[i][j].isObstacle()) {
                    count++;
                }
            }
        }

        return count;
    }

    /**
     * deprecated: para dibujar en consola el mapa
     */
    public String getStringRepresentation() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nCols; j++) {
                String str = "   ";

                if (!isNull(grid[i][j]))
                    str = grid[i][j].toString();

                result.append(String.format("|%1$2s", str));
            }
            result.append("|\n");
            for (int j = 0; j < nCols; j++) {
                result.append("--");
            }
            result.append("\n");
        }

        return result.toString();
    }

    /**
     * deprecated: para dibujar en consola el mapa
     */
    public void printMap() {
        System.out.println(getStringRepresentation());
    }
}