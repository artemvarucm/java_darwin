package es.ucm.mansion;

import java.util.ArrayList;
import java.util.List;

public class MansionMap {
    public static final int ROWS = 15;
    public static final int COLS = 15;
    // Carácter para obstáculo
    public static final char OBSTACLE_CHAR = '■';
    // Carácter para ruta (sobreescribe celdas transitables)
    public static final char ROUTE_CHAR = '*';
    // Carácter para la base
    public static final char BASE_CHAR = 'B';

    private char[][] grid;

    // Lista de habitaciones (id de 1 a 20)
    private List<Room> rooms;
    // Coordenadas de la base (fija en (7,7))
    private int baseRow = 7;
    private int baseCol = 7;

    public MansionMap() {
        grid = new char[ROWS][COLS];
        // Inicializamos con espacios en blanco
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                grid[i][j] = ' ';
            }
        }
        this.rooms = new ArrayList<>();
        crearHabitaciones();
        colocarHabitaciones();
        colocarObstaculos();
        colocarBase();
    }

    private void crearHabitaciones() {
        // Se crean las 20 habitaciones con sus coordenadas (fila, columna)
        // Los nombres y símbolos se pueden ajustar; en este ejemplo se usa el id como símbolo.
        rooms.add(new Room(1, "Sala", 2, 2));
        rooms.add(new Room(2, "Cocina", 2, 12));
        rooms.add(new Room(3, "Dormitorio", 12, 2));
        rooms.add(new Room(4, "Baño", 12, 12));
        rooms.add(new Room(5, "Comedor", 2, 7));
        rooms.add(new Room(6, "Oficina", 7, 2));
        rooms.add(new Room(7, "Estudio", 7, 12));
        rooms.add(new Room(8, "Lavandería", 12, 7));
        rooms.add(new Room(9, "Terraza", 0, 7));
        rooms.add(new Room(10, "Garaje", 7, 0));
        rooms.add(new Room(11, "Jardín", 14, 7));
        rooms.add(new Room(12, "Sótano", 7, 14));
        rooms.add(new Room(13, "Balcón", 0, 0));
        rooms.add(new Room(14, "Despacho", 0, 14));
        rooms.add(new Room(15, "Vestíbulo", 14, 0));
        rooms.add(new Room(16, "Cuarto de lavado", 14, 14));
        rooms.add(new Room(17, "Sala de estar", 4, 4));
        rooms.add(new Room(18, "Sala de juegos", 4, 12));
        rooms.add(new Room(19, "Sala de TV", 10, 4));
        rooms.add(new Room(20, "Sala de reuniones", 10, 12));
    }

    private void colocarHabitaciones() {
        // Poner en la grilla el id de cada habitación (como dígito o número)
        for (Room room : rooms) {
            int r = room.getRow();
            int c = room.getCol();
            // Para símbolos de dos dígitos se puede usar el dígito final o formatear la impresión
            String simbolo = String.valueOf(room.getId());
            // Si la celda ya tiene algún obstáculo o la base, se omite
            grid[r][c] = simbolo.charAt(0);
        }
    }

    private void colocarObstaculos() {
        // Obstáculos indicados:
        // Pared horizontal: fila 5, columnas 5 a 9
        for (int j = 5; j <= 9; j++) {
            grid[5][j] = OBSTACLE_CHAR;
        }
        // Pared vertical: columna 10, filas 8 a 12
        for (int i = 8; i <= 12; i++) {
            grid[i][10] = OBSTACLE_CHAR;
        }
        // Obstáculos individuales: (10,3) y (11,4)
        grid[10][3] = OBSTACLE_CHAR;
        grid[11][4] = OBSTACLE_CHAR;
        // Obstáculo adicional – pared vertical: columna 6, filas 10 a 13
        for (int i = 10; i <= 13; i++) {
            grid[i][6] = OBSTACLE_CHAR;
        }
        // Obstáculo adicional – pared horizontal: fila 8, columnas 1 a 4
        for (int j = 1; j <= 4; j++) {
            grid[8][j] = OBSTACLE_CHAR;
        }
        // Obstáculos en esquina superior derecha: (0,13) y (1,13)
        grid[0][13] = OBSTACLE_CHAR;
        grid[1][13] = OBSTACLE_CHAR;
        // Obstáculo adicional – pared horizontal: fila 3, columnas 8 a 11
        for (int j = 8; j <= 11; j++) {
            grid[3][j] = OBSTACLE_CHAR;
        }
    }

    private void colocarBase() {
        grid[baseRow][baseCol] = BASE_CHAR;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public int getBaseRow() {
        return baseRow;
    }

    public int getBaseCol() {
        return baseCol;
    }

    /**
     * Devuelve la representación actual del mapa.
     */
    public void printMap() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                System.out.printf("|%2c", grid[i][j]);
            }
            System.out.println("|");
        }
    }

    /**
     * Crea una copia de la grilla base para poder dibujar una ruta sin perder la configuración original.
     */
    public char[][] getGridCopy() {
        char[][] copy = new char[ROWS][COLS];
        for (int i = 0; i < ROWS; i++) {
            System.arraycopy(grid[i], 0, copy[i], 0, COLS);
        }
        return copy;
    }

    /**
     * Dibuja sobre la grilla copiada la ruta indicando el camino con el carácter ROUTE_CHAR.
     * La ruta es una lista de coordenadas, cada una representada por [fila, columna].
     */
    public void drawRoute(List<int[]> route, char[][] gridCopy) {
        for (int[] coord : route) {
            int r = coord[0];
            int c = coord[1];
            // Si la celda es transitable (espacio en blanco) se marca
            if (gridCopy[r][c] == ' ') {
                gridCopy[r][c] = ROUTE_CHAR;
            }
        }
    }

    /**
     * Imprime la grilla pasada (por ejemplo, la copiada con la ruta)
     */
    public void printGrid(char[][] gridToPrint) {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                System.out.printf("|%2c", gridToPrint[i][j]);
            }
            System.out.println("|");
        }
    }
}