package es.ucm.mansion.objects;

/**
 * Clase que representa un obstáculo
 */
public class Obstacle extends AbstractMansionObject {
    public Obstacle(int row, int col) {
        super(row, col);
    }

    @Override
    public boolean isObstacle() {
        return true;
    }

    @Override
    public String toString() {
        // deprecated: usado cuando se mostraba por consola
        return "■";
    }
}