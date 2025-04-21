package es.ucm.individuos.tree;


import java.util.LinkedList;
import java.util.List;

public class ForwardNode extends AbstractNode {
    @Override
    public List<Coord> walkAndReturnCoords(Hormiga hormiga) {
        if (hormiga.shouldStop()) {
            return new LinkedList<>();
        }

        // Avanzamos (respetamos limites del mapa)
        Coord newCoord = hormiga.getFrontPosition();
        hormiga.setPosition(newCoord);

        // Añadimos la nueva coordenada a la lista
        hormiga.step();
        return List.of(new Coord(newCoord.getRow(), newCoord.getCol()));
    }

    public AbstractNode clone() {
        AbstractNode clon = new ForwardNode();
        this.copyChildrenToClone(clon);
        return clon;
    }

    @Override
    public String getNodeName() {
        return "FORWARD";
    }
}