package es.ucm.mapa;

import es.ucm.individuos.arbol.Coord;


import java.util.*;

import static java.util.Objects.isNull;

/**
 * Clase abstract que se utiliza para representar una mansión, con comandos utiles como calcular el fitness.
 * A partir de ella se pueden crear mapas con diferente distribución de habitaciones y obstáculos
 */
public abstract class AbstractFoodMap {
    protected int nRows; // shape del mapa
	protected int nCols;
    protected boolean[][] foodGrid;
    protected List<Coord> foodCoords;
    public AbstractFoodMap(int nRows, int nCols) {
        this.nRows = nRows;
        this.nCols = nCols;
        this.foodGrid = new boolean[nRows][nCols];
        this.foodCoords = new ArrayList<>();
    }

    public void addFood(Coord coord) {
        if (!foodGrid[coord.getRow()][coord.getCol()]) {
            foodGrid[coord.getRow()][coord.getCol()] = true;
            foodCoords.add(coord);
        } else {
            throw new RuntimeException("YA EXISTE COMIDA EN ESA POSICIÓN (" + coord.getRow() + ", " + coord.getCol() + ")");
        }
    }

    public int getCurrentFoodCount() {
        return foodCoords.size();
    }

    public List<Coord> getFoodCoords() {
        return foodCoords;
    }

    public boolean hasFood(Coord coord) {
        return foodGrid[coord.getRow()][coord.getCol()];
    }

    public int getNRows() {
        return nRows;
    }

    public int getNCols() {
        return nCols;
    }
}