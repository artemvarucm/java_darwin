package es.ucm.individuos.grammar;

import es.ucm.individuos.tree.Coord;
import es.ucm.individuos.tree.DirectionEnum;
import es.ucm.individuos.tree.Hormiga;

import java.util.LinkedList;
import java.util.List;

/**
 * Gira a la izquierda 90 grados
 */
public class LeftGrammarElem extends AbstractGrammarElem {
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

    public AbstractGrammarElem clone() {
        return new LeftGrammarElem();
    }

    @Override
    public String getFuncName() {
        return "left";
    }
}