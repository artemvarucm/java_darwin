package es.ucm.selection;

import es.ucm.factories.IndividuoFactory;
import es.ucm.individuos.Individuo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class TorneoSelection extends AbstractSelection {
    private int n_participantes;
    private boolean deterministico;
    private double select_max_probability = 0.5; // solo se usa en el probabilistico (deterministico = false)

    public TorneoSelection(IndividuoFactory factory) {
        this(factory, 3, true); // por defecto son 3 participantes y determinisitico
    }

    public TorneoSelection(IndividuoFactory factory, int n_participantes, boolean deterministico) {
        super(factory);
        this.n_participantes = n_participantes;
        this.deterministico = deterministico;
    }

    @Override
    public List<Individuo> select(List<Individuo> poblacion) {
        int tam = poblacion.size();
        List<Individuo> seleccion = new ArrayList<>(tam);
        for (int i = 0; i < tam; i++) {
            seleccion.add(realizarTorneo(poblacion));
        }

        return seleccion;
    }

    private Individuo realizarTorneo(List<Individuo> poblacion) {
        int tam = poblacion.size();
        int minInd = -1;
        int maxInd = -1;
        double maxFitness = 0; // el 0 es para que esté inicializada, se sobreescribe en el primer ciclo
        double minFitness = 0; // el 0 es para que esté inicializada, se sobreescribe en el primer ciclo

        List<Individuo> participantes = new ArrayList<>(n_participantes);
        List<Double> p_fitness = new ArrayList<>(n_participantes);
        for (int i = 0; i < n_participantes; i++) {
            int elegido = ThreadLocalRandom.current().nextInt(0, tam);
            participantes.add(poblacion.get(elegido));
            double fitness = participantes.get(i).getFitness();
            p_fitness.add(fitness);

            if (minInd == -1 || minFitness > fitness) {
                minInd = i;
                minFitness = fitness;
            }

            if (maxInd == -1 || maxFitness < fitness) {
                maxInd = i;
                maxFitness = fitness;
            }
        }
        Individuo ganador;
        double p = ThreadLocalRandom.current().nextDouble();
        if (deterministico || p > this.select_max_probability) {
            ganador = participantes.get(maxInd);
        } else {
            ganador = participantes.get(minInd);
        }

        return ganador.copy();
    }
}
