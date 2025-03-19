package es.ucm.mansion.busqueda;

/**
 * Representa los movimientos posibles del robot
 */
public enum MovementEnum {
    LEFT(0, -1),
    RIGHT(0, 1),
    UP(-1, 0), // CUIDADO! para ir arriba es restando
    DOWN(1, 0);

    public final int rowDelta;
    public final int colDelta;

    MovementEnum(int rowDelta, int colDelta) {
        this.rowDelta = rowDelta;
        this.colDelta = colDelta;
    }
}
