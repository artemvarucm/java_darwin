package es.ucm.mansion.objects;

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
        return String.valueOf(id);
    }
}