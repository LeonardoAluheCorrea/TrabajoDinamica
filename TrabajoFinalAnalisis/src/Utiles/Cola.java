/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utiles;

/**
 *
 * @author Leo
 */
public class Cola {
    private Nodo frente;
    private Nodo fin;
    
    public Cola (){
        frente = null;
        fin = null;
    }
    public boolean poner (Object nuevoElemento){
        Nodo nuevo;
        nuevo = new Nodo(nuevoElemento, null);
            if (frente == null){
                frente = nuevo;
            }
            else{
                fin.setEnlace(nuevo);
            }
            fin = nuevo;
        return true;
    }
    public boolean sacar (){
        boolean exito;
        if (frente == null)
            exito = false;
        else{
            frente = frente.getEnlace();
            if (frente == null)
                fin = null;
            exito = true;
        }
        return exito;
    }
    public Object obtenerFrente(){
        Object res;
        res = null;
        if (frente != null)
            res = frente.getElemento();
        return res;
    }
    public boolean esVacia(){
        return frente == null;
    }
    public void vaciar (){
        frente = null;
        fin = null;
    }
    public Cola clone (){
        Nodo auxiliar1, auxiliar2;
        Cola copia;
        copia = new Cola();
        if (frente != null){
            auxiliar1 = frente.getEnlace();
            auxiliar2 = new Nodo (frente.getElemento(), frente.getEnlace());
            copia.frente = auxiliar2;
            while (auxiliar1 != null){               
                auxiliar2.setEnlace(new Nodo(auxiliar1.getElemento(), auxiliar1.getEnlace()));
                auxiliar1 = auxiliar1.getEnlace();
                auxiliar2 = auxiliar2.getEnlace();
            }
            copia.fin = auxiliar2;
        }
        return copia;
    }
    public String toString (){
        Nodo auxiliar;
        String result;
        result = "";
        if (frente == null)
            result = "Cola vacia";
        else{
            auxiliar = frente;
            while (auxiliar != null){
                result = result + auxiliar.getElemento().toString() + ",";
                auxiliar = auxiliar.getEnlace();
            }
        }
        return result;
    }
}
