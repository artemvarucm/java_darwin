package es.ucm;


import java.util.Arrays;
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
    private Integer tamGen;
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

    protected void set(int index, Boolean value) {
        this.genotipo.set(index, value);
    }

    protected Boolean get(int index) {
        return this.genotipo.get(index);
    }

    public Double getFenotipo() {
        return min + precision * genotipoToInt();
    }

    public int getTamGen() {
        return this.tamGen;
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
        // FIXME añadir comprobacion de estar entre maximo y minimo
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
}
