package es.ucm.genes;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;



/**
 * El gen que representa una variable de tipo float con un limite de estados que puede tomar
 * GENOTIPO: Array de bools
 */
public class BooleanGen extends Gen<Boolean> {
    private Double min;
    private Double max;
    private Double precision;
    private List<Boolean> genotipo;

    public BooleanGen(Double min, Double max, Double precision) {
        this.min = min;
        this.max = max;
        this.precision = precision;

        this.tamGen = (int) Math.ceil(Math.log10(((max - min) / precision) + 1) / Math.log10(2));
        this.genotipo = Arrays.asList(new Boolean[tamGen]);

        // rellenamos con valores aleatorios
        randomInit();
    }

    public BooleanGen(BooleanGen gen) {
        this(gen.min, gen.max, gen.precision);
        // copiamos genotipo
        Collections.copy(this.genotipo, gen.genotipo);
    }

    public void set(int index, Boolean value) {
        this.genotipo.set(index, value);
    }

    public Boolean get(int index) {
        return this.genotipo.get(index);
    }

    public Double getFenotipo() {
        return min + precision * genotipoToInt();
    }

    /**
     * Inicializa aleatoriamente el gen
     */
    private void randomInit() {
        double random = ThreadLocalRandom.current().nextDouble(this.min, this.max);
        setGenotipoFromFloat(random);
    }

    /**
     * Cambia el genotipo guardando el valor float
     */
    private void setGenotipoFromFloat(double valor) {
        int valorConvertido = (int) ((valor - min) / precision);

        for (int i = 0; i < tamGen; i++) {
            this.set(tamGen - 1 - i, ((valorConvertido >> i) & 1) == 1);
        }
    }

    public void printGenotipo() {
        for (boolean bit: this.genotipo)
            System.out.print(bit ? "1" : "0");
    }

    /**
     * Convirte binario a decimal
     */
    public int genotipoToInt() {
        int resultado = 0;
        for (int i = 0; i < this.tamGen; i++) {
            if (this.get(i)) {
                // 2^(n - 1 - i)
                resultado |= (1 << (tamGen - 1 - i));
            }
        }

        return resultado;
    }

    /**
     * Mutacion, reemplaza el bit con el inverso
     * 0 -> 1
     * 1 -> 0
     */
    @Override
    public void mutate(int index) {
        Boolean value = this.genotipo.get(index);
        this.genotipo.set(index, !value);
    }

    public Gen clone() {
        return new BooleanGen(this);
    }
}
