package es.ucm;

import es.ucm.mansion.MansionMap;
import es.ucm.mansion.objects.AbstractMansionObject;
import es.ucm.mansion.objects.Obstacle;
import es.ucm.mansion.objects.Room;

import javax.swing.*;
import java.awt.*;
import java.util.Set;

public class MansionMapPanel extends JPanel {
    private MansionMap mansion;
    private Set<String> routeCells; // Formato "fila,col", ej: "7,7" para la base

    public MansionMapPanel(MansionMap mansion, Set<String> routeCells) {
        this.mansion = mansion;
        this.routeCells = routeCells;
    }
    
    // Permite actualizar la ruta para redibujar el panel
    public void setRouteCells(Set<String> routeCells) {
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
        
        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nCols; j++) {
                int x = j * cellWidth;
                int y = i * cellHeight;
                AbstractMansionObject obj = mansion.getGrid()[i][j];
                
                // Si es la base
                if(i == 7 && j == 7) {
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
                // Si no hay objeto, comprobamos si es parte de la ruta
                else {
                    if (routeCells != null && routeCells.contains(i + "," + j)) {
                        g2d.setColor(Color.YELLOW);
                        g2d.fillRect(x, y, cellWidth, cellHeight);
                    } else {
                        g2d.setColor(Color.WHITE);
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