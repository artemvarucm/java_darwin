package es.ucm.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RandomUtil {
    /**
     * Devuelve una lista de enteros aleatorios en el rango seleccionado.
     * !Todos los numeros son diferentes!
     */
    public static List<Integer> getNDifferentRandInt(int origin, int bound, int size) {
        List<Integer> result = new ArrayList<>();

        while (result.size() < size) {
            int randomValue = ThreadLocalRandom.current().nextInt(origin, bound);
            if (!result.contains(randomValue)) {
                result.add(randomValue);
            }
        }

        return result;
    }
}
