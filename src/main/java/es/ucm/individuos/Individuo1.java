package es.ucm.individuos;


import java.util.List;

/**
 * PROBLEMA 1
 */
public class Individuo1 extends Individuo {
    public Individuo1(double precision) {
        super(precision, true);

        // x1
        this.addLimitedGen(-3.0, 12.1, precision);
        // x2
        this.addLimitedGen(4.1, 5.8, precision);
    }

    public double getFitness() {
        List<Double> x = this.getFenotipos();

        return 21.5
                + x.get(0) * Math.sin(4 * Math.PI * x.get(0))
                + x.get(1) * Math.sin(20 * Math.PI * x.get(1));
    }

    @Override
    public Individuo copy() {
        Individuo clon = new Individuo1(precision);
        this.copyToClone(clon);
        return clon;
    }
}
