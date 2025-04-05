package es.ucm.individuos.arbol;

import es.ucm.mapa.AbstractFoodMap;

/**
 * Clase que representa la hormiga en un instante
 * (sirve para guardar su posición y la dirección en la que mira)
 */

public class Hormiga {
    private Coord position;
    private DirectionEnum direction;
    private AbstractFoodMap map;
    
    public Hormiga(AbstractFoodMap map) {
        this.map = map;
        this.position = new Coord(0, 0);
        this.direction = DirectionEnum.EAST;
    }
    
    public Coord getPosition() { return position; }
    public void setPosition(Coord pos) { this.position = pos; }
    
    public DirectionEnum getDir() { return direction; }
    public void setDir(DirectionEnum dir) { this.direction = dir; }
    
    public AbstractFoodMap getMap() { return map; }
    
    public Coord getNextPosition() {
        int row = position.getRow();
        int col = position.getCol();
        
        switch(direction) {
            case NORTH: row = (row - 1 + map.getNRows()) % map.getNRows(); break;
            case SOUTH: row = (row + 1) % map.getNRows(); break;
            case EAST: col = (col + 1) % map.getNCols(); break;
            case WEST: col = (col - 1 + map.getNCols()) % map.getNCols(); break;
        }
        
        return new Coord(row, col);
    }
}
