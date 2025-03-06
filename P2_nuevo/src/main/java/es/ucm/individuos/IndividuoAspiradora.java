package es.ucm.individuos;


import es.ucm.mansion.AbstractMansionMap;
import es.ucm.mansion.objects.Room;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Problema de la aspiradora
 */
public class IndividuoAspiradora extends Individuo {
    protected AbstractMansionMap map;
    public IndividuoAspiradora(AbstractMansionMap map) {
        super(null, false);
        this.map = map;
        this.fillRandomPermutation();
    }

    /**
     * Rellena con una permutación aleatoria
     */
    public void fillRandomPermutation() {
        List<Integer> source = new LinkedList<>();
        List<Room> rooms = map.getRooms();
        for (Room r: rooms) {
            source.add(r.getId());
        }

        for (int i = 0; i < rooms.size(); i++) {
            int roomInd = source.size() == 1 ? 0 : ThreadLocalRandom.current().nextInt(0, source.size());
            this.addIntegerGen(source.get(roomInd));
            source.remove(roomInd);
        }
    }

    public double getFitness() {
        List<Number> roomOrder = this.getFenotipos();
        return map.calculateFitness(roomOrder);
    }

    @Override
    public Individuo copy() {
        Individuo clon = new IndividuoAspiradora(this.map);
        this.copyToClone(clon);
        return clon;
    }
}
