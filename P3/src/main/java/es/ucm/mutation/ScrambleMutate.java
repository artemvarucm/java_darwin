package es.ucm.mutation;

import es.ucm.individuos.Individuo;
import es.ucm.genes.IntegerGen;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

// Se elige un segmento de la permutación y se mezclan aleatoriamente sus elementos.

public class ScrambleMutate extends AbstractMutate {
    public ScrambleMutate(double mutateProbability) {
        super(mutateProbability);
    }

    @Override
    public Individuo mutate(Individuo ind) {
        Individuo indMutado = ind.copy();
        List<IntegerGen> intGenes = indMutado.getIntGenes();
        int n = intGenes.size();
        double p = ThreadLocalRandom.current().nextDouble();
        if (p < mutateProbability && n > 1) {
            // Se eligen dos índices aleatorios para definir el segmento
            int index1 = ThreadLocalRandom.current().nextInt(0, n);
            int index2 = ThreadLocalRandom.current().nextInt(0, n);
            while (index1 == index2) {
                index2 = ThreadLocalRandom.current().nextInt(0, n);
            }
            int start = Math.min(index1, index2);
            int end = Math.max(index1, index2);

            // Obtiene la permutación actual
            List<Integer> permutation = new ArrayList<>();
            for (IntegerGen g : intGenes) {
                permutation.add(g.getFenotipo());
            }

            // Se extrae y mezcla aleatoriamente el subsegmento [start, end]
            List<Integer> sublist = new ArrayList<>(permutation.subList(start, end + 1));
            Collections.shuffle(sublist);
            for (int i = start; i <= end; i++) {
                permutation.set(i, sublist.get(i - start));
            }

            // Actualiza los genes con la nueva permutación
            for (int i = 0; i < n; i++) {
                intGenes.get(i).set(0, permutation.get(i));
            }
        }
        
        return indMutado;
    }
}