package es.ucm.mutation;

import es.ucm.individuos.Individuo;
import es.ucm.genes.IntegerGen;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class InversionMutate extends AbstractMutate {
    public InversionMutate(double mutateProbability) {
        super(mutateProbability);
    }

    @Override
    public Individuo mutate(Individuo ind) {
        Individuo indMutado = ind.copy();
        List<IntegerGen> intGenes = indMutado.getIntGenes();
        int n = intGenes.size();
        double p = ThreadLocalRandom.current().nextDouble();

        if (p < mutateProbability && n > 1) {
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

            // Invierte el segmento [start, end]
            while (start < end) {
                Integer temp = permutation.get(start);
                permutation.set(start, permutation.get(end));
                permutation.set(end, temp);
                start++;
                end--;
            }

            // Actualiza los genes con la nueva permutación
            for (int i = 0; i < n; i++) {
                intGenes.get(i).set(0, permutation.get(i));
            }
        }

        return indMutado;
    }
}