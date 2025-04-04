package com.calculateTsp.tsp;

import java.io.FileInputStream;
import java.util.Scanner;

public class Problema {
    private Ciudad[] ciudades; // ciudades a visitar (incluyendo ciudad 0, origen)
    private double[][] distancias; // distancias entre ciudades

    // CONSTRUCTORES
    public Problema( double[][] distancias ) {
        // CREAMOS LISTA DE CIUDADES
        int dimension = distancias.length;
        Ciudad[] ciudades = new Ciudad[dimension];
        for (int i = 0; i < dimension; i++) {
            ciudades[i] = new Ciudad(i);
        }
        setCiudades( ciudades );
        setDistancias( distancias );
    }

    public Problema( String nomFich ) {
        try {
            leeDeFichero( nomFich );
        }
        catch(Exception e) {
            System.out.println("Problemas con la lectura de fichero" + e );
        }
    }

    // OBSERVADORES Y MODIFICADORES
    public Ciudad[] getCiudades() {
        return ciudades;
    }

    public void setCiudades ( Ciudad[] vCiudades ) {
        int numCiudadesConOri = vCiudades.length;
        ciudades = new Ciudad[numCiudadesConOri];
        for( int i=0; i<numCiudadesConOri; i++ ) {
            ciudades[i]=vCiudades[i];
        }
    }

    public double[][] getDistancias(){
        return distancias;
    }

    public void setDistancias( double[][] dist ) {
        distancias = new double[dist.length][dist[0].length];
        for( int i=0; i<dist.length; i++)
            for( int j=0; j<dist[0].length; j++ )
                distancias[i][j]=dist[i][j];
    }

    public int getNumCiudades() {
        return ciudades.length;
    }

    public double getDistEntre(int i, int j) {
        if( 0<=i && i<= getNumCiudades() && 0<=j && j<=getNumCiudades() )
            return distancias[i][j];
        else
            return Integer.MAX_VALUE;
    }

    public void setDistEntre(int i, int j, double dist) {
        if( 0 <= i && i <= getNumCiudades() && 0 <= j && j <= getNumCiudades() )
            distancias[i][j] = dist;
    }

    public boolean esMeta(Estado estado) {
        if (estado.getCiudadesVisitadas().size() == ciudades.length) {
            return true;
        }
        return false;
    }

    /**
     * @return el origen del tour
     */

    // METODOS PRIVADOS (AUXILIARES)


