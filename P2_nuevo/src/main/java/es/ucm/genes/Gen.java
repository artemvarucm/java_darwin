package es.ucm.genes;


public abstract class Gen<GT> {
    // La T representa el tipo de elemento en el GENOTIPO del gen
    // La U representa el tipo de elemento en el FENOTIPO del gen

    protected Integer tamGen;
    public Gen() {
    }

    public abstract Gen clone();

    /**
     * Devuelve el fenotipo del gen, ejemplo si es 11, devuelve 3
     */
    public abstract Number getFenotipo();

    public int getTamGen() {
        return this.tamGen;
    }

    public abstract void printGenotipo();

    /**
     * Muta la parte del gen
     */
    public abstract void mutate(int index);

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


    /**
     * Actualiza la parte del gen actual
     * en la posicion index a partir de esa parte del gen en el argumento
     *
     * Se usa en el operador cruce
     */
    public void fillFromGen(int index, Gen<GT> gen2) {
        this.set(index, gen2.get(index));
    }
}