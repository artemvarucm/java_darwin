package es.ucm;

import es.ucm.mansion.MansionMap;
import es.ucm.mansion.busqueda.NodoCamino;
import es.ucm.mansion.objects.AbstractMansionObject;
import es.ucm.mansion.objects.Obstacle;
import es.ucm.mansion.objects.Room;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MansionMapPanel extends JPanel {
    private MansionMap mansion;
    private List<NodoCamino> routeCells;

    public MansionMapPanel(MansionMap mansion, List<NodoCamino> routeCells) {
        this.mansion = mansion;
        this.routeCells = routeCells;
    }
    
    // Permite actualizar la ruta para redibujar el panel
    public void setRouteCells(List<NodoCamino> routeCells) {
        this.routeCells = routeCells;
        repaint();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Habilitar anti-aliasing para mejores gráficos
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                             RenderingHints.VALUE_ANTIALIAS_ON);

        int nRows = mansion.getNRows();
        int nCols = mansion.getNCols();
        int cellWidth = getWidth() / nCols;
        int cellHeight = getHeight() / nRows;

        // pintamos el fondo de blanco
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        // pintamos la ruta
        if (routeCells != null) {
            g2d.setColor(Color.YELLOW);
            for (NodoCamino nodo: routeCells) {
                // X son las columnas, Y son las filas
                g2d.fillRect(nodo.getCurrentCol() * cellWidth, nodo.getCurrentRow() * cellHeight, cellWidth, cellHeight);
            }
        }

        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nCols; j++) {
                int x = j * cellWidth; // X son las columnas, Y son las filas
                int y = i * cellHeight;
                AbstractMansionObject obj = mansion.getGrid()[i][j];
                
                // Si es la base
                if(i == mansion.getBaseRow() && j == mansion.getBaseCol()) {
                    g2d.setColor(Color.GREEN);
                    g2d.fillRect(x, y, cellWidth, cellHeight);
                    g2d.setColor(Color.BLACK);
                    g2d.drawString("B", x + cellWidth / 2 - 4, y + cellHeight / 2 + 4);
                }
                // Si hay un objeto, diferenciamos habitación y obstáculo
                else if (obj != null) {
                    if (obj instanceof Room) {
                        g2d.setColor(new Color(173, 216, 230)); // Azul claro para habitaciones
                        g2d.fillRect(x, y, cellWidth, cellHeight);
                        g2d.setColor(Color.BLACK);
                        Room room = (Room) obj;
                        g2d.drawString(String.valueOf(room.getId()), x + cellWidth / 2 - 4, y + cellHeight / 2 + 4);
                    } else if (obj instanceof Obstacle) {
                        g2d.setColor(Color.DARK_GRAY);
                        g2d.fillRect(x, y, cellWidth, cellHeight);
                    }
                }
                
                // Dibujar el borde de la celda
                g2d.setColor(Color.BLACK);
                g2d.drawRect(x, y, cellWidth, cellHeight);
            }
        }
    }
}