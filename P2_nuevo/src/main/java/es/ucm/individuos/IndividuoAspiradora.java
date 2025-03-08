package es.ucm.individuos;

import es.ucm.mansion.AbstractMansionMap;
import es.ucm.mansion.objects.Room;
import es.ucm.mansion.busqueda.AEstrellaBusquedaCamino;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import static java.util.Objects.isNull;

public class IndividuoAspiradora extends Individuo {
    protected AbstractMansionMap map;
    protected Double fitnessCache;
    protected String genotipoStrCache;
    protected int fitnessFunction; // 1: Original, 2: Penalización por obstáculos, 3: Penalización por giros

    public IndividuoAspiradora(AbstractMansionMap map) {
        super(null, false);
        this.map = map;
        this.fitnessFunction = 1; // Por defecto, usamos la función de fitness original
        this.fillRandomPermutation();
    }

    public void setFitnessFunction(int fitnessFunction) {
        this.fitnessFunction = fitnessFunction;
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
        switch (fitnessFunction) {
            case 1:
                this.fitnessCache = map.calculateFitness(roomOrder);
                break;
            case 2:
                this.fitnessCache = calculateFitnessWithObstaclePenalty(roomOrder);
                break;
            case 3:
                this.fitnessCache = calculateFitnessWithTurnPenalty(roomOrder);
                break;
            default:
                throw new IllegalArgumentException("Función de fitness no válida");
        }
        this.genotipoStrCache = this.genotipoToString();

        return this.fitnessCache;
    }

    private double calculateFitnessWithObstaclePenalty(List<Number> roomOrder) {
        double fitness = map.calculateFitness(roomOrder);
        AEstrellaBusquedaCamino aStar = new AEstrellaBusquedaCamino(map);
        double penalty = 0.0;

        for (int i = 0; i < roomOrder.size() - 1; i++) {
            final int index = i; // Copia final de i para usar en la expresión lambda
            Room room1 = map.getRooms().stream()
                    .filter(r -> r.getId() == roomOrder.get(index).intValue())
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Habitación no encontrada"));
            Room room2 = map.getRooms().stream()
                    .filter(r -> r.getId() == roomOrder.get(index + 1).intValue())
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Habitación no encontrada"));

            double distance = aStar.calculateCostAtoB(room1.getRow(), room1.getCol(), room2.getRow(), room2.getCol());

            // Penalización si el camino pasa cerca de un obstáculo
            if (distance > 1.0) {
                penalty += distance * 0.1; // Ajusta el factor de penalización según sea necesario
            }
        }

        return fitness + penalty;
    }

    private double calculateFitnessWithTurnPenalty(List<Number> roomOrder) {
        double fitness = map.calculateFitness(roomOrder);
        double penalty = 0.0;

        for (int i = 1; i < roomOrder.size() - 1; i++) {
        	final int index = i;
            Room prevRoom = map.getRooms().stream()
                    .filter(r -> r.getId() == roomOrder.get(index - 1).intValue())
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Habitación no encontrada"));
            Room currRoom = map.getRooms().stream()
                    .filter(r -> r.getId() == roomOrder.get(index).intValue())
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Habitación no encontrada"));
            Room nextRoom = map.getRooms().stream()
                    .filter(r -> r.getId() == roomOrder.get(index + 1).intValue())
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Habitación no encontrada"));

            int prevDirection = getDirection(prevRoom, currRoom);
            int nextDirection = getDirection(currRoom, nextRoom);

            if (prevDirection != nextDirection) {
                penalty += 1.0; // Penalización por cambio de dirección
            }
        }

        return fitness + penalty;
    }
    
    private double calculateFitnessMultiCriteria(List<Number> roomOrder) {
        double fitness = map.calculateFitness(roomOrder); // Fitness original (distancia)
        AEstrellaBusquedaCamino aStar = new AEstrellaBusquedaCamino(map);
        double obstaclePenalty = 0.0;
        double turnPenalty = 0.0;
        double deviationPenalty = 0.0;

        for (int i = 0; i < roomOrder.size() - 1; i++) {
            final int index = i;
            Room room1 = map.getRooms().stream()
                    .filter(r -> r.getId() == roomOrder.get(index).intValue())
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Habitación no encontrada"));
            Room room2 = map.getRooms().stream()
                    .filter(r -> r.getId() == roomOrder.get(index + 1).intValue())
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Habitación no encontrada"));

            // Penalización por proximidad a obstáculos
            double distance = aStar.calculateCostAtoB(room1.getRow(), room1.getCol(), room2.getRow(), room2.getCol());
            if (distance > 1.0) {
                obstaclePenalty += distance * 0.1;
            }

            // Penalización por cambios de dirección
            if (i > 0) {
                Room prevRoom = map.getRooms().stream()
                        .filter(r -> r.getId() == roomOrder.get(index - 1).intValue())
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("Habitación no encontrada"));
                int prevDirection = getDirection(prevRoom, room1);
                int currDirection = getDirection(room1, room2);
                if (prevDirection != currDirection) {
                    turnPenalty += 1.0;
                }
            }

            // Penalización por desviación de la distancia euclidiana
            double euclideanDistance = Math.sqrt(Math.pow(room2.getRow() - room1.getRow(), 2) + Math.pow(room2.getCol() - room1.getCol(), 2));
            deviationPenalty += Math.max(0, distance - euclideanDistance) * 0.2;
        }

        return fitness + obstaclePenalty + turnPenalty + deviationPenalty;
    }

    private int getDirection(Room from, Room to) {
        if (from.getRow() == to.getRow()) {
            return (from.getCol() < to.getCol()) ? 1 : 3; // Derecha (1) o Izquierda (3)
        } else {
            return (from.getRow() < to.getRow()) ? 2 : 4; // Abajo (2) o Arriba (4)
        }
    }

    @Override
    public Individuo copy() {
        Individuo clon = new IndividuoAspiradora(this.map);
        this.copyToClone(clon);
        ((IndividuoAspiradora) clon).fitnessFunction = this.fitnessFunction;
        return clon;
    }
}