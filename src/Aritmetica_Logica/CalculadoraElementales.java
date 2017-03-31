package Aritmetica_Logica;

import javafx.scene.layout.GridPane;

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
                     matriz.get(i).set(j, resultado);
                 }
             }
         }
    }

    public void sumarFilas(ArrayList<ArrayList<Fraccion>> matriz, int fila1, int fila2, Fraccion escalar){
        ArrayList<Fraccion> filaPorEscalar = new ArrayList<>(matriz.get(fila2));

        for(int i = 0; i < filaPorEscalar.size(); i++){
            filaPorEscalar.set(i,Fraccion.multiplicar(filaPorEscalar.get(i),escalar));
        }

        Fraccion operando1Actual;
        Fraccion operando2Actual;
        for(int j = 0; j < matriz.get(fila1).size(); j++){
            operando1Actual = matriz.get(fila1).get(j);
            operando2Actual = filaPorEscalar.get(j);

            matriz.get(fila1).set(j,Fraccion.sumar(operando1Actual,operando2Actual));
        }
    }
}
