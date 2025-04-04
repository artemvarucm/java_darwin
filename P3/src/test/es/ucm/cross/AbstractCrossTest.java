package es.ucm.cross;

import es.ucm.individuos.Individuo;
import es.ucm.individuos.IndividuoHormiga;
import es.ucm.mapa.SantaFeMap;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class AbstractCrossTest {
    public void crossTest(AbstractCross crosser) {
        // 10000 cruces
        for (int i = 0; i < 10000; i++) {
            oneCross(crosser);
        }
    }

    private void oneCross(AbstractCross crosser) {
        /*Individuo par1 = new IndividuoHormiga(new SantaFeMap());
        Individuo par2 = new IndividuoHormiga(new SantaFeMap());
        System.out.println("PADRES");
        par1.printGenotipo();
        par2.printGenotipo();
        List<Individuo> hijos = crosser.cross(par1, par2);
        System.out.println("HIJOS");
        hijos.get(0).printGenotipo();
        hijos.get(1).printGenotipo();
        Set<Integer> child1 = new HashSet<>();
        Set<Integer> child2 = new HashSet<>();

        // para ver que no salen habitaciones compartida
        for (int i = 0; i < hijos.get(0).getIntGenes().size(); i++) {
            int gen1 = hijos.get(0).getIntGenes().get(i).getFenotipo();
            int gen2 = hijos.get(1).getIntGenes().get(i).getFenotipo();
            if (child1.contains(gen1)) {
                throw new RuntimeException("HABITACION REPETIDA");
            } else {
                child1.add(gen1);
            }

            if (child2.contains(gen2)) {
                throw new RuntimeException("HABITACION REPETIDA");
            } else {
                child2.add(gen2);
            }
        }*/
    }
}