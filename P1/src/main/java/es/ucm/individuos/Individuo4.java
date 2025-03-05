package es.ucm.individuos;

import java.util.List;

/**
 * PROBLEMA 4: Michalewicz Function
 */
public class Individuo4 extends Individuo {

    private int dimension; // Parámetro d (dimensión)
    private final int m = 10; // Parámetro m fijo

    public Individuo4(double precision, int dimension) {
        super(precision, false); // Minimizar el fitness
        this.dimension = dimension;

        // Añadir genes para cada variable xi ∈ [0, π]
        for (int i = 0; i < dimension; i++) {
            this.addLimitedGen(0.0, Math.PI, precision);
        }
    }

    @Override
    public double getFitness() {
        List<Double> x = this.getFenotipos();

        double fitness = 0.0;

        // Calcular la función Michalewicz
        for (int i = 0; i < dimension; i++) {
            double xi = x.get(i);
            fitness += Math.sin(xi) * Math.pow(Math.sin((i + 1) * xi * xi / Math.PI), 2 * m);
        }

        return -fitness; // Minimizar (el valor original es negativo)
    }

    @Override
    public Individuo copy() {
        Individuo clon = new Individuo4(precision, dimension);
        this.copyToClone(clon);
        return clon;
    }

    public int getDimension() {
        return dimension;
    }
}