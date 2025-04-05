package es.ucm.individuos.arbol;

import es.ucm.individuos.Individuo;
import es.ucm.individuos.IndividuoHormiga;

import java.util.List;

public class ForwardNode extends AbstractNode {
    @Override
    public List<Coord> walkAndReturnCoords(Hormiga hormiga) {
        Coord coordCurrent = hormiga.getPosition();
        int col = coordCurrent.getCol();
        int row = coordCurrent.getRow();

        if (hormiga.getDir().equals(DirectionEnum.NORTH)) {
            row = row - 1;
        } else if (hormiga.getDir().equals(DirectionEnum.EAST)) {
            col = col + 1;
        } else if (hormiga.getDir().equals(DirectionEnum.SOUTH)) {
            row = row + 1;
        } else {
            col = col - 1;
        }

        hormiga.getPosition().setRow(row);
        hormiga.getPosition().setCol(col);
        return List.of(new Coord(row, col));
    }

    public AbstractNode clone() {
        AbstractNode clon = new ForwardNode();
        this.copyToClone(clon);
        return clon;
    }
    
    @Override
    public int getTreeSize() {
        return 1; // Solo este nodo
    }

    @Override
    public String getNodeName() {
        return "FORWARD";
    }
}
