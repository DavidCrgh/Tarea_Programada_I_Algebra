package Aritmetica_Logica;

import java.util.ArrayList;

/**
 * Created by Pablo Brenes on 31 mar 2017.
 */
public class EcuacionMatriz {

    public static Fraccion[][] solucionarEcuacion(Fraccion[][] A, Fraccion[][] B, Fraccion[][] C){
        int orden = A.length;
        Fraccion[][] solucion = new Fraccion[orden][orden];
        solucion = restarMatrices(C, B);
        solucion = multiplicarMatrices(inversa(A), solucion);
        return solucion;
    }

    public static Fraccion determinante(Fraccion[][] matriz){
        if (matriz.length == 1){
            return matriz[0][0];
        }
        Double control;
        Fraccion sum = Fraccion.multiplicar(matriz[0][0], determinante(suprimirFilaColuma(matriz, 1, 1)));
        for (int i = 1; i < matriz.length; i++){
            control = Math.pow(-1, i+2);

            sum = Fraccion.sumar(sum, Fraccion.multiplicar(new Fraccion(control.longValue(), 1), Fraccion.multiplicar(matriz[0][i], determinante(suprimirFilaColuma(matriz, 1, i+1)))));
        }
        return sum;
    }

    public static Fraccion[][] restarMatrices (Fraccion[][] minuendo, Fraccion[][] sustraendo){
        int orden = minuendo.length;
        Fraccion[][] matrizResultante = new Fraccion[orden][orden];
        for (int i = 0; i < orden; i++) {
            for (int j = 0; j < orden; j++) {
                matrizResultante[i][j] = Fraccion.restar(minuendo[i][j], sustraendo[i][j]);
            }
        }
        return matrizResultante;
    }

    public static Fraccion[][] multiplicarMatrices (Fraccion[][] factorIzquierdo, Fraccion[][] factorDerecho){
        int orden = factorIzquierdo.length;
        Fraccion[][] matrizResultante = new Fraccion[orden][orden];
        for (int i = 0; i < orden; i++) {
            for (int j = 0; j < orden; j++) {
                matrizResultante [i][j] = Fraccion.multiplicar(factorIzquierdo[i][0], factorDerecho[0][j]);
                for (int k = 1; k < orden; k++) {
                    matrizResultante [i][j] = Fraccion.sumar(matrizResultante[i][j], Fraccion.multiplicar(factorIzquierdo[i][k], factorDerecho[k][j]));
                }
            }
        }
        return matrizResultante;
    }

    public static Fraccion[][] inversa (Fraccion[][] matriz){
        Fraccion determinante = determinante(matriz);
        determinante = Fraccion.invertir(determinante);
        Fraccion[][] matrizInversa = matrizAdjunta(matriz);
        matrizInversa = trasponer(matrizInversa);
        matrizInversa = multpilicarPorEscalar(matrizInversa, determinante);
        return matrizInversa;
    }

    public static Fraccion[][] multpilicarPorEscalar(Fraccion[][] matriz, Fraccion escalar){
        int orden = matriz.length;
        for (int i = 0; i < orden; i++) {
            for (int j = 0; j < orden; j++) {
                matriz[i][j] = Fraccion.multiplicar(matriz[i][j], escalar);
            }
        }
        return matriz;
    }

    public static Fraccion[][] matrizAdjunta(Fraccion[][] matriz){
        int orden = matriz.length;
        Fraccion[][] matrizAdjunta = crearBaseCofactores(orden);
        for (int i = 0; i < orden; i++) {
            for (int j = 0; j < orden; j++) {
                matrizAdjunta[i][j] = Fraccion.multiplicar(matrizAdjunta[i][j], determinante(suprimirFilaColuma(matriz, i+1, j+1)));
            }
        }
        return matrizAdjunta;
    }

    public static Fraccion[][] trasponer(Fraccion[][] matriz){
        int orden = matriz.length;
        Fraccion[][] matrizTraspuesta = new Fraccion[orden][orden];
        for (int i = 0; i < orden; i++) {
            for (int j = 0; j < orden; j++) {
                matrizTraspuesta[i][j] = matriz[j][i];
            }
        }
        return matrizTraspuesta;
    }

    public static Fraccion[][] crearBaseCofactores(int orden){
        /* Determina donde irá un negativo o un positivo
           Crea la siguiente matriz         Crea la siguiente matriz
                en caso 3x3                         en caso 2x2
            1   -1    1                         1   -1
           -1    1   -1                        -1    1
            1   -1    1
        */
        Fraccion[][] matriz = new Fraccion[orden][orden];
        for (int i = 0; i < orden; i++){
            for (int j = 0; j < orden; j++) {
                if (((i + 1) + (j + 1)) % 2 == 0){
                    matriz[i][j] = new Fraccion(1);
                }
                else{
                    matriz[i][j] = new Fraccion(-1);
                }
            }
        }
        return matriz;
    }

    public static Fraccion[][] suprimirFilaColuma (Fraccion[][] matriz, int fila, int columna){
        int ordenMatriz = matriz.length - 1;
        Fraccion [][] matrizResultante = new Fraccion[ordenMatriz][ordenMatriz];
        int i2 = 0;
        int j2 = 0;
        for (int i = 0; i < ordenMatriz; i++){
            if (i + 1 == fila)
                i2++;
            for (int j = 0; j < ordenMatriz; j++){
                if (j + 1 == columna)
                    j2++;
                matrizResultante[i][j] = matriz[i2][j2];
                j2++;
            }
            i2++;
            j2 = 0;
        }
        return matrizResultante;
    }

    public static  Fraccion[][] pasarArray(ArrayList<ArrayList<Fraccion>> array){
        Fraccion[][] matrizCB = new Fraccion[array.size()][array.size()] ;
        for (int i = 0; i<array.size();i++){
            for (int j = 0; j<array.get(i).size();j++){
                matrizCB[i][j] = array.get(i).get(j);
            }
        }
        return matrizCB;
    }

    public static  ArrayList<ArrayList<Fraccion>> pasarVector(Fraccion[][] matrizCB){
        ArrayList<ArrayList<Fraccion>> array = new ArrayList<>() ;
        for (int i = 0; i<matrizCB.length;i++){
            ArrayList<Fraccion> filaActual = new ArrayList<>();
            for (int j = 0; j<matrizCB.length;j++){
                filaActual.add(matrizCB[i][j]);
            }
            array.add(filaActual);
        }
        return array;
    }
}
