package es.ucm.mansion.objects;

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
        return "■";
    }
}