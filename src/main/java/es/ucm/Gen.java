package es.ucm;


public abstract class Gen<T> {
    // La T representa el tipo de elemento en el genotipo del gen (Double o Boolean)
    protected Integer tamGen;
    public Gen() {
    }

    /**
     * Devuelve el fenotipo del gen, ejemplo si es 11, devuelve 3
     */
    protected abstract Double getFenotipo();

    protected int getTamGen() {
        return this.tamGen;
    }

    public abstract void printGenotipo();
    protected abstract void set(int index, T value);
    protected abstract T get(int index);
    public void updateGen(int index, Gen<T> gen2) {
        this.set(index, gen2.get(index));
    }
}