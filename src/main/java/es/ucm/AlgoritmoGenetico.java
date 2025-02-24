package es.ucm;

import es.ucm.factories.IndividuoFactory;
import es.ucm.individuos.Individuo;
import es.ucm.selection.AbstractSelection;
import es.ucm.cross.AbstractCross;
import es.ucm.mutation.AbstractMutate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class AlgoritmoGenetico {
    private IndividuoFactory factory;
    private List<Individuo> poblacion;
    private List<Individuo> elite; // guarda la élite para después reemplazar los peores.
    private List<Double> fitness;

    // Parámetros del algoritmo
    private int tamPoblacion;
    private int maxGeneraciones;
    private double probCruce;
    private int tamElite;

    // Métodos de selección, cruce y mutación
    private AbstractSelection selectionMethod;
    private AbstractCross crossoverMethod;
    private AbstractMutate mutationMethod;

    // Historial de fitness
    private double[] bestFitnessHistory; // Mejor fitness por generación
    private double[] averageFitnessHistory; // Fitness medio por generación
    private double[] absoluteBestHistory; // Mejor fitness absoluto
    private double[] presionSelectiva; // Presion selectiva (bestFitnessHistory[i] / averageFitnessHistory[i])

    // Mejor individuo
    private Individuo mejor;
    private double mejorFitness;

    public AlgoritmoGenetico(IndividuoFactory factory, int tamPoblacion) {
        this.factory = factory;
        this.tamPoblacion = tamPoblacion;
        this.poblacion = new ArrayList<>(tamPoblacion);
        this.fitness = new ArrayList<>(tamPoblacion);
        this.elite = new ArrayList<>();
    }

    public void setMaxGeneraciones(int maxGeneraciones) {
        this.maxGeneraciones = maxGeneraciones;
    }

    public void setProbCruce(double probCruce) {
        this.probCruce = probCruce;
    }

    public void setElitismRate(double elitismRate) {
        this.tamElite = (int) (tamPoblacion * elitismRate);
    }

    public void setSelectionMethod(AbstractSelection selectionMethod) {
        this.selectionMethod = selectionMethod;
    }

    public void setCrossoverMethod(AbstractCross crossoverMethod) {
        this.crossoverMethod = crossoverMethod;
    }

    public void setMutationMethod(AbstractMutate mutationMethod) {
        this.mutationMethod = mutationMethod;
    }

    public void optimize() {
        // Inicializar la población
        poblacion = random_sample();
        // Inicializar mejor individuo
        mejor = poblacion.get(0).copy();
        mejorFitness = mejor.getFitness();

        fitness_evaluation();

        // Inicializar historial de fitness
        bestFitnessHistory = new double[maxGeneraciones];
        averageFitnessHistory = new double[maxGeneraciones];
        absoluteBestHistory = new double[maxGeneraciones];
        presionSelectiva = new double[maxGeneraciones];

        // Evolución del algoritmo
        for (int generacion = 0; generacion < maxGeneraciones; generacion++) {
            System.out.printf("GENERACION: %d%n", generacion);
            // guardamos la élite
            this.saveElite();

            // Selección
            this.poblacion = selectionMethod.select(poblacion);

            // Cruce
            List<Individuo> descendientes = new ArrayList<>();
            for (int i = 0; i < poblacion.size() - 1; i += 2) {
                Individuo parent1 = poblacion.get(i);
                Individuo parent2 = poblacion.get(i + 1);
                double randomVal = ThreadLocalRandom.current().nextDouble(); // entre 0 y 1

                if (randomVal < probCruce) {
                    List<Individuo> hijos = crossoverMethod.cross(parent1, parent2);
                    descendientes.addAll(hijos);
                } else {
                    descendientes.add(parent1.copy());
                    descendientes.add(parent2.copy());
                }
            }

            // Si la población es impar, añadir el último individuo sin cruzar
            if (poblacion.size() % 2 != 0) {
                descendientes.add(poblacion.get(poblacion.size() - 1).copy());
            }

            this.poblacion = descendientes;

            // Mutación
            for (Individuo individuo : poblacion) {
                mutationMethod.mutate(individuo);
            }

            // Reemplazamos los peores con la elite
            this.replaceWorstWithElite();

            // Evaluación de fitness
            fitness_evaluation();

            // Registrar datos históricos
            registrarHistorial(generacion);
        }
    }

    private void saveElite() {
        // para no reordenar la poblacion, guardamos una lista ordenada aparte
        List<Individuo> sortedList;
        boolean maximizar = poblacion.get(0).getMaximizar();
        if (maximizar) {
            sortedList = poblacion.stream().sorted(Comparator.comparingDouble(Individuo::getFitness).reversed()).toList();
        } else {
            sortedList = poblacion.stream().sorted(Comparator.comparingDouble(Individuo::getFitness)).toList();
        }

        this.elite.clear();
        for (int i = 0; i < tamElite; i++) {
            this.elite.add(sortedList.get(i).copy());
        }
    }

    private void replaceWorstWithElite() {
        // para no reordenar la poblacion, guardamos una lista ordenada aparte
        boolean maximizar = poblacion.get(0).getMaximizar();
        if (maximizar) { // if inverso al de saveElite
            poblacion.sort(Comparator.comparingDouble(Individuo::getFitness));
        } else {
            poblacion.sort(Comparator.comparingDouble(Individuo::getFitness).reversed());
        }

        for (int i = 0; i < tamElite; i++) {
            poblacion.set(i, this.elite.get(i));
        }
        this.elite.clear();
    }

    private List<Individuo> random_sample() {
        return factory.createMany(tamPoblacion);
    }

    private void fitness_evaluation() {
        fitness.clear();
        for (Individuo individuo : poblacion) {
            double fit = individuo.getFitness();
            fitness.add(fit);

            // Actualizar mejor individuo
            if (individuo.getMaximizar() && fit > mejorFitness) {
                mejor = individuo.copy();
                mejorFitness = fit;
            } else if (!individuo.getMaximizar() && fit < mejorFitness) {
                mejor = individuo.copy();
                mejorFitness = fit;
            }
        }
    }


    private void registrarHistorial(int generacion) {
        // Mejor fitness de la generación
        Boolean maximizar = poblacion.get(0).getMaximizar();
        bestFitnessHistory[generacion] = maximizar ? Collections.max(fitness) : Collections.min(fitness);

        // Fitness medio de la generación
        double sumaFitness = 0.0;
        for (double fit : fitness) {
            sumaFitness += fit;
        }
        averageFitnessHistory[generacion] = sumaFitness / tamPoblacion;

        presionSelectiva[generacion] = bestFitnessHistory[generacion] / averageFitnessHistory[generacion];
        // Mejor fitness absoluto
        absoluteBestHistory[generacion] = mejorFitness;
    }

    public Individuo getMejor() {
        return mejor;
    }

    public double[] getBestFitnessHistory() {
        return bestFitnessHistory;
    }

    public double[] getAverageFitnessHistory() {
        return averageFitnessHistory;
    }

    public double[] getAbsoluteBestHistory() {
        return absoluteBestHistory;
    }

    public double[] getPresionSelectiva() {
        return presionSelectiva;
    }
}