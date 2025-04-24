package es.ucm.individuos.grammar;

import es.ucm.individuos.tree.AbstractNode;
import es.ucm.individuos.tree.Coord;
import es.ucm.individuos.tree.Hormiga;

import java.util.List;

/**
 * Clase abstracta que representa la acción abstracta usada en las gramáticas
 */
public abstract class AbstractGrammarElem {
    /**
     * Mueve la hormiga (incluyendo cambios de direccion)
     * y devuelve la ruta como coordenadas (no incluye posición incial)
     */
    public abstract List<Coord> walkAndReturnCoords(Hormiga hormiga);

    /**
     * Como identificar en la gramatica
     */
    public abstract String getFuncName();

    public abstract AbstractGrammarElem clone();

    public String toString() {
        return toStringStartWith("");
    }

    protected String toStringStartWith(String start) {
        return start + getFuncName() + "\n";
    }
}
