package com.calculateTsp.tsp;

import java.util.ArrayList;
import java.util.Random;

public class MinimumSpanningTree {

    public double calcularMst(double[][] distancias) {
        int vertices = distancias.length;
        ArrayList<Arista> arbol = new ArrayList<Arista>();
        boolean[] visitados = new boolean[vertices];
        double h = 0.0;

        int verticeOrigen = randomVertice(vertices);
        visitados[verticeOrigen] = true;
        double minDistancia;
        Arista arista;

        //recorremos las filas
        for (int i = 0; i < vertices - 1; i++) {
            minDistancia = Integer.MAX_VALUE;
            arista = null;
            //recorremos las columnas
            for (int j = 0; j < vertices; j++) {
                if (visitados[j]) {
                    Arista nuevaArista = calcularDistanciaMinima(j, distancias, visitados);
                    if (nuevaArista.getPeso() < minDistancia) {
                        minDistancia = nuevaArista.getPeso();
                        arista = nuevaArista;
                    }

                }
            }
            visitados[arista.getDestino()] = true;
            arbol.add(arista);
            h += arista.getPeso();
        }
        //printMST(arbol);
        return h;
    }

    private Arista calcularDistanciaMinima(int origen, double[][] grafo, boolean[] visitados) {
        double minDistancia = Integer.MAX_VALUE;
        Arista arista = new Arista();
        int destino = -1;

        for (int i = 0; i < grafo.length; i++) {
            if (!visitados[i] && grafo[origen][i] < minDistancia) {
                minDistancia = grafo[origen][i];
                destino = i;
            }
        }
        if (destino == -1) {
            return null;
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

    private void printMST(ArrayList<Arista> arbol) {
        System.out.println("Edge \tWeight");
        for (Arista arista: arbol) {
            System.out.println(arista.getOrigen() + " - " + arista.getDestino() + "\t" + arista.getPeso());
        }
    }
}
