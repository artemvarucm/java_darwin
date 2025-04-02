package es.ucm.genes;



/**
 * El gen que representa una variable de tipo int
 * GENOTIPO: Int
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

    public String toString() {
        return genotipo.toString();
    }

    public void set(int index, Integer value) {
        this.genotipo = value;
    }

    public Integer get(int index) {
        return this.genotipo;
    }
}
