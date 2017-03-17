package Aritmetica_Logica;

import java.util.ArrayList;

/**
 * Creado por David Valverde Garro - 2016034774
 * el 16-Mar-17.
 */
public class CalculadoraElementales {
    public void intercambiarFilas(ArrayList<ArrayList<Fraccion>> matriz, int fila1, int fila2){
        ArrayList<Fraccion> buffer = matriz.get(fila1);
        matriz.set(fila1,matriz.get(fila2));
        matriz.set(fila2,buffer);
    }

    public void multiplicarFila(ArrayList<ArrayList<Fraccion>> matriz, int fila, Fraccion escalar){
         for(int i = 0; i < matriz.size(); i++){
             if (i == fila) {
                 for(int j = 0; j < matriz.get(i).size(); j++){
                     Fraccion resultado = Fraccion.multiplicar(matriz.get(i).get(j),escalar);
                     matriz.get(i).set(i, resultado);
                 }
             }
         }
    }

    //public void
}
