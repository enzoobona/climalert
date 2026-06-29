package ar.edu.utn.ba.ddsi.climalert.utils;

public class GeneradorDeIds {

    private long siguiente;

    public GeneradorDeIds() {
        this(1L);
    }

    public GeneradorDeIds(long valorInicial) {
        this.siguiente = valorInicial;
    }

    public long siguiente() {
        return siguiente++;
    }
}