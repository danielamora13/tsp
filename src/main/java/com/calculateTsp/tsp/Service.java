package com.calculateTsp.tsp;

import org.apache.coyote.BadRequestException;
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

        Nodo actual = new Nodo(new ArrayList<>());
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

        Nodo actual = new Nodo(new ArrayList<>());
        frontera.add(actual);
//        int i = 0;

        try {
            while (!frontera.isEmpty()) {
                actual = frontera.poll();

                if (problema.esMeta(actual.getEstado())) {
                    System.out.println("Nodos en frontera: " + busqueda.getFrontera().size());
                    return actual;
                }

                expandir(actual, problema, frontera);
//                System.out.println("frontera size: " + frontera.size() + "en iteracion: " + i);
//                System.out.println(actual);
//                i ++;
            }
        } catch (OutOfMemoryError err) {
            System.out.println("en busqueda");
            return actual;
        }

        return null;
    }

    private PriorityQueue<Nodo> expandir(Nodo actual, Problema problema, PriorityQueue<Nodo> frontera) {
        ArrayList<Integer> ciudadesVisitadas = actual.getEstado();
        int numCiudadesVisitadas = ciudadesVisitadas.size();
        int numCiudadesTotales = problema.getNumCiudades();
        int ciudadActual;

        // expandir el nodo origen
        if (numCiudadesVisitadas == 0) {
            for (int i = 1; i < numCiudadesTotales; i++) {
                frontera.add(new Nodo(problema,
                        actual, i, problema.getDistEntre(0, i), minimumSpanningTree));

            }

            // expandir un nodo en el que solo falta aniadir la ciudad origen 0
        } else if (numCiudadesVisitadas == numCiudadesTotales - 1) {

            System.out.println("llega a numCiudadesTotales - 1");
            System.out.println(frontera.peek());
            try {
                ciudadActual = actual.getCiudadActual();
                frontera.add(new Nodo(actual, 0, problema.getDistEntre(ciudadActual, 0)));

            } catch (OutOfMemoryError err) {
                System.out.println("en service estado numCiudadesTotales - 1");
                System.out.println(frontera.size());
                throw new ArrayIndexOutOfBoundsException();
            }

            // expandir un nodo en el que solo falta aniadir una ciudad  y el origen 0
        } else if (numCiudadesVisitadas == numCiudadesTotales - 2) {
            System.out.println("llega a numCiudadesTotales - 2");
            System.out.println(frontera.peek());
            try {
                ciudadActual = actual.getCiudadActual();
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
                frontera.add(new Nodo(problema,
                        actual, ciudadFinal, problema.getDistEntre(ciudadActual, ciudadFinal), minimumSpanningTree));

            } catch (OutOfMemoryError err) {
                System.out.println("en service estado numCiudadesTotales - 2");
                System.out.println(frontera.size());
                throw new ArrayStoreException();
            }

            // expandir cualquier otro estado intermedio
        } else {
            try {
                ciudadActual = actual.getCiudadActual();
                for (int i = 1; i < numCiudadesTotales; i++) {
                    if (!ciudadesVisitadas.contains(i)) {
                        frontera.add(new Nodo(problema,
                                actual, i, problema.getDistEntre(ciudadActual, i), minimumSpanningTree));
                    }
                }
            } catch (OutOfMemoryError err) {

                System.out.println("en service estado intermedio, La frontera es ");
                return frontera;
            }
        }
        return frontera;
    }

}
