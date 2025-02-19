package es.ucm.individuos;


import es.ucm.genes.BooleanGen;
import es.ucm.genes.Gen;
import es.ucm.genes.RealGen;

import java.util.LinkedList;
import java.util.List;

/**
 * LO MISMO QUE CROMOSOMA, REPRESENTA UNA SOLUCIÓN DEL PROBLEMA
 */
public abstract class Individuo {
    protected List<Gen> genes;
    protected Boolean maximizar; // indica si necesitamos maximizar o minimizar el fitness

    public Individuo() {
        this(true);
    }

    public Individuo(Boolean maximizar) {
        this.maximizar = maximizar;
        this.genes = new LinkedList<>();
    }

    public abstract double getFitness();
    public abstract Individuo copy();

    protected void copyToClone(Individuo ind) {
        ind.maximizar = this.maximizar;

        List<Gen> copiaGenes = new LinkedList<>();
        for (Gen gen: genes) {
            copiaGenes.add(gen.clone());
        }

        ind.genes = copiaGenes;
    }

    public void addLimitedGen(double min, double max, double precision) {
        this.genes.add(new BooleanGen(min, max, precision));
    }

    public void addUnlimitedGen(double min, double max) {
        this.genes.add(new RealGen(min, max));
    }

    /**
     * Devuelve una lista con el fenotipo de cada gen (lista de valores de las variables)
     */
    public List<Double> getFenotipos() {
        return this.genes.stream().map(Gen::getFenotipo).toList();
    }

    public void printGenotipo() {
        for (Gen gen: this.genes) {
            gen.printGenotipo();
        }
        System.out.println();
    }

    /**
     * Número de todos los posibles puntos de cruce
     *
     * Ej: 2 variables de 3 y 4 bits
     * 1;0;1;0;1;0;1 -> devuelve 6
     */
    public int getNumberOfCrossPoints() {
        int total = 0;
        for (Gen gen: this.genes) {
            total += gen.getTamGen();
        }

        return total - 1;
    }

    /**
     * Actualiza la parte del genotipo actual
     * en la posicion index a partir de esa parte del genotipo del individuo en el argumento
     *
     * Se usa en el operador cruce
     * Ej:
     * Genotipo actual: 1011
     * fillGenotypeElem(2, 1000)
     * Resultado: 1001
     */
    public void fillGenotypeElem(int index, Individuo ind) {
        int total = 0;
        boolean updated = false;
        for (int i = 0; i < this.genes.size(); i++) {
            int tamGen = this.genes.get(i).getTamGen();
            if (index < total + tamGen) {
                updated = true;
                int genIndex = (index - total);
                this.genes.get(i).fillFromGen(genIndex, ind.genes.get(i));
                break;
            }

            total += tamGen;
        }

        if (!updated) {
            throw new RuntimeException("[ERROR]: fillGenotypeElem - elemento de genoma no encontrado.");
        }
    }
}
