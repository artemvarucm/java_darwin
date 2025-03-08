package es.ucm.individuos;


import es.ucm.mansion.AbstractMansionMap;
import es.ucm.mansion.objects.Room;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static java.util.Objects.isNull;

/**
 * Problema de la aspiradora
 */
public class IndividuoAspiradora extends Individuo {
    protected AbstractMansionMap map;
    protected Double fitnessCache; // fitness cacheado (para no volver a calcularlo)
    protected String genotipoStrCache; // por si cambia el genotipo, para recalcular el fitness (nunca pasa realmente)
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

    @Override
    public String getSolutionString() {
        StringBuilder result = new StringBuilder();
        result.append("B");
        for (Number var: this.getFenotipos()) {
            result.append(String.format(" -> %s", var));
        }
        result.append(" -> B");

        return result.toString();
    }

    public double getFitness() {
        if (!isNull(fitnessCache) && this.genotipoToString().equals(genotipoStrCache)) {
            return this.fitnessCache;
        }

        List<Number> roomOrder = this.getFenotipos();
        this.fitnessCache = map.calculateFitness(roomOrder);
        this.genotipoStrCache = this.genotipoToString();

        return this.fitnessCache;
    }

    @Override
    public Individuo copy() {
        Individuo clon = new IndividuoAspiradora(this.map);
        this.copyToClone(clon);
        return clon;
    }
}
