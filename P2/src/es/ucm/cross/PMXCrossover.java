package es.ucm.cross;

import es.ucm.individuos.IndividuoRuta;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PMXCrossover {

    private static Random random = new Random();

    /**
     * Realiza el cruce PMX entre dos IndividuoRuta y retorna un nuevo individuo (hijo)
     */
    public static IndividuoRuta crossover(IndividuoRuta parent1, IndividuoRuta parent2) {
        List<Integer> crom1 = parent1.getCromosoma();
        List<Integer> crom2 = parent2.getCromosoma();

        int size = crom1.size();
        List<Integer> child = new ArrayList<>(size);

        // Inicializamos el hijo con un valor marcador (-1) para cada posición
        for (int i = 0; i < size; i++) {
            child.add(-1);
        }

        // Seleccionar dos puntos de corte válidos (asegurándose que sean distintos y en orden)
        int cut1 = random.nextInt(size);
        int cut2 = random.nextInt(size);
        if (cut1 > cut2) {
            int temp = cut1;
            cut1 = cut2;
            cut2 = temp;
        }
        // En caso de ser iguales, se fuerza a que sean distintos
        if (cut1 == cut2) {
            cut2 = (cut1 + 1) % size;
            if (cut1 > cut2) {
                int temp = cut1;
                cut1 = cut2;
                cut2 = temp;
            }
        }

        // Copiar el segmento del primer padre al hijo
        for (int i = cut1; i <= cut2; i++) {
            child.set(i, crom1.get(i));
        }

        // Resolver el mapeo para la sección intercambiada
        for (int i = cut1; i <= cut2; i++) {
            int geneFromParent2 = crom2.get(i);
            // Si ya se encuentra en el segmento copiado se saltamos 
            if (child.contains(geneFromParent2)) {
                continue;
            }
            // Determinar la posición en la que se debe colocar el gene
            int position = i;
            while (true) {
                int geneInParent1 = crom1.get(position);
                // Buscar la posición de ese gen en el parent2
                position = crom2.indexOf(geneInParent1);
                // Si esa posición no forma parte del segmento copiado en el hijo, se coloca el gene
                if (child.get(position) == -1) {
                    child.set(position, geneFromParent2);
                    break;
                }
            }
        }

        // Completar el resto del hijo con los genes del parent2 en las posiciones que queden marcadas
        for (int i = 0; i < size; i++) {
            if (child.get(i) == -1) {
                child.set(i, crom2.get(i));
            }
        }

        return new IndividuoRuta(child);
    }
}