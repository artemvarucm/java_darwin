package es.ucm.factories;

import es.ucm.Individuo;

import java.util.List;

/**
 * Se usa al crear la población inicial
 */
public abstract class IndividuoFactory {
    public abstract List<Individuo> createIndividuos(int tam);
}
