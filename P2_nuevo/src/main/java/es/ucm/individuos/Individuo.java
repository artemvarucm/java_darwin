package es.ucm.individuos;


import es.ucm.genes.BooleanGen;
import es.ucm.genes.Gen;
import es.ucm.genes.IntegerGen;
import es.ucm.genes.RealGen;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * LO MISMO QUE CROMOSOMA, REPRESENTA UNA SOLUCIÓN DEL PROBLEMA
 */
public abstract class Individuo {
    protected List<Gen> genes;
    protected Boolean maximizar; // indica si necesitamos maximizar o minimizar el fitness

    protected Double precision;

    public Individuo(Double precision, Boolean maximizar) {
        this.maximizar = maximizar;
        this.precision = precision;
        this.genes = new LinkedList<>();
    }

    public Boolean getMaximizar() {
        return this.maximizar;
    }

    public double getAdjustedFitness(double minFitness, double maxFitness) {
        // 1.05 para todos tengan alguna probabilidad de ser elegido, incluso el peor
        if (maximizar) {
            return getFitness() + (1.05 * Math.abs(minFitness));
        } else {
            return - getFitness() + (1.05 * Math.abs(maxFitness));
        }
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

    public void addIntegerGen(Integer initValue) {
        this.genes.add(new IntegerGen(initValue));
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
    public List<Number> getFenotipos() {
        return this.genes.stream().map(Gen::getFenotipo).toList();
    }

    public void printGenotipo() {
        System.out.print("[ ");
        for (Gen gen: this.genes) {
            gen.printGenotipo();
            System.out.print(" ");
        }
        System.out.print("]");
        System.out.println();
    }

    /**
     * Número de todos los posibles puntos de cruce
     *
     * Ej: 2 variables de 3 y 4 bits
     * 1;0;1;0;1;0;1 -> devuelve 6
     */
    public int getNumberOfCrossPoints() {
        return this.getGenotipoLength() - 1;
    }

    public int getGenotipoLength() {
        int total = 0;
        for (Gen gen: this.genes) {
            total += gen.getTamGen();
        }

        return total;
    }

    /**
     * Para un índice GLOBAL del genotipo, devueve una lista
     *     el primer valor es el índice del gen
     *     el segundo valor es el índice LOCAL dentro del gen
     * Ej:
     * Genotipo actual: 1011 (2 genes de 2 bits, 10 y 11)
     * findGeneIndex(2)
     * Resultado: [1, 0]
     */
    private List<Integer> findGenIndex(int index) {
        int total = 0;
        for (int gen = 0; gen < this.genes.size(); gen++) {
            int tamGen = this.genes.get(gen).getTamGen();
            if (index < total + tamGen) {
                int localIndex = index - total;

                List<Integer> result = new ArrayList<>();
                result.add(gen);
                result.add(localIndex);
                return result;
            }
            total += tamGen;
        }

        throw new RuntimeException("[ERROR]: Elemento de genotipo no encontrado.");
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
        List<Integer> genInfo = this.findGenIndex(index);
        int gen = genInfo.get(0);
        int localIndex = genInfo.get(1);
        this.genes.get(gen).fillFromGen(localIndex, ind.genes.get(gen));
    }

    /**
     * Muta el elemento del genotipo (busca el gen y la parte del gen que hay que mutar y le aplica la mutacion)
     */
    public void mutateGenotypeElem(int index) {
        if (index < 0 || index >= getGenotipoLength()) {
            throw new IllegalArgumentException("Índice fuera de rango: " + index);
        }

        List<Integer> genInfo = this.findGenIndex(index);
        int gen = genInfo.get(0);
        int localIndex = genInfo.get(1);
        this.genes.get(gen).mutate(localIndex);
    }

    /**
     * Devuelve todos los genes de tipo IntegerGen del individuo
     */
    public List<IntegerGen> getIntGenes() {
        List<IntegerGen> genesEnteros = new ArrayList<>();
        for (Gen gen: this.genes) {
            if (gen instanceof IntegerGen) {
                genesEnteros.add((IntegerGen) gen);
            }
        }

        return genesEnteros;
    }
}
