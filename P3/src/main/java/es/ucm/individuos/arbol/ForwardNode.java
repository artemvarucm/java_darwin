package es.ucm.individuos.arbol;


import java.util.List;

public class ForwardNode extends AbstractNode {
    @Override
    public List<Coord> walkAndReturnCoords(Hormiga hormiga) {
        // Avanzamos (respetamos limites del mapa)
        hormiga.moveForward();
        // Añadimos la nueva coordenada a la lista
        Coord newCoord = hormiga.getPosition();
        return List.of(new Coord(newCoord.getRow(), newCoord.getCol()));
    }

    public AbstractNode clone() {
        AbstractNode clon = new ForwardNode();
        this.copyToClone(clon);
        return clon;
    }

    @Override
    public String getNodeName() {
        return "FORWARD";
    }
}
