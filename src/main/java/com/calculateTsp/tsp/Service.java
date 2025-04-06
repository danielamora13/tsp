package com.calculateTsp.tsp;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.PriorityQueue;

@org.springframework.stereotype.Service
public class Service {

    private MinimumSpanningTree minimumSpanningTree;

    public Nodo busqueda(double[][] distancias) {
        Problema problema = new Problema(distancias);
        Busqueda busqueda = new Busqueda(problema);
        PriorityQueue<Nodo> frontera = busqueda.getFrontera();
        minimumSpanningTree = new MinimumSpanningTree(problema.getNumCiudades());

        Nodo actual = new Nodo(new Estado());
        frontera.add(actual);

        while (!frontera.isEmpty()) {
            actual = frontera.poll();

            if (problema.esMeta(actual.getEstado())) {
                System.out.println("Nodos en frontera: " + busqueda.getFrontera().size());
                return actual;
            }

            frontera = expandir(actual, problema, frontera);
        }

        return null;
    }
    public Nodo busquedaFile(String file) {
        Problema problema = new Problema(file);
        Busqueda busqueda = new Busqueda(problema);
        PriorityQueue<Nodo> frontera = busqueda.getFrontera();
        minimumSpanningTree = new MinimumSpanningTree(problema.getNumCiudades());

        Nodo actual = new Nodo(new Estado());
        frontera.add(actual);

        while (!frontera.isEmpty()) {
            actual = frontera.poll();

            if (problema.esMeta(actual.getEstado())) {
                System.out.println("Nodos en frontera: " + busqueda.getFrontera().size());
                return actual;
            }

            frontera = expandir(actual, problema, frontera);
        }

        return null;
    }

    private PriorityQueue<Nodo> expandir(Nodo actual, Problema problema, PriorityQueue<Nodo> frontera) {
        ArrayList<Integer> ciudadesVisitadas = actual.getEstado().getIdCiudades();
        int numCiudadesVisitadas = ciudadesVisitadas.size();
        int numCiudadesTotales = problema.getNumCiudades();
        int ciudadActual;

        // expandir el nodo origen
        if (numCiudadesVisitadas == 0) {
            for (int i = 1; i < numCiudadesTotales; i++) {
                Estado estado = new Estado(actual.getEstado(), new Ciudad(i));
                frontera.add(new Nodo(estado, problema,
                        actual, problema.getDistEntre(0, i), minimumSpanningTree));

            }

            // expandir un nodo en el que solo falta aniadir la ciudad origen 0
        } else if (numCiudadesVisitadas == numCiudadesTotales - 1) {
            ciudadActual = actual.getEstado().getCiudadActual();
            frontera.add(new Nodo(new Estado(actual.getEstado(), new Ciudad(0)),
                    actual, problema.getDistEntre(ciudadActual, 0)));

            // expandir un nodo en el que solo falta aniadir una ciudad  y el origen 0
        } else if (numCiudadesVisitadas == numCiudadesTotales - 2) {
            ciudadActual = actual.getEstado().getCiudadActual();
            int ciudadFinal = -1;
            int i = 1;
            boolean encontrada = false;
            while (!encontrada && i < numCiudadesTotales) {
                if (!ciudadesVisitadas.contains(i)) {
                    ciudadFinal = i;
                    encontrada = true;
                }
                i++;
            }
            Estado estado = new Estado(actual.getEstado(), new Ciudad(ciudadFinal));
            frontera.add(new Nodo(estado, problema,
                    actual, problema.getDistEntre(ciudadActual, ciudadFinal), minimumSpanningTree));

            // expandir cualquier otro estado intermedio
        } else {
            ciudadActual = actual.getEstado().getCiudadActual();
            for (int i = 1; i < numCiudadesTotales; i++) {

                if (!ciudadesVisitadas.contains(i)) {
                    Estado estado = new Estado(actual.getEstado(), new Ciudad(i));
                    frontera.add(new Nodo(estado, problema,
                            actual, problema.getDistEntre(ciudadActual, i), minimumSpanningTree));
                }
            }
        }
        return frontera;
    }

}
