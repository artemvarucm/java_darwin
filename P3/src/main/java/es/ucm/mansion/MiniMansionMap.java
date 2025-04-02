package es.ucm.mansion;

import es.ucm.mansion.objects.Room;

/**
 * Mini mapa
 */
public class MiniMansionMap extends AbstractMansionMap {
    private static final int N_ROWS = 6;
    private static final int N_COLS = 6;
    private static final int BASE_ROW = 3;
    private static final int BASE_COL = 3;

    public MiniMansionMap() {
        super(N_ROWS, N_COLS, BASE_ROW, BASE_COL);
        this.colocarHabitaciones();
        this.colocarObstaculos();
    }

    private void colocarObstaculos() {
        // Obstacles that create paths with forced turns
        this.addObstacle(2, 2);
        this.addObstacle(2, 4);
        this.addObstacle(3, 4);
        this.addObstacle(3, 1);
        this.addObstacle(3, 5);
    }

    private void colocarHabitaciones() {
        // Rooms with multiple possible paths
        this.addRoom(new Room(1, "Sala", 0, 1));
        this.addRoom(new Room(2, "Cocina", 5, 0));
        this.addRoom(new Room(3, "Dormitorio", 5, 5));
        this.addRoom(new Room(4, "Baño", 0, 4));
    }
}
