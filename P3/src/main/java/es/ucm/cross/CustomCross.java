package es.ucm.cross;

import es.ucm.factories.IndividuoFactory;
import es.ucm.genes.IntegerGen;
import es.ucm.individuos.Individuo;

import java.util.ArrayList;
import java.util.List;

import static es.ucm.utils.RandomUtil.getNDifferentRandInt;


/**
 * Como cruce propio se propone usar lo siguente:
 * - Escoger n posiciones aleatorias y guardar en orden los valores que tienen en el primer padre
 * - Encontrar las posiciones de esos valores en el segundo padre y guardar las posiciones y el orden de los valores
 * - En el primer padre reemplazar las posiciones utilizando el orden del segundo padre
 * - En el segundo padre reemplazar las posiciones utilizando el orden del primer padre
 *
 * Ej.
 * 4 3 2 1
 * 2 1 4 3
 * Seleccionamos 2 posiciones -> 0 y 3
 * Que son los numeros 4 y 1
 * Encontramos las posiciones de esos numeros en el segundo hijo -> 1 y 2
 * En el primer padre los reordenamos segun el orden del segundo padre, en el segundo padre viceversa:
 * Primer hijo: 1 3 2 4
 * Segundo hijo: 2 4 1 3
 */
public class CustomCross extends AbstractCross {

    int N_RANDOMS = 6; // el número de posiciones aleatorias seleccionadas
    public CustomCross(IndividuoFactory factory) {
        super(factory);
    }

    @Override
    public List<Individuo> cross(Individuo parent1, Individuo parent2) {
        List<Integer> perm1 = getPermutation(parent1);
        List<Integer> perm2 = getPermutation(parent2);
        int n = perm1.size();
        // selected está ordenado de menor a mayor
        List<Integer> positions1 = getNDifferentRandInt(0, n, N_RANDOMS);
        List<Integer> positions2 = new ArrayList<>();
        List<Integer> order1 = new ArrayList<>();
        List<Integer> order2 = new ArrayList<>();

        // guardamos el orden del primer padre
        for (Integer i : positions1)
            order1.add(perm1.get(i));

        // encontramos las posiciones y el orden en el segundo padre
        for (int i = 0; i < n; i++) {
            if (order1.contains(perm2.get(i))) {
                positions2.add(i);
                order2.add(perm2.get(i));
            }
        }

        // invertimos los ordenes
        for (int i = 0; i < positions1.size(); i++) {
            int pos1 = positions1.get(i);
            int newVal1 = order2.get(i);
            perm1.set(pos1, newVal1);

            int pos2 = positions2.get(i);
            int newVal2 = order1.get(i);
            perm2.set(pos2, newVal2);
        }


        List<Individuo> result = new ArrayList<>(2);
        Individuo child1 = factory.createOne();
        Individuo child2 = factory.createOne();
        for (int i = 0; i < n; i++) {
            child1.getIntGenes().get(i).set(0, perm1.get(i));
            child2.getIntGenes().get(i).set(0, perm2.get(i));
        }
        result.add(child1);
        result.add(child2);
        return result;
    }

    // Extrae la permutación a partir de los genes
    private List<Integer> getPermutation(Individuo ind) {
        List<Integer> perm = new ArrayList<>();
        for (IntegerGen gene : ind.getIntGenes()) {
            perm.add(gene.getFenotipo());
        }
        return perm;
    }
}