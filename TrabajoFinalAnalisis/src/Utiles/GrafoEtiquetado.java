/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utiles;

import lineales.dinamicas.Cola;
import lineales.dinamicas.Lista;

/**
 *
 * @author Leo
 */
public class GrafoEtiquetado {
    private NodoVert inicio;
    
    public GrafoEtiquetado() {
        inicio = null;
    }  
    
    public boolean tieneArco(Object elemento){
        //Nos indica si el nodo que contiene a elemento esta conectado a algun arco
        boolean tiene;
        NodoVert aux;
        tiene = false;
        aux = ubicarVertice(elemento);
        if (aux != null){
            tiene = aux.getPrimerAdy() != null;
        }
        return tiene;
    }
    
    public int anchuraDesde(Object inicial, Object destino){
        Cola q;
        Lista visitados;
        NodoVert aux;
        NodoAdy auxAdy;
        int longitud, distancia;
        visitados = new Lista();
        q = new Cola();
        inicial = ubicarVertice(inicial);
        destino = ubicarVertice(destino);
        distancia = 0;
        if (inicial != null){
            visitados.insertar(inicial, visitados.longitud() + 1);
            q.poner(inicial);
            while (!q.esVacia()){
                longitud = visitados.longitud();
                aux = (NodoVert) q.obtenerFrente();
                q.sacar();
                //Obtenemos un vertice de la cola
                auxAdy = aux.getPrimerAdy();
                //Usamos un while para obtener todos sus adyacentes
                while (auxAdy != null){
                    aux = auxAdy.getVertice();
                    //Si el adyacente no esta en la lista, lo cargamos
                    if (visitados.localizar(aux) == -1){
                        visitados.insertar(aux, visitados.longitud() + 1);
                        q.poner(aux);
                    }
                    //Si el adyacente es el nodo destino entonces terminamos
                    if (aux.equals(destino)){
                        auxAdy = null;
                        q.vaciar();
                    }
                    else{
                        auxAdy = auxAdy.getSigAdyacente();
                    }
                }
                if (longitud < visitados.longitud()){
                    distancia++;
                }
            }
         }
        return distancia;
    }
    
    public Lista caminoMasCorto(Object origen, Object destino){
        //Encuentra el camino con menos arcos entre el origen y el destino
        NodoVert ori, dest;
        Lista camino;
        camino = new Lista();
        ori = ubicarVertice(origen);
        dest = ubicarVertice(destino);
        camino = caminoMasCortoAux(ori, dest, new Lista(), camino);
        return camino;
    }
    
    private Lista caminoMasCortoAux(NodoVert n, NodoVert destino, Lista caminoActual, Lista caminoMasCorto){
        if (n != null){
            //Visitamos el nodo n
            caminoActual.insertar(n, caminoActual.longitud() + 1);
            //Nos fijamos si n es el destino
            if (n.equals(destino)){
                //Si lo es entonces preguntamos la longitud del camino mas corto
                if (caminoMasCorto.longitud() == 0){
                    caminoMasCorto = caminoActual.clone();
                }
                else{
                    //Retornamos la lista de menor longitud
                    if (caminoActual.longitud() < caminoMasCorto.longitud()){
                        caminoMasCorto = caminoActual.clone();
                    }
                }            
            }
            else{
                //Si no lo es entonces llamamos recursivamente para sus adyacentes no visitados
                NodoAdy auxAdy;
                auxAdy = n.getPrimerAdy();
                while (auxAdy != null){
                    if (caminoActual.localizar(auxAdy.getVertice()) == -1){
                        caminoMasCorto = caminoMasCortoAux(auxAdy.getVertice(), destino, caminoActual, caminoMasCorto);
                        caminoActual.eliminar(caminoActual.longitud());
                    }
                    auxAdy = auxAdy.getSigAdyacente();
                }
            }
        }
        return caminoMasCorto;
    }
    
