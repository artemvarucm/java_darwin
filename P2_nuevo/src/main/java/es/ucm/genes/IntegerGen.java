package es.ucm.genes;


import java.util.concurrent.ThreadLocalRandom;

/**
 * El gen que representa una variable de tipo float
 * GENOTIPO: Double
 */
public class IntegerGen extends Gen<Integer> {
    private Integer genotipo;

    public IntegerGen(Integer initValue) {
        this.tamGen = 1;
        this.genotipo = initValue;
    }

    public IntegerGen(IntegerGen gen) {
        this(gen.genotipo);
    }

    @Override
    public Gen clone() {
        return new IntegerGen(this);
    }

    public Integer getFenotipo() {
        return genotipo;
    }

    public void printGenotipo() {
        System.out.print(genotipo);
    }

    @Override
    public void mutate(int index) {}

    public void set(int index, Integer value) {
        this.genotipo = value;
    }

    public Integer get(int index) {
        return this.genotipo;
    }
}
