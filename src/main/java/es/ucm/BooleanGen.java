package es.ucm;


import java.util.concurrent.ThreadLocalRandom;

public class BooleanGen extends Gen<Double> {
    private Double min;
    private Double max;
    private Double precision;
    private Integer tamGen;
    private boolean[] genotipo;

    public BooleanGen(Double min, Double max, Double precision) {
        this.min = min;
        this.max = max;
        this.precision = precision;

        this.tamGen = (int) Math.ceil(Math.log10(((max - min) / precision) + 1) / Math.log10(2));
        this.genotipo = new boolean[tamGen];

        // rellenamos con valores aleatorios
        randomInit();
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
            this.genotipo[tamGen - 1 - i] = ((valorConvertido >> i) & 1) == 1;
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
            if (this.genotipo[i]) {
                // 2^(n - 1 - i)
                resultado |= (1 << (tamGen - 1 - i));
            }
        }

        return resultado;
    }
}
