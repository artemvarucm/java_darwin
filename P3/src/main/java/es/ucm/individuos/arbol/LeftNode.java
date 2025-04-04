package es.ucm.individuos.arbol;

import java.util.LinkedList;
import java.util.List;

/**
 * Gira a la izquierda 90 grados
 */
public class LeftNode extends AbstractNode {
    @Override
    public List<Coord> walkAndReturnCoords(Hormiga hormiga) {

        if (hormiga.getDir().equals(DirectionEnum.NORTH)) {
            hormiga.setDir(DirectionEnum.WEST);
        } else if (hormiga.getDir().equals(DirectionEnum.EAST)) {
            hormiga.setDir(DirectionEnum.NORTH);
        } else if (hormiga.getDir().equals(DirectionEnum.SOUTH)) {
            hormiga.setDir(DirectionEnum.EAST);
        } else {
            hormiga.setDir(DirectionEnum.SOUTH);
        }

        return new LinkedList<>(); // no se ha movido
    }
}