    public Lista caminoMasRapido(Object origen, Object destino){
        //Encuentra el camino con arcos menos costosos entre el origen y el destino
        NodoVert ori, dest;
        Lista camino;
        int[] c = new int[2];
        camino = new Lista();
        ori = ubicarVertice(origen);
        dest = ubicarVertice(destino);
        camino = caminoMasRapidoAux(ori, dest, new Lista(), camino, c);
        return camino;
    }
    
    private Lista caminoMasRapidoAux(NodoVert n, NodoVert destino, Lista caminoActual, Lista caminoMasCorto, int[] costo){
        if (n != null){
            //Visitamos el nodo n
            caminoActual.insertar(n, caminoActual.longitud() + 1);
            //Nos fijamos si n es el destino
            if (n.equals(destino)){
                if(costo[1] < 1){
                    caminoMasCorto = caminoActual.clone();
                    costo[1] = costo[0];
                }
                    if (costo[0] < costo[1]){
                        caminoMasCorto = caminoActual.clone();
                        costo[1] = costo[0];
                    }            
            }
            else{
                //Si no lo es entonces llamamos recursivamente para sus adyacentes no visitados
                NodoAdy auxAdy;
                auxAdy = n.getPrimerAdy();
                while (auxAdy != null){
                    if (caminoActual.localizar(auxAdy.getVertice()) == -1){
                        costo [0] = costo[0] + (int) auxAdy.getEtiqueta();
                        caminoMasCorto = caminoMasRapidoAux(auxAdy.getVertice(), destino, caminoActual, caminoMasCorto, costo);
                        caminoActual.eliminar(caminoActual.longitud());
                        costo [0] = costo[0] - (int) auxAdy.getEtiqueta();
                    }
                    auxAdy = auxAdy.getSigAdyacente();
                }
            }
        }
        return caminoMasCorto;
    }
    
    
     public int costoCaminoMasRapido(Object origen, Object destino){
        NodoVert ori, dest;
        Lista camino;
        int[] costo = new int[2];
        camino = new Lista();
        ori = ubicarVertice(origen);
        dest = ubicarVertice(destino);
        caminoMasRapidoAux(ori, dest, new Lista(), camino, costo); // El algoritmo implementado guarda el costo del camino mas corto en un arreglo
        return costo[1]; //Retornamos el costo del camino mas corto obtenido por caminoMasRapidoAux
    }
    
    
    public boolean existeVertice(Object buscado){
        //Verifica si existe un vertice con el elemento buscado
        //Llamamos a ubicarVertice, si nos devuelve un null es porque el elemento no existe
        return ubicarVertice(buscado) != null;
    }
    
    public Object obtenerElemento(Object buscado){
        //Devuelve el objeto que tenga la misma clave que buscado
        //Usado en el trabajo final para encontrar al aeropuerto que tenga la misma clave que buscado
        NodoVert aux; 
        Object res;
        res = null;
        aux = ubicarVertice(buscado);
        if (aux != null){
            res = aux.getElemento();
        }
        return res;
    }
     
    public boolean insertarVertice(Object nuevo){
        boolean exito;
        NodoVert aux;
        exito = false;
        //Nos fijamos si el elemento a insertar esta en el grafo
        aux = ubicarVertice(nuevo);
        if (aux == null){
            inicio = new NodoVert(nuevo,inicio,null);
            exito = true;
        }
        return exito;
    }
    
