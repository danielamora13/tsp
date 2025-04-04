package com.calculateTsp.tsp;

import lombok.Getter;

import java.util.PriorityQueue;

public class Busqueda {
    @Getter
    private Problema problema;
    private PriorityQueue<Nodo> frontera;

    public Busqueda(Problema problema) {
        this.problema = problema;
        frontera = new PriorityQueue<Nodo>();
    }

    public PriorityQueue<Nodo> getFrontera() {
        return frontera;
    }
}
