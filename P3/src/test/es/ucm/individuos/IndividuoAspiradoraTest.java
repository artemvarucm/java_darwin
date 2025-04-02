package es.ucm.individuos;

import es.ucm.genes.IntegerGen;
import es.ucm.mansion.MansionMap;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;


public class IndividuoAspiradoraTest {

    @Test
    public void getFitness() {
        IndividuoAspiradora a = new IndividuoAspiradora(new MansionMap());
        System.out.println(a.getFitness());
    }

    @Test
    public void initTest() {
        for (int i = 0; i < 1000; i++) {
            IndividuoAspiradora a = new IndividuoAspiradora(new MansionMap());
            Set<Integer> rooms = new HashSet<>();
            for (IntegerGen gen: a.getIntGenes()) {
                if (rooms.contains(gen.getFenotipo())) {
                    throw new RuntimeException("Hay habitaciones repetidas en el cromosoma!!!");
                } else {
                    rooms.add(gen.getFenotipo());
                }
            }
        }

    }
}