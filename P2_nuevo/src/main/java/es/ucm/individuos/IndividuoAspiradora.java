package es.ucm.individuos;


import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Problema de la aspiradora
 */
public class IndividuoAspiradora extends Individuo {
    protected Integer NUM_ROOMS = 20;
    public IndividuoAspiradora() {
        // fixme como parametro deberia de tener el mapa (para calcular el fitness)
        super(null, false);
        this.fillRandomPermutation();
    }

    /**
     * Rellena con una permutación aleatoria
     */
    public void fillRandomPermutation() {
        List<Integer> source = new LinkedList<>();
        for (int i = 0; i < NUM_ROOMS; i++) {
            source.add(i);
        }

        for (int i = 0; i < NUM_ROOMS; i++) {
            int roomInd = source.size() == 1 ? 0 : ThreadLocalRandom.current().nextInt(0, source.size());
            this.addIntegerGen(source.get(roomInd));
            source.remove(roomInd);
        }
    }

    public double getFitness() {
        List<Number> x = this.getFenotipos();

        return 0;
    }

    @Override
    public Individuo copy() {
        Individuo clon = new IndividuoAspiradora();
        this.copyToClone(clon);
        return clon;
    }
}
