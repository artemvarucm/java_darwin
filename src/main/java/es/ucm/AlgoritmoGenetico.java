package es.ucm;

import java.util.ArrayList;
import java.util.List;

public class AlgoritmoGenetico {
    private List<Individuo> poblacion;
    private List<Double> fitness;

    // Parametros
    private Integer tamPoblacion;
    private Integer maxGeneraciones;
    private Double probCruce;
    private Double probMutacion;
    private Integer tamTorneo;

    // Optimo
    private Individuo mejor;
    private int pos_mejor;

    public AlgoritmoGenetico() {
        // Valores por defecto
        this(50, 10, 0.5, 0.1, 10);
    }

    public AlgoritmoGenetico(Integer tamPoblacion, Integer maxGeneraciones, Double probCruce, Double probMutacion, Integer tamTorneo) {
        this.tamPoblacion = tamPoblacion;
        this.maxGeneraciones = maxGeneraciones;
        this.probCruce = probCruce;
        this.probMutacion = probMutacion;
        this.tamTorneo = tamTorneo;
        //this.fitness = new ArrayList<>();

        this.poblacion = new ArrayList<>(tamPoblacion);
    }

    public void optimize() {
        this.poblacion = random_sample();
        this.fitness_evaluation();

        int generacion = 0;
        boolean converged = false;
        while (generacion < maxGeneraciones && !converged) {
            this.selection();
            this.cross();
            this.mutation();
            this.fitness_evaluation();
            generacion++;
        }
    }

    private List<Individuo> random_sample() {
        return null;
    }

    private void fitness_evaluation() {
        //this.fitness =;
    }

    private void selection() {

    }

    private void cross() {

    }

    private void mutation() {

    }
}
