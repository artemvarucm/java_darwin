package es.ucm.genes;


import java.util.concurrent.ThreadLocalRandom;

/**
 * El gen que representa una variable de tipo float
 * GENOTIPO: Double
 */
public class RealGen extends Gen<Double> {
    private Double genotipo;

    public RealGen() {
        this.tamGen = 1;

        // rellenamos con valor aleatorio
        randomInit();
    }

    public Double getFenotipo() {
        return genotipo;
    }

    /**
     * Inicializa aleatoriamente el gen
     */
    private void randomInit() {
        // FIXME genera un numero entre 0 y 1
        this.genotipo = ThreadLocalRandom.current().nextDouble();
    }

    public void printGenotipo() {
        System.out.print(genotipo);
    }

    protected void set(int index, Double value) {
        this.genotipo = value;
    }

    protected Double get(int index) {
        return this.genotipo;
    }
}
