package es.ucm;

import java.util.List;

public class AlgoritmoGenetico {
    private List<Individuo> poblacion;
    private List<Double> fitness;

    // Parametros
    private Integer maxGeneraciones;
    private Double probCruce;
    private Double probMutacion;
    private Integer tamTorneo;

    // Optimo
    private Individuo mejor;
    private int pos_mejor;

    public AlgoritmoGenetico() {
        // seleccionar diferentes clases para:
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
