package com.calculateTsp.tsp;

import java.util.ArrayList;
import java.util.Random;

public class MinimumSpanningTree {

    double[][] distancias;
    ArrayList<Integer> ciudadesVisitadas;

    public MinimumSpanningTree(int numCiudades) {
        distancias = new double[numCiudades][numCiudades];
    }

    public double setH(Problema problema, Estado estado) {
        ciudadesVisitadas = estado.getIdCiudades();
        int ciudadActual = estado.getCiudadActual();

        int numCiudadesTotales = problema.getNumCiudades();
        int numCiudades = numCiudadesTotales - ciudadesVisitadas.size() + 1; // +1 para aniadir la ciudad actual
        int indice1 = 0;
        int indice2;

        for (int i = 0; i < numCiudadesTotales; i++) {
            indice2 = 0;

            if (!ciudadesVisitadas.contains(i) || i == ciudadActual) {
                for (int j = 0; j < numCiudadesTotales; j++) {

                    if (!ciudadesVisitadas.contains(j) || j == ciudadActual) {
                        distancias[indice1][indice2] = problema.getDistEntre(i, j);
                        indice2++;
                    }

                }
                indice1++;
            }
        }

        return calcularMst(distancias, numCiudades);
    }

    private double calcularMst(double[][] distancias, int numCiudades) {
        boolean[] visitados = new boolean[numCiudades];
        double h = 0.0;

        int verticeOrigen = randomVertice(numCiudades);
        visitados[verticeOrigen] = true;
        double minDistancia;
//        double peso;
//        int destino;
        Arista arista;

        //recorremos las filas
        for (int i = 0; i < numCiudades - 1; i++) {
            minDistancia = Integer.MAX_VALUE;
//            peso = 0.0;
//            destino = -1;
            arista = null;
            //recorremos las columnas
            for (int j = 0; j < numCiudades; j++) {
                if (visitados[j]) {
//                    double nuevoPeso = calcularDistanciaMinima(j, distancias, visitados);
                    Arista nuevaArista = calcularDistanciaMinima(j, distancias, visitados);
                    if (nuevaArista.getPeso() < minDistancia) {
                        minDistancia = nuevaArista.getPeso();
                        arista = nuevaArista;
                    }
//                    if (nuevoPeso < minDistancia) {
//                        minDistancia = nuevoPeso;
//                        peso = nuevoPeso;
//                        destino = j;
//                    }
                }
            }
            visitados[arista.getDestino()] = true;
            h += arista.getPeso();
//            visitados[destino] = true;
//            h += peso;
        }
        return h;
    }

    private Arista calcularDistanciaMinima(int origen, double[][] grafo, boolean[] visitados) {
        double minDistancia = Integer.MAX_VALUE;
        Arista arista = new Arista();
        int destino = -1;

        for (int i = 0; i < visitados.length; i++) {
            if (!visitados[i] && grafo[origen][i] < minDistancia) {
                minDistancia = grafo[origen][i];
                destino = i;
            }
        }

        arista.setOrigen(origen);
        arista.setDestino(destino);
        arista.setPeso(minDistancia);
        return arista;
    }

    /** Metodo que elige un vertice aleatoriamente en un grafo
     * @param vertices numero de vertices del grafo
     * @return vertice escogido
     */
    public int randomVertice(int vertices) {
        Random r = new Random();
        int low = 0;
        int high = vertices;
        return r.nextInt(high-low) + low;
    }

}
