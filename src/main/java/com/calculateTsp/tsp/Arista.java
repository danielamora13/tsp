package com.calculateTsp.tsp;

public class Arista implements Comparable<Arista> {
    private int origen;
    private int destino;
    private double peso;

    public Arista() {
    }

    public void setOrigen(int origen) {
        this.origen = origen;
    }

    public void setDestino(int destino) {
        this.destino = destino;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public int getOrigen() {
        return origen;
    }

    public int getDestino() {
        return destino;
    }

    public double getPeso() {
        return peso;
    }

    @Override
    public int compareTo(Arista arista) {
        if (peso < arista.peso) {
            return -1;
        } else if (arista.peso < peso) {
            return 1;
        }
        return 0;
    }


}
