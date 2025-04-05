package es.ucm.individuos.arbol;

import org.junit.Test;
import static org.junit.Assert.*;
import es.ucm.mapa.SantaFeMap;

import java.util.List;

public class AbstractNodeTest {
    
    @Test
    public void testForwardNode() {
        ForwardNode forward = new ForwardNode();
        Coord start = new Coord(0, 0);
        DirectionEnum dir = DirectionEnum.EAST;
        
        // Test avanzar hacia el este
        checkCorrectPath(forward, start, dir, new Coord(0, 1), dir);
        
        // Test avanzar hacia el sur
        dir = DirectionEnum.SOUTH;
        checkCorrectPath(forward, start, dir, new Coord(1, 0), dir);
    }

    @Test
    public void testLeftNode() {
        LeftNode left = new LeftNode();
        Coord start = new Coord(0, 0);
        
        // Test giro desde este
        checkCorrectPath(left, start, DirectionEnum.EAST, start, DirectionEnum.NORTH);
        
        // Test giro desde norte
        checkCorrectPath(left, start, DirectionEnum.NORTH, start, DirectionEnum.WEST);
    }

    @Test
    public void testRightNode() {
        RightNode right = new RightNode();
        Coord start = new Coord(0, 0);
        
        // Test giro desde este
        checkCorrectPath(right, start, DirectionEnum.EAST, start, DirectionEnum.SOUTH);
        
        // Test giro desde sur
        checkCorrectPath(right, start, DirectionEnum.SOUTH, start, DirectionEnum.WEST);
    }

    @Test
    public void testProg2Node() {
        // PROG2(AVANZA, IZQUIERDA)
        Prog2Node prog2 = new Prog2Node(new ForwardNode(), new LeftNode());
        Coord start = new Coord(0, 0);
        
        checkCorrectPath(prog2, start, DirectionEnum.EAST, new Coord(0, 1), DirectionEnum.NORTH);
    }

    @Test
    public void testProg3Node() {
        // PROG3(AVANZA, DERECHA, AVANZA)
        Prog3Node prog3 = new Prog3Node(
            new ForwardNode(), 
            new RightNode(), 
            new ForwardNode()
        );
        Coord start = new Coord(0, 0);
        
        checkCorrectPath(prog3, start, DirectionEnum.EAST, new Coord(1, 1), DirectionEnum.SOUTH);
    }

    @Test
    public void testIfFoodNode() {
        // SICOMIDA(AVANZA, IZQUIERDA)
        IfFoodNode ifFood = new IfFoodNode(new ForwardNode(), new LeftNode());
        Coord start = new Coord(0, 0);
        
        // Caso con comida (debería avanzar)
        // Necesitarías mockear el mapa para este test
        // checkCorrectPathWithFood(ifFood, start, DirectionEnum.EAST, new Coord(0, 1), DirectionEnum.EAST, true);
        
        // Caso sin comida (debería girar)
        // checkCorrectPathWithFood(ifFood, start, DirectionEnum.EAST, start, DirectionEnum.NORTH, false);
    }

    protected void checkCorrectPath(AbstractNode node, Coord startCoord, DirectionEnum startDir, 
                                  Coord endCoord, DirectionEnum endDir) {
        // Crear hormiga con mapa mock (simplificado para pruebas)
        SantaFeMap map = new SantaFeMap();
        Hormiga h = new Hormiga(map);
        h.setPosition(startCoord);
        h.setDir(startDir);
        
        List<Coord> path = node.walkAndReturnCoords(h);
        
        System.out.println("RUTA para " + node.getNodeName() + ":");
        for (Coord c : path) {
            System.out.println(c.getRow() + ", " + c.getCol());
        }
        
        assertEquals("Posición final X incorrecta", 
                   endCoord.getRow(), h.getPosition().getRow());
        assertEquals("Posición final Y incorrecta", 
                   endCoord.getCol(), h.getPosition().getCol());
        assertEquals("Dirección final incorrecta", 
                   endDir, h.getDir());
    }

    // Método adicional para probar condicionales con comida
    protected void checkCorrectPathWithFood(AbstractNode node, Coord startCoord, DirectionEnum startDir,
                                          Coord endCoord, DirectionEnum endDir, boolean hasFood) {
        SantaFeMap map = new SantaFeMap();
        if (hasFood) {
            // Añadir comida en la posición delante de la hormiga
            Coord next = new Coord(
                startDir == DirectionEnum.NORTH ? startCoord.getRow() - 1 :
                startDir == DirectionEnum.SOUTH ? startCoord.getRow() + 1 : startCoord.getRow(),
                startDir == DirectionEnum.EAST ? startCoord.getCol() + 1 :
                startDir == DirectionEnum.WEST ? startCoord.getCol() - 1 : startCoord.getCol()
            );
            map.addFood(next);
        }
        
        Hormiga h = new Hormiga(map);
        h.setPosition(startCoord);
        h.setDir(startDir);
        
        List<Coord> path = node.walkAndReturnCoords(h);
        
        assertEquals("Posición final X incorrecta", 
                   endCoord.getRow(), h.getPosition().getRow());
        assertEquals("Posición final Y incorrecta", 
                   endCoord.getCol(), h.getPosition().getCol());
        assertEquals("Dirección final incorrecta", 
                   endDir, h.getDir());
    }
}