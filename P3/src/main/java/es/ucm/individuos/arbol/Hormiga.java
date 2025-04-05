package es.ucm.individuos.arbol;

import es.ucm.mapa.AbstractFoodMap;

import java.util.Objects;

/**
 * Clase que representa la hormiga en un instante
 * (sirve para guardar su posición y la dirección en la que mira)
 */

public class Hormiga {
    private Coord position;
    private DirectionEnum direction;
    private AbstractFoodMap map;
    private Integer eatenFood; // cuantos trozos ha comido
    private Integer steps; // cuantos pasos ha hecho
    private Integer stepsLimit; // limite de pasos
    public Hormiga(AbstractFoodMap map, Integer stepsLimit) {
        this.map = map;
        map.fillFoodGrid();
        this.steps = 0;
        this.eatenFood = 0;
        this.stepsLimit = stepsLimit;
        setPosition(new Coord(0, 0));
        this.direction = DirectionEnum.EAST;
    }

    public void step() {
        // Ha realizado una acción, incrementar cuenta de pasos
        this.steps++;
    }

    public boolean shouldStop() {
        return ((Objects.equals(this.steps, stepsLimit)) || (this.eatenFood == this.map.getAllFoodCount()));
    }

    public Coord getPosition() { return position; }

    public void setPosition(Coord pos) {
        if (map.hasFood(pos)) {
            this.eatenFood++;
            map.eatFood(pos);
        }

        this.position = pos;
    }
    
    public Integer getSteps() {
        return this.steps;
    }

    public DirectionEnum getDir() { return direction; }
    public void setDir(DirectionEnum dir) { this.direction = dir; }

    public Integer getEatenFood() {
        return this.eatenFood;
    }

    /**
     * usado en IfFood
     * devuelve true si hay comida delante
     */
    public boolean hasFoodInFront() {
        return map.hasFood(getFrontPosition());
    }

    /**
     * Devuelve la posición si se mueve hacia delante en la dirección actual
     */
    public Coord getFrontPosition() {
        int row = position.getRow();
        int col = position.getCol();
        
        switch(direction) {
            case NORTH: row = (row - 1 + map.getNRows()) % map.getNRows(); break;
            case SOUTH: row = (row + 1) % map.getNRows(); break;
            case EAST: col = (col + 1) % map.getNCols(); break;
            case WEST: col = (col - 1 + map.getNCols()) % map.getNCols(); break;
        }

        return new Coord(row, col);
    }
}