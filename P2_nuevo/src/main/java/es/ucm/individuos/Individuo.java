package es.ucm.individuos;


import es.ucm.genes.Gen;
import es.ucm.genes.IntegerGen;

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

    /**
     * Devuelve una lista con el fenotipo de cada gen (lista de valores de las variables)
     */
    public List<Number> getFenotipos() {
        return this.genes.stream().map(Gen::getFenotipo).toList();
    }

    public void printGenotipo() {
        System.out.println(this.genotipoToString());
    }

    protected String genotipoToString() {
        StringBuilder result = new StringBuilder();
        result.append("[ ");
        for (Gen gen: this.genes) {
            result.append(gen.toString());
            result.append(" ");
        }
        result.append("]");

        return result.toString();
    }


    public int getGenotipoLength() {
        int total = 0;
        for (Gen gen: this.genes) {
            total += gen.getTamGen();
        }

        return total;
    }

    public String getSolutionString() {
        int i = 1;
        StringBuilder result = new StringBuilder();
        for (Number var: this.getFenotipos()) {
            result.append(String.format("x%d = %s;   ", i, var));
            if (i % 5 == 0) {
                result.append("\n");
            }
            i++;
        }

        return result.toString();
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
