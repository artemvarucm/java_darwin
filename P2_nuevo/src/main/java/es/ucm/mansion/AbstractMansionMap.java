package es.ucm.mansion;

import es.ucm.mansion.busqueda.AEstrellaBusquedaCamino;
import es.ucm.mansion.busqueda.NodoCamino;
import es.ucm.mansion.objects.AbstractMansionObject;
import es.ucm.mansion.objects.Obstacle;
import es.ucm.mansion.objects.Room;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;

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
    public void printMap() {
        System.out.println(getStringRepresentation());
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
            result.addAll(this.buscadorCamino.calculatePathFromAtoB(rowA, colA, rowB, colB));
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

    private double calculateFitnessWithObstaclePenalty(List<Number> roomOrder) {
        double fitness = this.calculateFitness(roomOrder);
        AEstrellaBusquedaCamino aStar = new AEstrellaBusquedaCamino(this);
        double penalty = 0.0;

        for (int i = 0; i < roomOrder.size() - 1; i++) {
            final int index = i; // Copia final de i para usar en la expresión lambda
            Room room1 = this.getRooms().stream()
                    .filter(r -> r.getId() == roomOrder.get(index).intValue())
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Habitación no encontrada"));
            Room room2 = this.getRooms().stream()
                    .filter(r -> r.getId() == roomOrder.get(index + 1).intValue())
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Habitación no encontrada"));

            double distance = aStar.calculateCostAtoB(room1.getRow(), room1.getCol(), room2.getRow(), room2.getCol());

            // Penalización si el camino pasa cerca de un obstáculo
            if (distance > 1.0) {
                penalty += distance * 0.1; // Ajusta el factor de penalización según sea necesario
            }
        }

        return fitness + penalty;
    }

    private double calculateFitnessWithTurnPenalty(List<Number> roomOrder) {
        double fitness = this.calculateFitness(roomOrder);
        double penalty = 0.0;

        for (int i = 1; i < roomOrder.size() - 1; i++) {
            final int index = i;
            Room prevRoom = this.getRooms().stream()
                    .filter(r -> r.getId() == roomOrder.get(index - 1).intValue())
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Habitación no encontrada"));
            Room currRoom = this.getRooms().stream()
                    .filter(r -> r.getId() == roomOrder.get(index).intValue())
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Habitación no encontrada"));
            Room nextRoom = this.getRooms().stream()
                    .filter(r -> r.getId() == roomOrder.get(index + 1).intValue())
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Habitación no encontrada"));

            int prevDirection = getDirection(prevRoom, currRoom);
            int nextDirection = getDirection(currRoom, nextRoom);

            if (prevDirection != nextDirection) {
                penalty += 1.0; // Penalización por cambio de dirección
            }
        }

        return fitness + penalty;
    }

    private int getDirection(Room from, Room to) {
        if (from.getRow() == to.getRow()) {
            return (from.getCol() < to.getCol()) ? 1 : 3; // Derecha (1) o Izquierda (3)
        } else {
            return (from.getRow() < to.getRow()) ? 2 : 4; // Abajo (2) o Arriba (4)
        }
    }
}