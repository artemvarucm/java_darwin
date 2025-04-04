package es.ucm.genes;


public abstract class Gen<GT, FT> {
    // La GT representa el tipo de elemento en el GENOTIPO del gen
    // La FT representa el tipo de elemento en el FENOTIPO del gen

    protected Integer tamGen;
    public Gen() {
    }

    public abstract Gen clone();

    /**
     * Devuelve el fenotipo del gen, ejemplo si es 11, devuelve 3
     */
    public abstract FT getFenotipo();

    public int getTamGen() {
        return this.tamGen;
    }

    /**
     * Actualiza la parte del gen
     * en la posicion index
     */
    protected abstract void set(int index, GT value);

    /**
     * Devuelve el valor de la parte del gen
     * en la posicion index
     */
    protected abstract GT get(int index);
}