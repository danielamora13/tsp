package com.calculateTsp.tsp;

import java.util.ArrayList;

public class Nodo implements Comparable<Nodo> {
    private ArrayList<Integer> estado = new ArrayList<>();
    private double g = 0.0;
    private double h = 0.0;

    private double f = g + h;

    public Nodo (ArrayList<Integer> estado) {
        this.estado = estado;
    }

    public Nodo (Problema problema, Nodo padre, int nuevaCiudad, double coste, MinimumSpanningTree mst) {
        this.estado.addAll(padre.estado);
        this.estado.add(nuevaCiudad);
        this.g = padre.g + coste;
        this.h = mst.setH(problema, estado, nuevaCiudad);
        setF();
    }

    public Nodo (Nodo padre, int nuevaCiudad, double coste) {
        this.estado.addAll(padre.estado);
        this.estado.add(nuevaCiudad);
        this.g = padre.g + coste;
        setF();
    }

    public ArrayList<Integer> getEstado() {
        return estado;
    }

    public double getG() {
        return g;
    }
    public double getF() {
        return f;
    }
    public double getH() {
        return h;
    }

    public int getCiudadActual() {
        int numCiudadesVisitadas = estado.size();
        return estado.get(numCiudadesVisitadas - 1);

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
                " coste: " + getG() +
                " h: " + getH() +
                " f: " + getF() + "]";
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
