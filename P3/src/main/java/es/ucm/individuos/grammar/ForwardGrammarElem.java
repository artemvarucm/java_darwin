package es.ucm.individuos.grammar;


import es.ucm.individuos.tree.Coord;
import es.ucm.individuos.tree.Hormiga;

import java.util.LinkedList;
import java.util.List;

public class ForwardGrammarElem extends AbstractGrammarElem {
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

    public AbstractGrammarElem clone() {
        return new ForwardGrammarElem();
    }

    @Override
    public String getFuncName() {
        return "forward";
    }
}