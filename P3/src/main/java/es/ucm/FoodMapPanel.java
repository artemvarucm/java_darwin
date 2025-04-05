package es.ucm;

import es.ucm.individuos.arbol.Coord;
import es.ucm.mapa.AbstractFoodMap;
import es.ucm.mapa.SantaFeMap;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class FoodMapPanel extends JPanel {
    private AbstractFoodMap map;
    private List<Coord> routeCells;
    private List<Coord> foodCells;

    public FoodMapPanel(AbstractFoodMap map) {
        this.map = map;
        updateFoodCells();
    }

    public void setMansion(AbstractFoodMap map) {
        this.map = map;
        updateFoodCells();
        repaint();
    }

    public void setRouteCells(List<Coord> routeCells) {
        this.routeCells = routeCells;
        repaint();
    }

    private void updateFoodCells() {
        this.foodCells = map.getFoodCoords();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int cellWidth = getWidth() / map.getNCols();
        int cellHeight = getHeight() / map.getNRows();

        // Dibujar fondo
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        // Dibujar comida (verde)
        if(foodCells != null) {
            g2d.setColor(new Color(0, 180, 0));
            for(Coord coord : foodCells) {
                g2d.fillRect(coord.getCol() * cellWidth, coord.getRow() * cellHeight, 
                             cellWidth, cellHeight);
            }
        }

        // Dibujar ruta (amarillo transparente)
        if(routeCells != null && !routeCells.isEmpty()) {
            g2d.setColor(new Color(255, 255, 0, 150));
            for(Coord coord : routeCells) {
                g2d.fillRect(coord.getCol() * cellWidth, coord.getRow() * cellHeight, 
                             cellWidth, cellHeight);
            }

            // Dibujar posición final (rojo)
            Coord last = routeCells.get(routeCells.size()-1);
            g2d.setColor(Color.RED);
            g2d.fillRect(last.getCol() * cellWidth, last.getRow() * cellHeight, 
                         cellWidth, cellHeight);
        }

        // Dibujar grid
        g2d.setColor(Color.LIGHT_GRAY);
        for(int i = 0; i <= map.getNRows(); i++) {
            g2d.drawLine(0, i * cellHeight, getWidth(), i * cellHeight);
        }
        for(int j = 0; j <= map.getNCols(); j++) {
            g2d.drawLine(j * cellWidth, 0, j * cellWidth, getHeight());
        }
    }
}