package es.ucm.mutation;

import es.ucm.individuos.Individuo;
import es.ucm.genes.IntegerGen;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class InsertionMutate extends AbstractMutate {
    public InsertionMutate(double mutateProbability) {
        super(mutateProbability);
    }

    @Override
    public Individuo mutate(Individuo ind) {
        Individuo indMutado = ind.copy();
        List<IntegerGen> intGenes = indMutado.getIntGenes();
        int n = intGenes.size();
        double p = ThreadLocalRandom.current().nextDouble();
        if (p < mutateProbability && n > 1) {
            // removeIndex -> la posicion del número que sacamos
            // insertIndex -> la posicion en la que lo metemos
            int removeIndex = ThreadLocalRandom.current().nextInt(0, n);
            int insertIndex = ThreadLocalRandom.current().nextInt(0, n);
            while (insertIndex == removeIndex) {
                insertIndex = ThreadLocalRandom.current().nextInt(0, n);
            }

            // Obtiene la permutación actual
            List<Integer> permutation = new ArrayList<>();
            for (IntegerGen g : intGenes) {
                permutation.add(g.getFenotipo());
            }

            // Extrae el elemento y lo reubica
            Integer elem = permutation.remove(removeIndex);
            permutation.add(insertIndex, elem);

            // Actualiza los genes con la nueva permutación
            for (int i = 0; i < n; i++) {
                intGenes.get(i).set(0, permutation.get(i));
            }
        }

        return indMutado;
    }
}