    public boolean insertarArco(Object origen, Object destino, Object etiqueta, boolean esArcoDoble){
        boolean exito, continuar, igualOrigen, igualDestino;
        NodoVert auxA, auxB;
        auxA = inicio;
        auxB = inicio;
        continuar = false;
        exito = false;
        //Buscamos los nodos de origen y destino del arco
        while (auxA != null && auxB != null && !continuar){
            igualOrigen = auxA.getElemento().equals(origen);
            if (!igualOrigen){
                auxA = auxA.getSigVertice();
            }
            igualDestino = auxB.getElemento().equals(destino);
            if (!igualDestino){
                auxB = auxB.getSigVertice();
            }
            continuar = igualOrigen && igualDestino;
        }
        //Si ambos nodos estan en el grafo entonces añadimos el arco
        if (continuar){
            NodoAdy nuevoOrigen, nuevoDestino;
            nuevoOrigen = auxA.getPrimerAdy();
            //Primero creamos el arco desde el origen al destino
            if (nuevoOrigen == null){
                nuevoOrigen = new NodoAdy(auxB,null,etiqueta);
                auxA.setPrimerAdy(nuevoOrigen);
            }
            else{
                auxA.setPrimerAdy(new NodoAdy(auxB,nuevoOrigen,etiqueta));
                /*while (nuevoOrigen.getSigAdyacente() != null){
                    nuevoOrigen = nuevoOrigen.getSigAdyacente();
                }
                nuevoOrigen.setSigAdyacente(new NodoAdy(auxB,null,etiqueta));
                */
            }
            //Ahora creamos el arco desde el destino al origen, pero solo si origen y destino son distintos vertices y si el parametro "esDoble" es T
            if (esArcoDoble && !origen.equals(destino)){
                nuevoDestino = auxB.getPrimerAdy();
                if (nuevoDestino == null){
                    nuevoDestino = new NodoAdy(auxA,null,etiqueta);
                    auxB.setPrimerAdy(nuevoDestino);
                }
                else{
                    auxB.setPrimerAdy(new NodoAdy(auxA,nuevoDestino,etiqueta));
                    /*while (nuevoDestino.getSigAdyacente() != null){
                        nuevoDestino = nuevoDestino.getSigAdyacente();
                    }
                    nuevoDestino.setSigAdyacente(new NodoAdy(auxA,null,etiqueta));
                    */
                }
            }
            exito = true;
        }
        return exito;
    }
    
    public boolean eliminarArco(Object origen, Object destino, Object etiqueta, boolean esArcoDoble){
        boolean exito;
        NodoVert aux;
        aux = inicio;
        exito = false;
        //Buscamos el nodo origen del arco
        aux = ubicarVertice(origen);
        //Si existe el nodo origen entonces lo usamos para buscar el destino y eliminar el enlace
        if (aux != null){
            NodoAdy auxAdyA, auxAdyB;
            auxAdyA = aux.getPrimerAdy();
            //Caso especial: el enlace esta en el primer adyacente
            if (auxAdyA.getVertice().getElemento().equals(destino) && auxAdyA.getEtiqueta().equals(etiqueta)){
                aux.setPrimerAdy(auxAdyA.getSigAdyacente());
                aux = auxAdyA.getVertice();
            }
            else{
                //Caso general: el enlace no esta en el primer adyacente
                //Buscamos el adyacente que contiene el enlace al destino con la etiqueta pedida
                auxAdyB = aux.getPrimerAdy();
                while (auxAdyA != null && !(auxAdyA.getVertice().getElemento().equals(destino) && auxAdyA.getEtiqueta().equals(etiqueta))){
                    auxAdyB = auxAdyA;
                    auxAdyA = auxAdyA.getSigAdyacente();
                }
                //Si el arco existe entonces lo eliminamos
                if (auxAdyA != null){
                    aux = auxAdyA.getVertice();
                    auxAdyB.setSigAdyacente(auxAdyA.getSigAdyacente());
                }
            }
            //Repetimos el proceso para el nodo destino si el parametro "esArcoDoble" es true
            if (esArcoDoble) {
                auxAdyA = aux.getPrimerAdy();
                if (auxAdyA.getVertice().getElemento().equals(origen)) {
                    aux.setPrimerAdy(auxAdyA.getSigAdyacente());
                } else {
                    auxAdyB = aux.getPrimerAdy();
                    while (auxAdyA != null && !(auxAdyA.getVertice().getElemento().equals(origen) && auxAdyA.getEtiqueta().equals(etiqueta))) {
                        auxAdyB = auxAdyA;
                        auxAdyA = auxAdyA.getSigAdyacente();
                    }
                    if (auxAdyA != null) {
                        auxAdyB.setSigAdyacente(auxAdyA.getSigAdyacente());
                    }
                }
            }
            exito = true;
        }
        return exito;
    }
    
