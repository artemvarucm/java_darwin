package es.ucm.individuos.grammar;

import es.ucm.individuos.tree.Coord;
import es.ucm.individuos.tree.DirectionEnum;
import es.ucm.individuos.tree.Hormiga;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Gira a la derecha 90 grados
 */
public class IfFoodGrammarElem extends AbstractGrammarElem {
    List<AbstractGrammarElem> actionsIfFood;
    List<AbstractGrammarElem> actionsNoFood;

    public IfFoodGrammarElem() {
        actionsIfFood = new ArrayList<>();
        actionsNoFood = new ArrayList<>();
    }

    public void setActionsIfFood(List<AbstractGrammarElem> actionsIfFood) {
        this.actionsIfFood = actionsIfFood;
    }

    public void setActionsNoFood(List<AbstractGrammarElem> actionsNoFood) {
        this.actionsNoFood = actionsNoFood;
    }

    @Override
    public List<Coord> walkAndReturnCoords(Hormiga hormiga) {
        if (hormiga.shouldStop()) {
            return new LinkedList<>();
        }

        List<Coord> path = new LinkedList<>();
        if (hormiga.hasFoodInFront()) {
            for (AbstractGrammarElem act: actionsIfFood)
                path.addAll(act.walkAndReturnCoords(hormiga));
        } else {
            for (AbstractGrammarElem act: actionsNoFood)
                path.addAll(act.walkAndReturnCoords(hormiga));
        }

        return path;
    }

    public AbstractGrammarElem clone() {
        IfFoodGrammarElem clone = new IfFoodGrammarElem();
        clone.actionsIfFood = actionsIfFood;
        clone.actionsNoFood = actionsNoFood;

        return clone;
    }

    @Override
    public String getFuncName() {
        return "<if_food>";
    }

    @Override
    public String toStringStartWith(String start) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(start + getFuncName() + "\n");

        for (AbstractGrammarElem act: actionsIfFood)
            stringBuilder.append(act.toStringStartWith(start + "\t"));

        stringBuilder.append(start + "else" + "\n");
        for (AbstractGrammarElem act: actionsNoFood)
            stringBuilder.append(act.toStringStartWith(start + "\t"));

        return stringBuilder.toString();
    }
}