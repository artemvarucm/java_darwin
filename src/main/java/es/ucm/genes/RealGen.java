package es.ucm.genes;


import java.util.concurrent.ThreadLocalRandom;

/**
 * El gen que representa una variable de tipo float
 * GENOTIPO: Double
 */
public class RealGen extends Gen<Double> {
    private Double min;
    private Double max;
    private Double genotipo;

    public RealGen(Double min, Double max) {
        this.min = min;
        this.max = max;
        this.tamGen = 1;

        // rellenamos con valor aleatorio
        randomInit();
    }

    public RealGen(RealGen gen) {
        this(gen.min, gen.max);
        // copiamos genotipo
        this.genotipo = gen.genotipo;
    }

    @Override
    public Gen clone() {
        return new RealGen(this);
    }

    public Double getFenotipo() {
        return genotipo;
    }

    /**
     * Inicializa aleatoriamente el gen
     */
    private void randomInit() {
        this.genotipo = ThreadLocalRandom.current().nextDouble(this.min, this.max);
    }

    public void printGenotipo() {
        System.out.print(genotipo);
    }

    @Override
    public void mutate(int index) {
        randomInit(); // volvemos a asignar aleatoriamente
    }

    public void set(int index, Double value) {
        this.genotipo = value;
    }

    public Double get(int index) {
        return this.genotipo;
    }
}
