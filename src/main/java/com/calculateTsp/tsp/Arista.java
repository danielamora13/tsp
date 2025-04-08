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

    @Override
    public boolean equals(Object obj) {
        if ( this == obj ) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Arista arista = (Arista) obj;
        return origen == arista.origen && destino == arista.getDestino() && Double.compare(arista.peso, peso) == 0;
    }

    @Override
    public int hashCode() {
        int result = Integer.hashCode(origen);
        result = 31 * result + Integer.hashCode(destino);
        result = 31 * result + Double.hashCode(peso);
        return  result;
    }


}
