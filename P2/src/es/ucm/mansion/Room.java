package es.ucm.mansion;

public class Room {
    private int id;
    private String name;
    private int row;
    private int col;

    public Room(int id, String name, int row, int col) {
        this.id = id;
        this.name = name;
        this.row = row;
        this.col = col;
    }

    public int getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}