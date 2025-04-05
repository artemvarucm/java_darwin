package es.ucm.individuos.arbol;

import org.w3c.dom.Node;

import java.util.LinkedList;
import java.util.List;

/**
 * Gira a la izquierda 90 grados
 */
public class LeftNode extends AbstractNode {
    @Override
    public List<Coord> walkAndReturnCoords(Hormiga hormiga) {
        if (hormiga.shouldStop()) {
            return new LinkedList<>();
        }

        if (hormiga.getDir().equals(DirectionEnum.NORTH)) {
            hormiga.setDir(DirectionEnum.WEST);
        } else if (hormiga.getDir().equals(DirectionEnum.EAST)) {
            hormiga.setDir(DirectionEnum.NORTH);
        } else if (hormiga.getDir().equals(DirectionEnum.SOUTH)) {
            hormiga.setDir(DirectionEnum.EAST);
        } else {
            hormiga.setDir(DirectionEnum.SOUTH);
        }

        hormiga.step();

        return new LinkedList<>(); // no se ha movido
    }

    public AbstractNode clone() {
        AbstractNode clon = new LeftNode();
        this.copyToClone(clon);
        return clon;
    }

    @Override
    public String getNodeName() {
        return "LEFT";
    }
}
