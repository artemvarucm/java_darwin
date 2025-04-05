package es.ucm;

import es.ucm.individuos.arbol.Coord;
import es.ucm.individuos.arbol.DirectionEnum;
import es.ucm.mapa.AbstractFoodMap;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class FoodMapPanel extends JPanel {
    private AbstractFoodMap mapa;
    private List<Coord> pathHistory;
    private Coord currentPosition;
    private DirectionEnum currentDirection;
    private int foodCollected;
    private int totalFood;
    private int stepsTaken;
    private List<Coord> bestSolutionPath;

    // Colores mejorados
    private static final Color FOOD_COLOR = new Color(46, 125, 50); // Verde más oscuro
    private static final Color TRAIL_COLOR = new Color(255, 152, 0, 180); // Naranja más vivo
    private static final Color CURRENT_POS_COLOR = new Color(198, 40, 40); // Rojo más intenso
    private static final Color ANT_COLOR = new Color(33, 33, 33); // Negro puro
    private static final Color BEST_SOLUTION_COLOR = new Color(63, 81, 181, 150); // Azul para mejor solución
    private static final Color GRID_COLOR = new Color(189, 189, 189); // Gris más claro
    private static final Color BACKGROUND_COLOR = new Color(250, 250, 250); // Fondo blanco suave

    public FoodMapPanel(AbstractFoodMap mapa) {
        this.mapa = mapa;
        this.totalFood = mapa.getCurrentFoodCount();
        setBackground(BACKGROUND_COLOR);
    }

    public void setMansion(AbstractFoodMap map) {
        this.mapa = map;
        this.totalFood = map.getCurrentFoodCount();
        repaint();
    }

    public void updateAntData(Coord position, DirectionEnum direction, 
                            int foodCollected, int stepsTaken, List<Coord> path) {
        this.currentPosition = position;
        this.currentDirection = direction;
        this.foodCollected = foodCollected;
        this.stepsTaken = stepsTaken;
        this.pathHistory = path;
        repaint();
    }

    public void setBestSolution(List<Coord> bestPath) {
        this.bestSolutionPath = bestPath;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        // Configuración de calidad de renderizado
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        int cellSize = calculateCellSize();
        int offsetX = (getWidth() - mapa.getNCols() * cellSize) / 2;
        int offsetY = (getHeight() - mapa.getNRows() * cellSize) / 2;

        drawGrid(g2d, offsetX, offsetY, cellSize);
        drawFood(g2d, offsetX, offsetY, cellSize);
        
        // Dibujar mejor solución si existe
        if (bestSolutionPath != null && !bestSolutionPath.isEmpty()) {
            drawPath(g2d, bestSolutionPath, BEST_SOLUTION_COLOR, offsetX, offsetY, cellSize, 3);
        }
        
        // Dibujar ruta actual
        if (pathHistory != null && !pathHistory.isEmpty()) {
            drawPath(g2d, pathHistory, TRAIL_COLOR, offsetX, offsetY, cellSize, 5);
        }
        
        // Dibujar hormiga
        if (currentPosition != null) {
            drawAnt(g2d, currentPosition, currentDirection, offsetX, offsetY, cellSize);
        }
        
        drawStatusPanel(g2d, offsetX, offsetY + mapa.getNRows() * cellSize + 10, cellSize);
    }

    private int calculateCellSize() {
        int maxCellWidth = getWidth() / mapa.getNCols();
        int maxCellHeight = getHeight() / mapa.getNRows();
        return Math.min(Math.min(maxCellWidth, maxCellHeight), 30); // Máximo 30px por celda
    }

    private void drawGrid(Graphics2D g2d, int offsetX, int offsetY, int cellSize) {
        g2d.setColor(GRID_COLOR);
        
        // Dibujar líneas verticales
        for (int x = 0; x <= mapa.getNCols(); x++) {
            g2d.drawLine(offsetX + x * cellSize, offsetY, 
                         offsetX + x * cellSize, offsetY + mapa.getNRows() * cellSize);
        }
        
        // Dibujar líneas horizontales
        for (int y = 0; y <= mapa.getNRows(); y++) {
            g2d.drawLine(offsetX, offsetY + y * cellSize, 
                         offsetX + mapa.getNCols() * cellSize, offsetY + y * cellSize);
        }
    }

    private void drawFood(Graphics2D g2d, int offsetX, int offsetY, int cellSize) {
        g2d.setColor(FOOD_COLOR);
        for (Coord foodCoord : mapa.getFoodCoords()) {
            int x = offsetX + foodCoord.getCol() * cellSize;
            int y = offsetY + foodCoord.getRow() * cellSize;
            
            // Dibujar comida como un círculo relleno con borde
            g2d.fillOval(x + 2, y + 2, cellSize - 4, cellSize - 4);
            g2d.setColor(new Color(0, 0, 0, 50));
            g2d.drawOval(x + 2, y + 2, cellSize - 4, cellSize - 4);
            g2d.setColor(FOOD_COLOR);
        }
    }

    private void drawPath(Graphics2D g2d, List<Coord> path, Color color, 
                         int offsetX, int offsetY, int cellSize, int dotSize) {
        g2d.setColor(color);
        for (Coord coord : path) {
            int x = offsetX + coord.getCol() * cellSize + (cellSize - dotSize)/2;
            int y = offsetY + coord.getRow() * cellSize + (cellSize - dotSize)/2;
            g2d.fillOval(x, y, dotSize, dotSize);
        }
    }

    private void drawAnt(Graphics2D g2d, Coord position, DirectionEnum direction, 
                        int offsetX, int offsetY, int cellSize) {
        int centerX = offsetX + position.getCol() * cellSize + cellSize/2;
        int centerY = offsetY + position.getRow() * cellSize + cellSize/2;
        int antSize = cellSize/2;
        
        // Dibujar posición actual
        g2d.setColor(CURRENT_POS_COLOR);
        g2d.fillOval(centerX - antSize/2, centerY - antSize/2, antSize, antSize);
        
        // Dibujar dirección
        g2d.setColor(ANT_COLOR);
        int[] xPoints = new int[3];
        int[] yPoints = new int[3];
        int arrowSize = cellSize/3;
        
        switch (direction) {
            case NORTH:
                xPoints = new int[]{centerX, centerX - arrowSize/2, centerX + arrowSize/2};
                yPoints = new int[]{centerY - antSize/2, centerY + arrowSize/2, centerY + arrowSize/2};
                break;
            case EAST:
                xPoints = new int[]{centerX + antSize/2, centerX - arrowSize/2, centerX - arrowSize/2};
                yPoints = new int[]{centerY, centerY - arrowSize/2, centerY + arrowSize/2};
                break;
            case SOUTH:
                xPoints = new int[]{centerX, centerX - arrowSize/2, centerX + arrowSize/2};
                yPoints = new int[]{centerY + antSize/2, centerY - arrowSize/2, centerY - arrowSize/2};
                break;
            case WEST:
                xPoints = new int[]{centerX - antSize/2, centerX + arrowSize/2, centerX + arrowSize/2};
                yPoints = new int[]{centerY, centerY - arrowSize/2, centerY + arrowSize/2};
                break;
        }
        
        g2d.fillPolygon(xPoints, yPoints, 3);
    }

    private void drawStatusPanel(Graphics2D g2d, int x, int y, int cellSize) {
        // Fondo del panel
        g2d.setColor(new Color(255, 255, 255, 200));
        g2d.fillRoundRect(x, y, cellSize * mapa.getNCols(), 80, 10, 10);
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.drawRoundRect(x, y, cellSize * mapa.getNCols(), 80, 10, 10);
        
        // Texto de estado
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        String status = String.format("Comida recolectada: %d/%d | Pasos: %d/400 | Dirección: %s",
                                    foodCollected, totalFood, stepsTaken, currentDirection);
        g2d.drawString(status, x + 10, y + 20);      
        
    } 
}