    public boolean eliminarVertice(Object buscado){
        boolean exito;
        NodoVert auxA, auxB;
        auxA = inicio;
        auxB = auxA;
        exito = false;
        //Caso especial: eliminamos el inicio
        if (inicio != null){
            if (auxA.getElemento() != null && auxA.getElemento().equals(buscado)){
                inicio = auxA.getSigVertice();
            }
            else{
                //Caso general: Debemos recorer los vertices para encontrar el buscado
                while (auxA != null && !auxA.getElemento().equals(buscado)){
                    auxB = auxA;
                    auxA = auxA.getSigVertice();
                }
                //Si existe el vertice buscado lo desconectamos
                if (auxA != null){
                    auxB.setSigVertice(auxA.getSigVertice());
                }
            }
            if (auxA != null){
                //Ahora debemos borrar los arcos que se dirigen al vertice desconectado
                NodoAdy auxAdyA, auxAdyB, auxAdyC;
                NodoVert eliminado;
                eliminado = auxA; 
                auxAdyA = eliminado.getPrimerAdy();
                //Usamos un while externo para recorrer los nodos ady del vertice eliminado
                while (auxAdyA != null){
                    auxA = auxAdyA.getVertice();
                    auxAdyB = auxA.getPrimerAdy();
                    //Recorremos los nodos ady del vertice conectado al vertice eliminado y eliminamos los enlaces correspondientes
                    //Caso especial: el primer ady debe ser eliminado
                    if (auxAdyB.getVertice().equals(eliminado)){
                        auxA.setPrimerAdy(auxAdyB.getSigAdyacente());
                    }
                    //Caso general: Se debe recorrer los nodos ady para encontrar el que debemos eliminar
                    auxAdyC = auxAdyB;
                    while (!auxAdyB.getVertice().equals(eliminado)){
                        auxAdyC = auxAdyB;
                        auxAdyB = auxAdyB.getSigAdyacente();
                    }
                    //Una ver encontrado el enlace lo eliminamos
                    auxAdyC.setSigAdyacente(auxAdyB.getSigAdyacente());
                    //Pasamos el proximo enlace del vertice eliminado
                    auxAdyA = auxAdyA.getSigAdyacente();
                }
                exito = true;
            }
        }
        return exito;
    }
    
    private NodoVert ubicarVertice(Object buscado){
        NodoVert aux;
        aux = inicio;
        //Recorremos los vertices en busca de "buscado"
        while (aux != null && !aux.getElemento().equals(buscado)){
            aux = aux.getSigVertice();
        }
        //Devolvemos el vertice que contiene a "buscado"
        //Puede ser null
        return aux;
    }    
    
    @Override
    public String toString(){
        String grafo;
        NodoVert aux;
        NodoAdy auxAdy;
        aux = inicio;
        grafo = "";
        //Usamos una estructura externa para recorrer la lista de vertices
        while (aux != null){
            grafo = grafo + "\n" + "Nodo: " + aux.getElemento().toString() + "  enlazado a: ";
            //Usamos una estructura interna para recorrer las listas de nodos ady
            auxAdy = aux.getPrimerAdy();
            while (auxAdy != null){
                grafo = grafo + auxAdy.getVertice().getElemento().toString() + " con etiqueta: " + auxAdy.getEtiqueta() + "      , ";
                auxAdy = auxAdy.getSigAdyacente();
            }
            aux = aux.getSigVertice();
        }
        return grafo;
    } 
}
