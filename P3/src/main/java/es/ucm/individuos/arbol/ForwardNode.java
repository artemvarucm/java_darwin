package es.ucm.individuos.arbol;

import java.util.List;

public class ForwardNode extends AbstractNode {
    @Override
    public List<Coord> walkAndReturnCoords(Hormiga hormiga) {
        Coord coordCurrent = hormiga.getCoord();
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

        hormiga.getCoord().setRow(row);
        hormiga.getCoord().setCol(col);
        return List.of(new Coord(row, col));
    }
}
