package es.ucm.main;

import es.ucm.factories.IndividuoRutaFactory;
import es.ucm.individuos.Individuo;
import es.ucm.individuos.IndividuoRuta;
import es.ucm.mansion.MansionMap;
import es.ucm.mansion.PathFinder;
import es.ucm.mansion.Room;
import es.ucm.cross.PMXCrossover;
import es.ucm.mutation.SwapMutation;
import es.ucm.selection.RouletteSelection;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    // Parámetros evolutivos
    private static final int POP_SIZE = 50;
    private static final int MAX_GENERATIONS = 400;
    private static final double CROSSOVER_PROB = 0.8;
    private static final double MUTATION_PROB = 0.2;

    public static void main(String[] args) {
        // 1. Mostrar mapa inicial con habitaciones y obstáculos
        MansionMap mansion = new MansionMap();
        System.out.println("Mapa de la mansión:");
        mansion.printMap();

        // 2. Evolución: inicialización de la población
        IndividuoRutaFactory factory = new IndividuoRutaFactory();
        List<Individuo> poblacion = factory.createMany(POP_SIZE);
        // Usaremos la Ruleta (RouletteSelection) implementada en tu proyecto para selección
        RouletteSelection selector = new RouletteSelection(factory);
        Random rand = new Random();

        IndividuoRuta bestIndividuo = null;
        double bestFitness = Double.MAX_VALUE;

        // Para guardar evolución del fitness (mínimo de cada generación)
        List<Double> bestFitnessEvo = new ArrayList<>();

        // Bucle evolutivo
        for (int gen = 0; gen < MAX_GENERATIONS; gen++) {
            // Evaluación: buscar el mejor individuo en la población
            for (Individuo ind : poblacion) {
                double fitness = ind.getFitness();
                if (fitness < bestFitness) {
                    bestFitness = fitness;
                    bestIndividuo = (IndividuoRuta) ind.copy();
                }
            }
            bestFitnessEvo.add(bestFitness);
            System.out.println("Generación " + gen + " - Mejor fitness: " + bestFitness);

            // Selección (se seleccionan tantos padres como tamaño de población)
            List<Individuo> padres = selector.select(poblacion);
            List<Individuo> nuevaPoblacion = new ArrayList<>();

         // Crossover por parejas
            for (int i = 0; i < padres.size(); i += 2) {
                IndividuoRuta parent1 = (IndividuoRuta) padres.get(i);
                IndividuoRuta parent2;
                if (i + 1 < padres.size())
                    parent2 = (IndividuoRuta) padres.get(i + 1);
                else
                    parent2 = (IndividuoRuta) padres.get(0);

                if (rand.nextDouble() < CROSSOVER_PROB) {
                    // Generar dos hijos realizando el crossover en ambas direcciones
                    IndividuoRuta hijo1 = PMXCrossover.crossover(parent1, parent2);
                    IndividuoRuta hijo2 = PMXCrossover.crossover(parent2, parent1);

                    nuevaPoblacion.add(hijo1);
                    nuevaPoblacion.add(hijo2);
                } else {
                    nuevaPoblacion.add(parent1.copy());
                    nuevaPoblacion.add(parent2.copy());
                }
            }

            // Mutación
            for (Individuo ind : nuevaPoblacion) {
                if (rand.nextDouble() < MUTATION_PROB) {
                    SwapMutation.mutate((IndividuoRuta) ind);
                }
            }

            // Actualizar la población
            poblacion = nuevaPoblacion;
        }

        // Mostrar mejor solución encontrada
        System.out.println("\nMejor ruta encontrada:");
        System.out.println("Fitness = " + bestFitness);
        System.out.print("Ruta: ");
        bestIndividuo.printCromosoma();

        // Calcular la ruta completa utilizando A* en cada segmento y dibujar sobre el mapa
        List<int[]> rutaCompleta = calcularRutaCompleta(bestIndividuo);
        char[][] gridConRuta = mansion.getGridCopy();
        mansion.drawRoute(rutaCompleta, gridConRuta);
        System.out.println("\nRecorrido óptimo en el mapa:");
        mansion.printGrid(gridConRuta);

        // (Opcional) Imprimir la evolución del fitness a lo largo de generaciones
        System.out.println("\nEvolución del mejor fitness de cada generación:");
        for (int i = 0; i < bestFitnessEvo.size(); i++) {
            System.out.println("Gen " + i + ": " + bestFitnessEvo.get(i));
        }
    }
    
    /**
     * Dado un individuo solución, calcula la ruta completa (lista de coordenadas)
     * desde la base, pasando por cada habitación en el orden del cromosoma y regresando a la base.
     */
    private static List<int[]> calcularRutaCompleta(IndividuoRuta ind) {
        MansionMap mansion = new MansionMap();
        PathFinder pf = new PathFinder(mansion);
        List<int[]> rutaCompleta = new ArrayList<>();
        int baseRow = mansion.getBaseRow();
        int baseCol = mansion.getBaseCol();
        // Agregamos la posición de la base
        List<int[]> segmento = pf.findPath(baseRow, baseCol, getRoom(ind, 0).getRow(), getRoom(ind, 0).getCol());
        if(segmento != null) rutaCompleta.addAll(segmento);

        // Segmentos entre habitaciones
        for (int i = 0; i < ind.getCromosoma().size() - 1; i++) {
            Room r1 = getRoom(ind, i);
            Room r2 = getRoom(ind, i + 1);
            segmento = pf.findPath(r1.getRow(), r1.getCol(), r2.getRow(), r2.getCol());
            if(segmento != null) {
                // Para evitar repetir el último nodo anterior
                segmento.remove(0);
                rutaCompleta.addAll(segmento);
            }
        }
        // Último segmento: de la última habitación a la base
        Room ultima = getRoom(ind, ind.getCromosoma().size() - 1);
        segmento = pf.findPath(ultima.getRow(), ultima.getCol(), baseRow, baseCol);
        if(segmento != null) {
            segmento.remove(0);
            rutaCompleta.addAll(segmento);
        }
        return rutaCompleta;
    }

    // Ayuda: dado un individuo y una posición del cromosoma, retorna el Room correspondiente.
    private static Room getRoom(IndividuoRuta ind, int pos) {
        int id = ind.getCromosoma().get(pos);
        // Como en MansionMap, las habitaciones se agregan en orden de id (1 a 20)
        return new MansionMap().getRooms().get(id - 1);
    }
}