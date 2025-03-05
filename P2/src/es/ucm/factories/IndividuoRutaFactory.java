package es.ucm.factories;

import es.ucm.individuos.Individuo;
import es.ucm.individuos.IndividuoRuta;

public class IndividuoRutaFactory extends IndividuoFactory {

    @Override
    public Individuo createOne() {
        return new IndividuoRuta();
    }
}