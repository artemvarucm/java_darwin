package es.ucm;

import es.ucm.individuos.arbol.Coord;
import es.ucm.mapa.AbstractFoodMap;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import static java.util.Objects.isNull;

public class FoodMapPanel extends JPanel {
    private AbstractFoodMap mapa;
    private List<Coord> routeCells;

    public FoodMapPanel(AbstractFoodMap mapa) {
        this.mapa = mapa;
    }
    
    // Permite actualizar la ruta para redibujar el panel
    public void setRouteCells(List<Coord> routeCells) {
        this.routeCells = routeCells;
        repaint();
    }

    public void setMansion(AbstractFoodMap mapa) {
        this.mapa = mapa;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Habilitar anti-aliasing para mejores gráficos
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                             RenderingHints.VALUE_ANTIALIAS_ON);

        int nRows = mapa.getNRows();
        int nCols = mapa.getNCols();
        int cellWidth = getWidth() / nCols;
        int cellHeight = getHeight() / nRows;

        // pintamos el fondo de blanco
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        // pintamos la ruta
        if (!isNull(routeCells)) {
            g2d.setColor(Color.YELLOW);
            for (Coord coord: routeCells) {
                // X son las columnas, Y son las filas
                g2d.fillRect(coord.getCol() * cellWidth, coord.getRow() * cellHeight, cellWidth, cellHeight);
            }
        }

        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nCols; j++) {
                int x = j * cellWidth; // X son las columnas, Y son las filas
                int y = i * cellHeight;

                // Dibujar el borde de la celda
                g2d.setColor(Color.BLACK);
                g2d.drawRect(x, y, cellWidth, cellHeight);
            }
        }
    }
}