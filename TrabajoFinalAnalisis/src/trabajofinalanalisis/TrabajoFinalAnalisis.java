/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package trabajofinalanalisis;

import Utiles.GrafoEtiquetado;
import Utiles.NodoVert;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author Leo
 */
public class TrabajoFinalAnalisis {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
        GrafoEtiquetado grafoDeFrobenius;
        int[] A = new int[100]; //Tener cuidado de acomodar el tamaño del arreglo a la cantidad de nros
        int maxCosto = -1;
        Scanner scan = new Scanner(new File(System.getProperty("user.dir") + "\\src\\trabajofinalanalisis\\nros.txt"));
        int c = 0;
        double tiempo;
        //Cargamos nuestro conjunto de nros coprimos A usando un archivo de texto
        while (scan.hasNext()){
            A[c] = scan.nextInt();
            c++;
        }
        scan.close();
        
        //Construimos el grafo circulante y lo mostramos
        grafoDeFrobenius = construirGrafoDeFrobenius(A);
        System.out.println(grafoDeFrobenius.toString());
        tiempo = System.currentTimeMillis();
        System.out.println("Tiempo de construir el grafo en ms: " + tiempo);
        
        //Primero buscamos los caminos menos costosos desde 0 hasta todos los otros vertices con Dijstra
        HashMap<NodoVert,Integer> caminosMasCortos = (HashMap) grafoDeFrobenius.dijkstra(0);
        for(HashMap.Entry entry: caminosMasCortos.entrySet()){
            maxCosto = maximo(maxCosto,(int)entry.getValue());
        }
        tiempo = System.currentTimeMillis() - tiempo;
        System.out.println("Tiempo usando Dijkstra en ms: " + tiempo);
        System.out.println("El nro de Frobenius es: " + (maxCosto - A[0]));
        
        //Ahora buscamos con el algoritmo propio
        maxCosto = -1;
        tiempo = System.currentTimeMillis();
        for (int i = 1; i < A[0]; i++){
            maxCosto = maximo(maxCosto,grafoDeFrobenius.costoCaminoMasRapido(0,i));
        }
        tiempo = System.currentTimeMillis() - tiempo;
        System.out.println("Tiempo usando el algoritmo propio en ms: " + tiempo);
        System.out.println("El nro de Frobenius es: " + (maxCosto - A[0]));
    }
    
    private static int maximo(int x, int y){
        int maximo = x;
        if (y > x)
            maximo = y;
        return maximo;
    }
    
    private static GrafoEtiquetado construirGrafoDeFrobenius(int[] A){
        GrafoEtiquetado grafoF = new GrafoEtiquetado();
        int a0 = A[0];
        int n = A.length;
        for (int i = 0; i < a0; i++){ //Primero insertamos todos los vertices
            grafoF.insertarVertice(i);
        }
        
        for (int i = 0; i < a0; i++){ //Ahora insertamos todos los arcos
            for (int j = 0; j < a0; j++){ //Recorremos todos vertices para ver si tenemos arcos i -> j
                for (int k = 1; k < n; k++){ //Recorremos A buscando el elemento ak para formar los arcos 
                    int ak = A[k];
                    if ((((i + A[k]) - j) % a0) == 0){ //Si se cumple esta condicion entonces hay un arco i -> j con peso ak
                        grafoF.insertarArco(i, j, ak, false);
                    }
                }
            }
        }
        return grafoF;
    }

}
