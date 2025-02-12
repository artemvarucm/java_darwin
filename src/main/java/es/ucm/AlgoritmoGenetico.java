package es.ucm;

import es.ucm.factories.IndividuoFactory;

import java.util.ArrayList;
import java.util.List;

public class AlgoritmoGenetico {
    private IndividuoFactory factory;
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


    public AlgoritmoGenetico(IndividuoFactory factory, Integer tamPoblacion) {
        this.factory = factory;
        this.tamPoblacion = tamPoblacion;

        // FIXME VALORES POR DEFECTO, meter en una funcion setConfig
        this.maxGeneraciones = 10;
        this.probCruce = 0.5;
        this.probMutacion = 0.1;
        this.tamTorneo = 10;
        //this.fitness = new ArrayList<>();

        this.poblacion = new ArrayList<>(tamPoblacion);
    }

    public void optimize() {
        this.poblacion = random_sample();
        this.fitness_evaluation();

        int generacion = 0;
        boolean converged = false;
        while (generacion < maxGeneraciones && !converged) {
            System.out.printf("GENERACION: %d%n", generacion);
            this.selection();
            this.cross();
            this.mutation();
            this.fitness_evaluation();
            generacion++;
        }
    }

    private List<Individuo> random_sample() {
        return factory.createIndividuos(this.tamPoblacion);
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
