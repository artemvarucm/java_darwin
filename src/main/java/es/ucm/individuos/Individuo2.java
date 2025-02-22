package es.ucm.individuos;

import java.util.List;

/**
 * PROBLEMA 2: Mishra Bird Function
 */
public class Individuo2 extends Individuo {

    public Individuo2() {
        super(false); // Minimizar el fitness

        // x1 ∈ [-10, 0]
        this.addLimitedGen(-10.0, 0.0, 0.0000001);
        // x2 ∈ [-6.5, 0]
        this.addLimitedGen(-6.5, 0.0, 0.0000001);
    }

    @Override
    public double getFitness() {
        List<Double> x = this.getFenotipos();

        double x1 = x.get(0);
        double x2 = x.get(1);

        double term1 = Math.sin(x2) * Math.exp(Math.pow(1 - Math.cos(x1), 2));
        double term2 = Math.cos(x1) * Math.exp(Math.pow(1 - Math.sin(x2), 2));
        double term3 = Math.pow(x1 - x2, 2);

        return term1 + term2 + term3;
    }

    @Override
    public Individuo copy() {
        Individuo clon = new Individuo2();
        this.copyToClone(clon);
        return clon;
    }
}