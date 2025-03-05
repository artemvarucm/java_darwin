package es.ucm.mutation;

import es.ucm.individuos.IndividuoRuta;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class SwapMutation {

    /**
     * Realiza una mutación por intercambio en el cromosoma.
     */
    public static void mutate(IndividuoRuta ind) {
        List<Integer> cromosoma = ind.getCromosoma();
        Random rand = new Random();
        int pos1 = rand.nextInt(cromosoma.size());
        int pos2 = rand.nextInt(cromosoma.size());
        Collections.swap(cromosoma, pos1, pos2);
    }
}