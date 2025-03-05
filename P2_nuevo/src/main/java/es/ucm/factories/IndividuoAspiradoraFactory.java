package es.ucm.factories;

import es.ucm.individuos.Individuo;
import es.ucm.individuos.IndividuoAspiradora;

import java.util.ArrayList;
import java.util.List;

/**
 * Fábrica de Individuos, puede crear Individuos de tipo determinado, ej. Individuo1
 *
 * Se usa en el opeardor cruce y al crear la población inicial
 */
public class IndividuoAspiradoraFactory extends IndividuoFactory {
    public Individuo createOne() {
        return new IndividuoAspiradora();
    }
}
