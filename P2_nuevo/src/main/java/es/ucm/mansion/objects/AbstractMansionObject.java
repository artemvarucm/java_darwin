package es.ucm.mansion.objects;

/**
 * Clase abstracta para representa el objeto en un lugar de la mansión (obstaculo o habitacion)
 */
public abstract class AbstractMansionObject {
    protected final int row;
    protected final int col;

    public AbstractMansionObject(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public abstract boolean isObstacle();
}
