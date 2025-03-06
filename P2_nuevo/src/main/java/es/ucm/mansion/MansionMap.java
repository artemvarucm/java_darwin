package es.ucm.mansion;

import es.ucm.mansion.objects.Room;

public class MansionMap extends AbstractMansionMap {
    private static int N_ROWS = 15;
    private static int N_COLS = 15;
    private static int BASE_ROW = 7;
    private static int BASE_COL = 7;
    public MansionMap() {
        super(N_ROWS, N_COLS, BASE_ROW, BASE_COL);
        this.colocarHabitaciones();
        this.colocarObstaculos();
    }

    private void colocarObstaculos() {
        // Pared horizontal: fila 5, columnas 5 a 9
        for (int j = 5; j <= 9; j++) {
            this.addObstacle(5, j);
        }
        // Pared vertical: columna 10, filas 8 a 12
        for (int i = 8; i <= 12; i++) {
            this.addObstacle(i, 10);
        }
        // Obstáculos individuales: (10,3) y (11,4)
        this.addObstacle(10, 3);
        this.addObstacle(11, 4);
        // Obstáculo adicional – pared vertical: columna 6, filas 10 a 13
        for (int i = 10; i <= 13; i++) {
            this.addObstacle(i, 6);
        }
        // Obstáculo adicional – pared horizontal: fila 8, columnas 1 a 4
        for (int j = 1; j <= 4; j++) {
            this.addObstacle(8, j);
        }
        // Obstáculos en esquina superior derecha: (0,13) y (1,13)
        this.addObstacle(0, 13);
        this.addObstacle(1, 13);
        // Obstáculo adicional – pared horizontal: fila 3, columnas 8 a 11
        for (int j = 8; j <= 11; j++) {
            this.addObstacle(3, j);
        }
    }

    private void colocarHabitaciones() {
        this.addRoom(new Room(1, "Sala", 2, 2));
        this.addRoom(new Room(2, "Cocina", 2, 12));
        this.addRoom(new Room(3, "Dormitorio", 12, 2));
        this.addRoom(new Room(4, "Baño", 12, 12));
        this.addRoom(new Room(5, "Comedor", 2, 7));
        this.addRoom(new Room(6, "Oficina", 7, 2));
        this.addRoom(new Room(7, "Estudio", 7, 12));
        this.addRoom(new Room(8, "Lavandería", 12, 7));
        this.addRoom(new Room(9, "Terraza", 0, 7));
        this.addRoom(new Room(10, "Garaje", 7, 0));
        this.addRoom(new Room(11, "Jardín", 14, 7));
        this.addRoom(new Room(12, "Sótano", 7, 14));
        this.addRoom(new Room(13, "Balcón", 0, 0));
        this.addRoom(new Room(14, "Despacho", 0, 14));
        this.addRoom(new Room(15, "Vestíbulo", 14, 0));
        this.addRoom(new Room(16, "Cuarto de lavado", 14, 14));
        this.addRoom(new Room(17, "Sala de estar", 4, 4));
        this.addRoom(new Room(18, "Sala de juegos", 4, 12));
        this.addRoom(new Room(19, "Sala de TV", 10, 4));
        this.addRoom(new Room(20, "Sala de reuniones", 10, 12));
    }
}
