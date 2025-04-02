package es.ucm.individuos.arbol;

/**
 * Clase que representa la hormiga en un instante
 * (sirve para guardar su posición y la dirección en la que mira)
 */
public class Hormiga {
    protected Coord coord;
    protected DirectionEnum dir;

    public Hormiga(Coord coord, DirectionEnum dir) {
        this.coord = coord;
        this.dir = dir;
    }

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public DirectionEnum getDir() {
        return dir;
    }

    public void setDir(DirectionEnum dir) {
        this.dir = dir;
    }
}
