package es.ucm.individuos;

import java.util.List;

/**
 * PROBLEMA 3: Schubert Function
 */
public class Individuo3 extends Individuo {

    public Individuo3(double precision) {
        super(precision, false); // Minimizar el fitness

        // x1 ∈ [-10, 10]
        this.addLimitedGen(-10.0, 10.0, precision);
        // x2 ∈ [-10, 10]
        this.addLimitedGen(-10.0, 10.0, precision);
    }

    @Override
    public double getFitness() {
        List<Double> x = this.getFenotipos();

        double x1 = x.get(0);
        double x2 = x.get(1);

        double sum1 = 0.0;
        double sum2 = 0.0;

        // Calcular la primera suma: sumatoria de i=1 a 5 de i * cos((i + 1) * x1 + i)
        for (int i = 1; i <= 5; i++) {
            sum1 += i * Math.cos((i + 1) * x1 + i);
        }

        // Calcular la segunda suma: sumatoria de i=1 a 5 de i * cos((i + 1) * x2 + i)
        for (int i = 1; i <= 5; i++) {
            sum2 += i * Math.cos((i + 1) * x2 + i);
        }

        // Multiplicar las dos sumas
        return sum1 * sum2;
    }

    @Override
    public Individuo copy() {
        Individuo clon = new Individuo3(precision);
        this.copyToClone(clon);
        return clon;
    }
}