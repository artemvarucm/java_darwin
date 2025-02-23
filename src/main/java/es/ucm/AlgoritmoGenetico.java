package es.ucm;

import es.ucm.factories.IndividuoFactory;
import es.ucm.individuos.Individuo;
import es.ucm.selection.AbstractSelection;
import es.ucm.cross.AbstractCross;
import es.ucm.mutation.AbstractMutate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AlgoritmoGenetico {
    private IndividuoFactory factory;
    private List<Individuo> poblacion;
    private List<Double> fitness;

    // Parámetros del algoritmo
    private int tamPoblacion;
    private int maxGeneraciones;
    private double probCruce;
    private double probMutacion;
    private double elitismRate;

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
    }

    public void setMaxGeneraciones(int maxGeneraciones) {
        this.maxGeneraciones = maxGeneraciones;
    }

    public void setProbCruce(double probCruce) {
        this.probCruce = probCruce;
    }

    public void setProbMutacion(double probMutacion) {
        this.probMutacion = probMutacion;
    }

    public void setElitismRate(double elitismRate) {
        this.elitismRate = elitismRate;
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

            // Selección
            this.poblacion = selectionMethod.select(poblacion);

            // Cruce
            List<Individuo> descendientes = new ArrayList<>();
            for (int i = 0; i < poblacion.size() - 1; i += 2) {
                Individuo parent1 = poblacion.get(i);
                Individuo parent2 = poblacion.get(i + 1);

                if (Math.random() < probCruce) {
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

            // Elitismo
            aplicarElitismo();

            // Evaluación de fitness
            fitness_evaluation();

            // Registrar datos históricos
            registrarHistorial(generacion);
        }
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

    private void aplicarElitismo() {
        int numElitistas = (int) (elitismRate * tamPoblacion);
        if (numElitistas > 0) {
            // Ordenar la población actual por fitness
            poblacion.sort((a, b) -> Double.compare(a.getFitness(), b.getFitness()));

            // Reemplazar los peores descendientes con los mejores individuos de la población actual
            for (int i = 0; i < numElitistas; i++) {
                poblacion.set(i, poblacion.get(i).copy());
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