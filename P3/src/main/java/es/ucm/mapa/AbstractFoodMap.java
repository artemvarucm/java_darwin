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
        foodCoords.add(coord);
    }
    
    public int getCurrentFoodCount() {
        int count = 0;
        for (Coord coord : foodCoords) {
            if (foodGrid[coord.getRow()][coord.getCol()]) {
                count++;
            }
        }
        return count;
    }

    public void fillFoodGrid() {
        // crea el tablero con 0's
        this.foodGrid = new boolean[nRows][nCols];

        // rellena casillas con comida
        for (Coord food: foodCoords)
            foodGrid[food.getRow()][food.getCol()] = true;
    }

    public int getAllFoodCount() {
        return foodCoords.size();
    }

    public List<Coord> getFoodCoords() {
        return foodCoords;
    }

    public boolean hasFood(Coord coord) {
        return foodGrid[coord.getRow()][coord.getCol()];
    }

    public void eatFood(Coord coord) {
        if (hasFood(coord))
            foodGrid[coord.getRow()][coord.getCol()] = false;
        else
            throw new RuntimeException("NO HAY COMIDA EN (" + coord.getRow() + ", " + coord.getCol() + ")");
    }

    public int getNRows() {
        return nRows;
    }

    public int getNCols() {
        return nCols;
    }
}