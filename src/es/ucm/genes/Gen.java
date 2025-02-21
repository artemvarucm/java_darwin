package es.ucm.genes;


public abstract class Gen<T> {
    // La T representa el tipo de elemento en el genotipo del gen (Double o Boolean)
    protected Integer tamGen;
    public Gen() {
    }

    public abstract Gen clone();

    /**
     * Devuelve el fenotipo del gen, ejemplo si es 11, devuelve 3
     */
    public abstract Double getFenotipo();

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
    protected abstract void set(int index, T value);

    /**
     * Devuelve el valor de la parte del gen
     * en la posicion index
     */
    protected abstract T get(int index);


    /**
     * Actualiza la parte del gen actual
     * en la posicion index a partir de esa parte del gen en el argumento
     *
     * Se usa en el operador cruce
     */
    public void fillFromGen(int index, Gen<T> gen2) {
        this.set(index, gen2.get(index));
    }
}