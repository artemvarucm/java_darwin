package es.ucm.individuos;

import es.ucm.mansion.AbstractMansionMap;
import es.ucm.mansion.objects.Room;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import static java.util.Objects.isNull;

public class IndividuoAspiradora extends Individuo {
    protected AbstractMansionMap map;
    protected Double fitnessCache;
    protected String genotipoStrCache;

    public IndividuoAspiradora(AbstractMansionMap map) {
        super(null, false);
        this.map = map;
        this.fillRandomPermutation();
    }
    
    public void fillRandomPermutation() {
        List<Integer> source = new ArrayList<>();
        List<Room> rooms = map.getRooms();
        for (Room r : rooms) {
            source.add(r.getId());
        }

        for (int i = 0; i < rooms.size(); i++) {
            int roomInd = ThreadLocalRandom.current().nextInt(0, source.size());
            this.addIntegerGen(source.get(roomInd));
            source.remove(roomInd);
        }
    }

    @Override
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
    public String getSolutionString() {
        StringBuilder result = new StringBuilder();
        result.append("B");
        for (Number var: this.getFenotipos()) {
            result.append(String.format("->%s", var));
        }
        result.append("->B");

        return result.toString();
    }

    @Override
    public Individuo copy() {
        Individuo clon = new IndividuoAspiradora(this.map);
        this.copyToClone(clon);
        return clon;
    }
}