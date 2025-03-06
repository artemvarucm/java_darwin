package es.ucm.mansion;

import es.ucm.mansion.objects.AbstractMansionObject;
import es.ucm.mansion.objects.Obstacle;
import es.ucm.mansion.objects.Room;


import static java.util.Objects.isNull;

public abstract class AbstractMansionMap {
    private int nRows, nCols; // shape del mapa
    private int baseRow, baseCol; // coordenadas base
    private AbstractMansionObject[][] grid; // toma 3 valores: null(vacio), room, obstacle

    public AbstractMansionMap(int nRows, int nCols, int baseRow, int baseCol) {
        this.nRows = nRows;
        this.nCols = nCols;
        this.baseRow = baseRow;
        this.baseCol = baseCol;
        this.grid = new AbstractMansionObject[nRows][nCols];
    }

    public void addRoom(Room room) {
        if (isNull(grid[room.getRow()][room.getCol()])) {
            grid[room.getRow()][room.getCol()] = room;
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
}