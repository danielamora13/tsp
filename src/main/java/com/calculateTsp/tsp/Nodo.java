package com.calculateTsp.tsp;

import lombok.Getter;

import java.util.ArrayList;

public class Nodo implements Comparable<Nodo> {
    private Estado estado;
    private double g = 0.0;
    private double h = 0.0;
    private double f = g + h;

    public Nodo (Estado estado) {
        this.estado = estado;
    }

    public Nodo (Estado estado, Problema problema, Nodo padre, double coste, MinimumSpanningTree mst) {
        this.estado = estado;
        this.g = padre.g + coste;
        this.h = mst.setH(problema, estado);
        setF();
    }

    public Nodo (Estado estado, Nodo padre, double coste) {
        this.estado = estado;
        this.g = padre.g + coste;
        setF();
    }

    public Estado getEstado() {
        return estado;
    }

    public double getG() {
        return g;
    }
    public void setF() {
        f = g + h;
    }

    @Override
    public int compareTo(Nodo nodo) {
        return (int) Math.signum(this.f - nodo.f);
    }

    @Override
    public String toString(){
        return "[ estado: " + getEstado().toString() +
                " coste: " + getG() + "]";
    }

    @Override
    public boolean equals(Object otro){
        if(otro instanceof Nodo) {
            return getEstado().equals(((Nodo)otro).getEstado());
        }
        return false;
    }

    @Override
    public int hashCode(){
        return getEstado().hashCode();
    }

}
