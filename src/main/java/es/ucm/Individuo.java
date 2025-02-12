package es.ucm;


import java.util.LinkedList;
import java.util.List;

/**
 * LO MISMO QUE CROMOSOMA, REPRESENTA UNA SOLUCIÓN DEL PROBLEMA
 */
public abstract class Individuo {
    List<Gen> genes;

    public Individuo() {
        this.genes = new LinkedList<>();
    }

    public void addLimitedGen(double min, double max, double precision) {
        this.genes.add(new BooleanGen(min, max, precision));
    }

    // Devuelve una lista con el fenotipo de cada gen (lista de valores de las variables)
    public List<Double> getFenotipos() {
        return this.genes.stream().map(Gen::getFenotipo).toList();
    }

    public void addUnlimitedGen() {
        this.genes.add(new RealGen());
    }

    public abstract double getFitness();
}
