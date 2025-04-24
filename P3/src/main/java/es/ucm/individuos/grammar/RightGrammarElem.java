package es.ucm.individuos.grammar;

import es.ucm.individuos.tree.Coord;
import es.ucm.individuos.tree.DirectionEnum;
import es.ucm.individuos.tree.Hormiga;

import java.util.LinkedList;
import java.util.List;

/**
 * Gira a la derecha 90 grados
 */
public class RightGrammarElem extends AbstractGrammarElem {
    @Override
    public List<Coord> walkAndReturnCoords(Hormiga hormiga) {
        if (hormiga.shouldStop()) {
            return new LinkedList<>();
        }

        if (hormiga.getDir().equals(DirectionEnum.NORTH)) {
            hormiga.setDir(DirectionEnum.EAST);
        } else if (hormiga.getDir().equals(DirectionEnum.EAST)) {
            hormiga.setDir(DirectionEnum.SOUTH);
        } else if (hormiga.getDir().equals(DirectionEnum.SOUTH)) {
            hormiga.setDir(DirectionEnum.WEST);
        } else {
            hormiga.setDir(DirectionEnum.NORTH);
        }

        hormiga.step();
        return new LinkedList<>(); // no se ha movido
    }

    public AbstractGrammarElem clone() {
        return new RightGrammarElem();
    }

    @Override
    public String getFuncName() {
        return "right";
    }
}