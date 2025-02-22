package es.ucm.individuos;

import java.util.List;

/**
 * PROBLEMA 5: Michalewicz Function con codificación real
 */
public class Individuo5 extends Individuo {

    private int dimension; // Parámetro d (dimensión)
    private final int m = 10; // Parámetro m fijo

    public Individuo5(int dimension) {
        super(false); // Minimizar el fitness
        this.dimension = dimension;

        // Añadir genes reales para cada variable xi ∈ [0, π]
        for (int i = 0; i < dimension; i++) {
            this.addUnlimitedGen(0.0, Math.PI); // Usamos addUnlimitedGen para genes reales
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
        Individuo clon = new Individuo5(this.dimension);
        this.copyToClone(clon);
        return clon;
    }

    public int getDimension() {
        return dimension;
    }
}