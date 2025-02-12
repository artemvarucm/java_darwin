package es.ucm;


public abstract class Gen<T> {
    // La T representa el fenotipo
    public Gen() {
    }

    /**
     * Devuelve el fenotipo del gen, ejemplo si es 11, devuelve 3
     */
    protected abstract T getFenotipo();

    protected abstract int getTamGen();
}