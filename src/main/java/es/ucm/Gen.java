package es.ucm;


public abstract class Gen {
    // La T representa el fenotipo
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
}