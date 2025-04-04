package es.ucm.individuos.arbol;

import org.w3c.dom.Node;

import java.util.LinkedList;
import java.util.List;

/**
 * Gira a la derecha 90 grados
 */
public class RightNode extends AbstractNode {
    @Override
    public List<Coord> walkAndReturnCoords(Hormiga hormiga) {

        if (hormiga.getDir().equals(DirectionEnum.NORTH)) {
            hormiga.setDir(DirectionEnum.EAST);
        } else if (hormiga.getDir().equals(DirectionEnum.EAST)) {
            hormiga.setDir(DirectionEnum.SOUTH);
        } else if (hormiga.getDir().equals(DirectionEnum.SOUTH)) {
            hormiga.setDir(DirectionEnum.WEST);
        } else {
            hormiga.setDir(DirectionEnum.NORTH);
        }

        return new LinkedList<>(); // no se ha movido
    }

    public AbstractNode clone() {
        AbstractNode clon = new RightNode();
        this.copyToClone(clon);
        return clon;
    }

    @Override
    public String getNodeName() {
        return "RIGHT";
    }
}