package es.ucm.mapa;

import es.ucm.individuos.arbol.Coord;
import java.util.ArrayList;
import java.util.List;

public class SantaFeMap extends AbstractFoodMap {
    private static final int N_ROWS = 32;
    private static final int N_COLS = 32;
    public SantaFeMap() {
        super(N_ROWS, N_COLS);
        initializeSantaFeTrail();
    }
    
    private void initializeSantaFeTrail() {
        // Definir las coordenadas del rastro de Santa Fe
        int[][] trailCoords = {
            {0,0}, {0,1}, {0,2}, {0,3}, {1,3}, {2,3}, {3,3}, {4,3}, {5,3}, {5,4}, {5,5}, {5,6},
            {5,8}, {5,9}, {5,10}, {5,11}, {5,12}, {6,12}, {7,12}, {8, 12}, {9,12}, {10,12}, {12,12},
            {13,12}, {14,12}, {15,12}, {18,12}, {19,12}, {20,12}, {21,12}, {22,12}, {23,12}, {24,11},
            {24,10}, {24,9}, {24,8}, {24,7}, {24,4}, {24,3}, {25,1}, {26,1}, {27,1}, {28,1}, {30,2}, {30,3},
            {30,4}, {30,5}, {29,7}, {28,7}, {27,8}, {27,9}, {27,10}, {27,11}, {27,12}, {27,13}, {27,14},
            {26,16}, {25,16}, {24,16}, {21,16}, {19,16}, {18,16}, {17,16}, {16,17}, {15,20}, {14,20}, {11,20},
            {10,20}, {9,20}, {8,20}, {5,21}, {5,22}, {4,24}, {3,24}, {2,25}, {2,26}, {2,27}, {3,29}, {4,29},
            {6,29}, {9,29}, {12,29}, {14,28}, {14,27}, {14,26}, {15,23}, {18,24}, {19,27}, {22,26}, {23,23}

        };

        
        for(int[] coord : trailCoords) {
            Coord c = new Coord(coord[0], coord[1]);
            this.addFood(c);
        }
    }
}