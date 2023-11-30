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
public class Lista {
    private Nodo cabecera;
    private int longitud;
    
    public Lista (){
        cabecera = null;
        longitud = 0;
    }
   
    
    public boolean eliminar (int pos){
        boolean exito = true;
        if (pos < 1 || pos > longitud || longitud == 0){
            exito = false;
        }
        else{
            if (pos == 1){
                cabecera = cabecera.getEnlace();
            }
            else{
                Nodo auxiliar = cabecera;
                int i = 1;
                while (i < pos-1 ){
                    auxiliar = auxiliar.getEnlace();
                    i++;
                }
                auxiliar.setEnlace(auxiliar.getEnlace().getEnlace());
            }
            longitud--;
        }
        return exito;
    }
    public boolean insertar (Object elemento, int pos){
        boolean exito = true;
        if (pos < 1 || pos > longitud+1){
            exito = false;
        }
        else{
            if (pos == 1){
                cabecera = new Nodo (elemento,cabecera);
            }
            else{
                Nodo auxiliar = cabecera;
                int i = 1;
                while (i < pos-1){
                    auxiliar = auxiliar.getEnlace();
                    i++;
                }
                Nodo nuevo = new Nodo(elemento, auxiliar.getEnlace());
                auxiliar.setEnlace(nuevo);
            }
            longitud++;
        }
        return exito;
    }
    public Object recuperar (int pos){
        Object res;
        res = null;
        if (longitud > 0){
            if (pos >= 1 && pos <= longitud){
                if (pos == 1)
                    res = cabecera.getElemento();
                else{
                    Nodo auxiliar;
                    int i;
                    i = 1;
                    auxiliar = cabecera;
                    while (i < pos){
                        auxiliar = auxiliar.getEnlace();
                        i++;
                    }
                    res = auxiliar.getElemento();
                }   
            }
        }
        return res;
    }
    public int localizar (Object elemento){
        int res, i;
        Nodo auxiliar;
        boolean encontrado;
        encontrado = true;
        auxiliar = cabecera;
        i = 1;
        res = -1;
        while (auxiliar != null && encontrado){
            if (auxiliar.getElemento().equals(elemento)){
                encontrado = false;
                res = i;
            }
            auxiliar = auxiliar.getEnlace();
            i++;
            }
        return res;
    }
    public boolean esVacia (){
        return longitud == 0;
    }
    public void vaciar (){
        longitud = 0;
        cabecera = null;
    }
    public int longitud (){
        return longitud;
    }
    @Override
    public Lista clone (){
        Lista copia;
        copia = new Lista();
        if (this.longitud > 0){
            Nodo auxiliar1, auxiliar2;
            copia.longitud = this.longitud;
            auxiliar2 = new Nodo (this.cabecera.getElemento(), null);
            copia.cabecera = auxiliar2;
            auxiliar1 = this.cabecera.getEnlace();
            while (auxiliar1 != null){
                auxiliar2.setEnlace(new Nodo (auxiliar1.getElemento(),null));
                auxiliar2 = auxiliar2.getEnlace();
                auxiliar1 = auxiliar1.getEnlace();
            }
        }
        return copia;
    }

    public String toString (){
        String res;
        res = "";
        if (longitud == 0){
            res = "Lista vacia";
        }
        else{
            Nodo auxiliar;
            auxiliar = cabecera;
            while (auxiliar != null){
                res = res + auxiliar.getElemento().toString() + "\n";
                auxiliar = auxiliar.getEnlace();
            }
        }
        return res;
    }
    
    
    //Metodos de practica para el parcial
    public Lista obtenerMultiplos(int num){
        //Devolvera los elementos en posiciones multiplos de "num" en una nueva lista
        Lista res;
        Nodo auxiliar, auxiliar2;
        int i, j, n;
        res = new Lista();
        auxiliar2 = null;
        auxiliar = cabecera;
        n = longitud;
        j = 1;
        i = num;
        if (num >= 1){
            while(i <= n){
                //Recorremos la lista hasta encontrar la posicion que buscamos
                while (j < i){
                    auxiliar = auxiliar.getEnlace();
                    j++;
                }
                //Ahora lo insertamos en la lista de resultado
                if (auxiliar2 == null){
                    auxiliar2 = new Nodo(auxiliar.getElemento(),null);
                    res.cabecera = auxiliar2;
                    res.longitud++;
                }
                else{
                    auxiliar2.setEnlace(new Nodo(auxiliar.getElemento(),null));
                    res.longitud++;
                    auxiliar2 = auxiliar2.getEnlace();
                }
                i = i + num;
            }
        }
        return res;
    }
}
