package es.ucm.mansion.objects;

/**
 * Clase que representa una habitación de la mansión
 */
public class Room extends AbstractMansionObject{
    private final int id;
    private final String name;

    public Room(int id, String name, int row, int col) {
        super(row, col);
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        // deprecated: usado cuando se mostraba por consola
        return String.valueOf(id);
    }
    @Override
    public boolean isObstacle() {
        return false;
    }
}