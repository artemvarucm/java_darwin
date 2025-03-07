package es.ucm.mansion;

import es.ucm.mansion.busqueda.AEstrellaBusquedaCamino;
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
    public AbstractMansionMap(int nRows, int nCols, int baseRow, int baseCol) {
        this.nRows = nRows;
        this.nCols = nCols;
        this.baseRow = baseRow;
        this.baseCol = baseCol;
        this.rooms = new HashMap<>();
        this.grid = new AbstractMansionObject[nRows][nCols];
        this.buscadorCamino = new AEstrellaBusquedaCamino(this);
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
}