    /** Metodo para leer de fichero el problema
     * El fichero sigue el formato de tsplib
     * (ver http://vrp.atd-lab.inf.puc-rio.br/attachments/article/6/TSPLIB%2095.pdf)
     * @param nomFich
     * @throws Exception
     */
    private void leeDeFichero( String nomFich ) throws Exception {
        int dimension = 0;
        try {
            //
            Scanner dis = new Scanner(new FileInputStream("C:/Users/danie/Documents/Grado Matemáticas/Cuarto/IntroSisInteligentes/Practicas/Workspace/calculateTsp/calculateTsp/src/main/java/com/tsp/calculateTsp/" + nomFich));
            String s="";
            // tiene que ser de tipo TSP
            while (dis.hasNext() && !s.contains("TSP")) {
                s = dis.next();
            }
            if (!s.contains("TSP")) {
                dis.close();
                throw new Exception("Mal los datos... no es un problema TSP");
            }
            // LEEMOS DIMENSION (numero de ciudades incluido el origen)
            while (dis.hasNext() && !s.contains("DIMENSION:")) {
                s = dis.next();
            }
            if (s.contains("DIMENSION:")) {
                dimension = dis.nextInt();
                System.out.println("leida dimension: " + dimension);
                s=dis.nextLine(); // terminamos la linea
            }
            else {
                dis.close();
                throw new Exception("Mal los datos... no hay dimension");
            }
            // CREAMOS LISTA DE CIUDADES
            Ciudad[] ciudades = new Ciudad [dimension];
            for (int i = 0; i < dimension; i++){
                ciudades[i] = new Ciudad(i);
            }
            setCiudades(ciudades);
            // Comprobamos que se dan las distancias explicitas
            dis.next();
            String tipo=dis.next();
            System.out.println(tipo);
            //Creamos matriz para guardar las distancias
            double[][] dist = new double[dimension][dimension];
            switch(tipo) {
                case "EXPLICIT":
                    // leemos el formato en que se dan las distancias
                    s=dis.next();
                    if(!s.equals("EDGE_WEIGHT_FORMAT:")) {
                        dis.close();
                        throw new Exception("Mal los datos ... no se da el formato de los pesos");
                    }
                    String formato=dis.next(); // formato de las distancias
                    dis.nextLine();// terminamos la linea
                    dis.nextLine();// leemos la siguiente linea "EDGE_WEIGHT_SECTION"
                    // LEEMOS DISTANCIAS

                    switch(formato) {
                        case "UPPER_ROW": // matriz triangular superior sin diagonal
                            for (int i = 0; i < dimension-1; i++) {
                                for (int j = i+1; j < dimension; j++) {
                                    double x = dis.nextDouble();
                                    dist[i][j] = dist[j][i] = x;
                                }
                            }

                            break;
                        case "LOWER_DIAG_ROW": // matriz diagonal inferior incluida diagonal
                            for (int i=0; i<dimension; i++){
                                for (int j=0; j<i+1; j++){
                                    double x = dis.nextDouble();
                                    dist[i][j]=dist[j][i]= x; // aniade distancia simetrica
                                }
                            }
                            break;
                        default:
                            dis.close();
                            throw new Exception("Mal los datos ... Formato no soportado");

                    }
                    break;

                case "EUC_2D":
                    // leemos el formato en que se dan las distancias
                    s=dis.next();

                    if(!s.equals("NODE_COORD_SECTION")) {
                        dis.close();
                        throw new Exception("Mal los datos ... no se dan las coordenadas");
                    }

                    double[][] coord = new double[dimension][2];
                    dis.nextLine();
                    s = dis.next();

                    double x;
                    int id = 0;

                    while (!s.equals("EOF")) {
                        System.out.println(s);

                        x = Double.parseDouble(dis.next());
                        System.out.println(x);
                        coord[id][0] = x;
                        x = Double.parseDouble(dis.next());
                        coord[id][1] = x;
                        id++;
                        dis.nextLine();
                        s = dis.next();
                    }

                    System.out.println(coord);
                    double y;
                    for (int i = 0; i < dimension-1; i++) {
                        for (int j = i+1; j < dimension; j++) {
                            y = Math.sqrt(Math.pow((coord[i][0] - coord[j][0]), 2) + Math.pow((coord[i][1] - coord[j][1]), 2));
                            dist[i][j] = dist[j][i] = y;
                        }
                    }

                    break;

                case "CEIL_2D":
                    // leemos el formato en que se dan las distancias
                    s=dis.next();
                    if(!s.equals("NODE_COORD_SECTION")) {
                        dis.close();
                        throw new Exception("Mal los datos ... no se dan las coordenadas");
                    }

                    double[][] coordCeil = new double[dimension][2];
                    s = dis.nextLine();
                    dis.nextDouble();
                    double xCeil;
                    int idCeil = 0;
                    while (!s.equals("EOF")) {
                        xCeil = dis.nextDouble();
                        coordCeil[idCeil][0] = xCeil;
                        xCeil = dis.nextDouble();
                        coordCeil[idCeil][1] = xCeil;
                        idCeil++;
                        dis.nextLine();
                        s = dis.next();
                    }

                    double yCeil;
                    for (int i = 0; i < dimension-1; i++) {
                        for (int j = i+1; j < dimension; j++) {
                            yCeil = Math.sqrt(Math.pow((coordCeil[i][0] - coordCeil[j][0]), 2) + Math.pow((coordCeil[i][1] - coordCeil[j][1]), 2));
                            dist[i][j] = dist[j][i] = Math.ceil(yCeil);
                        }
                    }

                    break;

                case "GEO":
                    // leemos el formato en que se dan las distancias
                    while (dis.hasNext() && !s.contains("NODE_COORD_SECTION")) {
                        s = dis.next();
                    }
//					if (s.contains("DIMENSION:")) {
//						dimension = dis.nextInt();
//						System.out.println("leida dimension: " + dimension);
//						s=dis.nextLine(); // terminamos la linea
//					}
//					else {
//						dis.close();
//						throw new Exception("Mal los datos... no hay dimension");
//					}
//					dis.nextLine();
//					s=dis.next();
                    System.out.println(s);
                    if(!s.equals("NODE_COORD_SECTION")) {
                        dis.close();
                        throw new Exception("Mal los datos ... no se dan las coordenadas");
                    }

                    double[][] coordGeo = new double[dimension][2];
                    s = dis.nextLine();
                    double si = Double.parseDouble(dis.next());
                    System.out.println(si);
                    double grados;
                    double minutos;
                    double xGeo;
                    double yGeo;
                    int idGeo = 0;
                    while (!s.equals("EOF")) {
                        System.out.println("antes");
                        yGeo = Double.parseDouble(dis.next());
                        System.out.println(yGeo);
                        grados = Math.floor(yGeo);
                        minutos = yGeo - grados;
                        xGeo = Math.PI * (grados + 5.0 * minutos / 3.0) / 180.0;
                        coordGeo[idGeo][0] = xGeo;

                        yGeo = Double.parseDouble(dis.next());
                        grados = Math.floor(yGeo);
                        minutos = yGeo - grados;
                        xGeo = Math.PI * (grados + 5.0 * minutos / 3.0) / 180.0;
                        coordGeo[idGeo][1] = xGeo;

                        idGeo++;
                        dis.nextLine();
                        s = dis.next();
                        System.out.println(s);
                    }

                    double r = 6378.388;
                    double q1;
                    double q2;
                    double q3;
                    double z;
                    for (int i = 0; i < dimension-1; i++) {
                        for (int j = i+1; j < dimension; j++) {
                            q1 = Math.cos(coordGeo[i][1] - coordGeo[j][1] );
                            q2 = Math.cos(coordGeo[i][0] - coordGeo[j][0] );
                            q3 = Math.cos(coordGeo[i][0] + coordGeo[j][0] );
                            z = r * Math.acos(0.5 *((1.0 + q1)*q2 - (1.0 - q1)*q3)) + 1.0;
                            dist[i][j] = dist[j][i] = z;
                        }
                    }
                    break;
                default:
                    dis.close();
                    throw new Exception("Mal los datos ... Tipo no soportado");

            }
            dis.close();
            setDistancias(dist);
        }
        catch(Exception e) {
            throw e;

        }
        String a = "";
        for (int i = 0; i < distancias.length; i++) {
            a = "";
            for (int j = 0; j < distancias.length; j++) {
                a = a + " " + distancias[i][j];
            }
            System.out.println(a);
        }
        System.out.println("La desviacion tipica es: " + calcularVarianza(distancias));

    }// fin metodo leer de fichero

    private String calcularVarianza(double[][] distancias) {
        // Media
        double media = 0.0;
        for (int i = 0; i < distancias.length; i++) {
            for (int j = 0; j < distancias.length; j++) {
                media += distancias[i][j];
            }
        }
        media /= Math.pow(distancias.length, 2);

        // Varianza
        double varianza = 0;
        for (int i = 0; i < distancias.length; i++) {
            for (int j = 0; j < distancias.length; j++) {
                varianza += Math.pow(distancias[i][j] - media, 2);
            }
        }
        varianza /= Math.pow(distancias.length, 2);

        // Desviación tipica
        double std = Math.sqrt(varianza);
        return String.valueOf(std);
    }
}
