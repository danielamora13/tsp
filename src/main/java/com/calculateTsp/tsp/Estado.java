package com.calculateTsp.tsp;

import java.util.ArrayList;

public class Estado {

    private ArrayList<Ciudad> ciudadesVisitadas = new ArrayList<Ciudad>();

    public Estado() {
    }

    public Estado(Estado estado, Ciudad ciudad) {
        ciudadesVisitadas.addAll(estado.getCiudadesVisitadas());
        ciudadesVisitadas.add(ciudad);
    }

    public ArrayList<Ciudad> getCiudadesVisitadas() {
        return ciudadesVisitadas;
    }

    public int getCiudadActual() {
        int numCiudadesVisitadas = getCiudadesVisitadas().size();
        return getCiudadesVisitadas().get(numCiudadesVisitadas - 1).getId();

    }

    public ArrayList<Integer> getIdCiudades() {
        ArrayList<Integer> ciudades = new ArrayList<Integer>();
        for ( Ciudad ciudad: ciudadesVisitadas ) {
            ciudades.add( ciudad.getId() );
        }
        return ciudades;
    }

    @Override
    public String toString() {
        if (ciudadesVisitadas.size() == 0) {
            return "()";
        }
        String estado = "(";
        int j = ciudadesVisitadas.size();
        for (int i = 0; i < j - 1; i++) {
            estado += ciudadesVisitadas.get(i).getId() + ", ";
        }
        estado += ciudadesVisitadas.get(j - 1).getId() + ")";
        return estado;
    }
}
