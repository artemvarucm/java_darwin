package es.ucm.mutation;

import es.ucm.individuos.Individuo;
import es.ucm.genes.IntegerGen;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class HeuristicMutate extends AbstractMutate {
    @Override
    public void mutate(Individuo ind) {
        List<IntegerGen> intGenes = ind.getIntGenes();
        int n = intGenes.size();
        if (n < 2)
            return;
        
        // Obtiene la permutación actual
        List<Integer> currentPermutation = new ArrayList<>();
        for (IntegerGen g : intGenes) {
            currentPermutation.add(g.getFenotipo());
        }
        
        double bestFitness = ind.getFitness();
        int bestInsertPos = -1;
        
        // Se elige aleatoriamente el índice del gen a mover
        int indexToMove = ThreadLocalRandom.current().nextInt(0, n);
        Integer geneValue = currentPermutation.get(indexToMove);
        // Se remueve de la posición original
        currentPermutation.remove(indexToMove);
        
        // Itera por todas las posiciones posibles para insertar el gen
        for (int pos = 0; pos <= currentPermutation.size(); pos++) {
            List<Integer> tempPermutation = new ArrayList<>(currentPermutation);
            tempPermutation.add(pos, geneValue);
            
            // Crea un clon del individuo y actualiza la permutación
            Individuo tempInd = ind.copy();
            List<IntegerGen> tempIntGenes = tempInd.getIntGenes();
            for (int i = 0; i < tempIntGenes.size(); i++) {
                tempIntGenes.get(i).set(0, tempPermutation.get(i));
            }
            double tempFitness = tempInd.getFitness();
            
            // Según se maximice o minimice se actualiza la mejor posicion
            if (!ind.getMaximizar() && tempFitness < bestFitness) {
                bestFitness = tempFitness;
                bestInsertPos = pos;
            } else if (ind.getMaximizar() && tempFitness > bestFitness) {
                bestFitness = tempFitness;
                bestInsertPos = pos;
            }
        }
        
        // Si se encontró alguna mejora, se actualiza el individuo original
        if (bestInsertPos != -1) {
            // Se reconstruye la permutación final:
            List<Integer> finalPermutation = new ArrayList<>();
            // Se vuelve a obtener la permutación actual
            List<Integer> originalPermutation = new ArrayList<>();
            for (IntegerGen g : intGenes) {
                originalPermutation.add(g.getFenotipo());
            }
            originalPermutation.remove(indexToMove);
            originalPermutation.add(bestInsertPos, geneValue);
            finalPermutation = originalPermutation;
            
            // Se actualizan los genes del individuo
            for (int i = 0; i < n; i++) {
                intGenes.get(i).set(0, finalPermutation.get(i));
            }
        }
    }
}