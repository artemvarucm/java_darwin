package es.ucm.mansion;

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

    public AbstractMansionMap(int nRows, int nCols, int baseRow, int baseCol) {
        this.nRows = nRows;
        this.nCols = nCols;
        this.baseRow = baseRow;
        this.baseCol = baseCol;
        this.rooms = new HashMap<>();
        this.grid = new AbstractMansionObject[nRows][nCols];
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

    public int getBaseRow() {
        return baseRow;
    }

    public int getBaseCol() {
        return baseCol;
    }

    public void printMap() {
        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nCols; j++) {
                String str = "  ";

                if (!isNull(grid[i][j]))
                    str = grid[i][j].toString();

                System.out.printf("|%1$2%s", str);
            }
            System.out.println("|");
        }
    }

    public List<Room> getRooms() {
        List<Room> result = new ArrayList<>();
        for (Room value : rooms.values())
            result.add(value);

        return result;
    }

    private double calculateCostAtoB(int rowA, int colA, int rowB, int colB) {
        // fixme, manhattan para probar, reempoazar con A* para tener en cuenta obstaculos
        return Math.abs(rowA - rowB) + Math.abs(colA - colB);
    }

    public double calculateFitness(List<Number> roomOrder) {
        double totalCost = 0;
        int rowA = baseRow;
        int colA = baseCol;
        for (Number id: roomOrder) {
            int rowB = rooms.get(id).getRow();
            int colB = rooms.get(id).getCol();
            totalCost += calculateCostAtoB(rowA, colA, rowB, colB);
            rowA = rowB;
            colA = colB;
        }

        return totalCost;
    }
}