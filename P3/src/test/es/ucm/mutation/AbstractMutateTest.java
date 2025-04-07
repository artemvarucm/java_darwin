package es.ucm.mutation;

import es.ucm.individuos.Individuo;
import es.ucm.individuos.IndividuoHormiga;
import es.ucm.individuos.arbol.AbstractNode;
import es.ucm.initializer.FULLInitializer;
import es.ucm.mapa.SantaFeMap;

public abstract class AbstractMutateTest {
    public void mutateTest(AbstractMutate mutator) {
        // 1000 mutaciones
        for (int i = 0; i < 1000; i++) {
            oneMutate(mutator);
        }
    }

    private void oneMutate(AbstractMutate mutator) {
        AbstractNode node = new FULLInitializer(4).initialize();
        Individuo ind1 = new IndividuoHormiga(new SantaFeMap(), 400, 0., node);
        System.out.println("GENOTIPO INICIAL");
        ind1.printGenotipo();
        Individuo indMutado = mutator.mutate(ind1);
        System.out.println("GENOTIPO MUTADO");
        indMutado.printGenotipo();
    }
}