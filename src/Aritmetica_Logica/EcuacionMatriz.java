package Aritmetica_Logica;


import static Aritmetica_Logica.Fraccion.multiplicar;

/**
 * Created by Toño_PC on 28/3/2017.
 */
public class EcuacionMatriz {
    public static Fraccion[][] ecuacionMatriz(Fraccion[][] A,Fraccion[][] B,Fraccion[][] C){
        /*
        * Funcion ecuacionMatriz
        * Resuelve una ecuacion del tipo AX + B = C
        * Si no hay B se soluciona con entrar una matriz de ceros
        * Todas las matrices deben ser del tipo nxn
        */
        Fraccion[][] matrizCB = new Fraccion[B.length][B.length] ;
        for(int i=0;i<B.length;i++){
            for(int j=0;j<B.length;j++){
                matrizCB[i][j]=Fraccion.restar(C[i][j],B[i][j]);
            }
        }
        //System.out.println("Matriz Inversa");
        //System.out.print(Arrays.deepToString(matrizInversa(A)));
        return multiplicarMatrices(matrizInversa(A), matrizCB);
    }
    public static Fraccion[][] multiplicarMatrices(Fraccion[][] A, Fraccion[][] B){
        Fraccion[][] matRes = new Fraccion[A.length][B.length];
        for ( int i = 0; i < A.length; i++){
            for ( int j = 0; j < B.length; j++){
                for ( int k = 0; k < A.length; k++ ) { //puede ser columnasA o filasB ya que deben ser iguales
                    if (matRes[i][j] == null) {
                        matRes[i][j] = multiplicar(A[i][k], B[k][j]);
                        //System.out.println("\nMultiplicando " + A[i][k] + " * " +  B[k][j]);
                    }
                    else{
                        Fraccion aux = multiplicar(A[i][k], B[k][j]);
                        // System.out.println("\nMultiplicando " + A[i][k] + " * " +  B[k][j]);
                        matRes[i][j] = Fraccion.sumar(matRes[i][j], aux);
                        //System.out.println("\nSumando " + matRes[i][j] + " + " + aux);
                    }}

            }
        }
        return matRes;
    }
    public static Fraccion[][] matrizInversa(Fraccion[][] matriz) {
        Fraccion det= Fraccion.dividir(1,determinante(matriz));
        if (matriz.length == 2){
            Fraccion[][] nmatriz1 = new Fraccion[2][2];
            nmatriz1[0][0] = matriz[1][1];
            nmatriz1[1][1] = matriz[0][0];
            nmatriz1[0][1] = multiplicar(matriz[0][1] , -1);
            nmatriz1[1][0] = multiplicar(matriz[1][0] , -1);
            return nmatriz1;

        }
        Fraccion[][] nmatriz= matrizAdjunta(matriz);
        multiplicarMatriz(det,nmatriz);
        return nmatriz;
    }

    public static void multiplicarMatriz(Fraccion n, Fraccion[][] matriz) {
        for(int i=0;i<matriz.length;i++)
            for(int j=0;j<matriz.length;j++)
                matriz[i][j] = multiplicar(matriz[i][j],n);
    }


    public static Fraccion[][] matrizAdjunta(Fraccion [][] matriz){
        return matrizTranspuesta(matrizCofactores(matriz));
    }


    public static Fraccion[][] matrizCofactores(Fraccion[][] matriz){
        Fraccion[][] nm=new Fraccion[matriz.length][matriz.length];
        for(int i=0;i<matriz.length;i++) {
            for(int j=0;j<matriz.length;j++) {
                Fraccion[][] det=new Fraccion[matriz.length-1][matriz.length-1];
                Fraccion detValor;
                for(int k=0;k<matriz.length;k++) {
                    if(k!=i) {
                        for(int l=0;l<matriz.length;l++) {
                            if(l!=j) {
                                int indice1=k<i ? k : k-1 ;
                                int indice2=l<j ? l : l-1 ;
                                det[indice1][indice2]=matriz[k][l];
                            }
                        }
                    }
                }
                detValor=determinante(det);
                nm[i][j]= multiplicar(detValor , (int)Math.pow(-1, i+j+2));

            }
        }
        return nm;
    }

    public static Fraccion[][] matrizTranspuesta(Fraccion[][] matriz){
        Fraccion[][]nuevam=new Fraccion[matriz[0].length][matriz.length];
        for(int i=0; i<matriz.length; i++)
        {
            for(int j=0; j<matriz.length; j++)
                nuevam[i][j]=matriz[j][i];
        }
        return nuevam;
    }

    public static Fraccion determinante(Fraccion[][] matriz)
    {
        Fraccion det;
        if(matriz.length==2)
        {
            det = Fraccion.restar(multiplicar(matriz[0][0],matriz[1][1]), multiplicar(matriz[1][0],matriz[0][1]));
            return det;
        }
        Fraccion suma= new Fraccion(0,1);
        for(int i=0; i<matriz.length; i++){
            Fraccion[][] nm=new Fraccion[matriz.length-1][matriz.length-1];
            for(int j=0; j<matriz.length; j++){
                if(j!=i){
                    for(int k=1; k<matriz.length; k++){
                        int indice=-1;
                        if(j<i)
                            indice=j;
                        else if(j>i)
                            indice=j-1;
                        nm[indice][k-1]=matriz[j][k];
                    }
                }
            }
            if(i%2==0)
                suma = Fraccion.sumar(suma, multiplicar(matriz[i][0] , determinante(nm)));
            else
                suma = Fraccion.restar(suma, multiplicar(matriz[i][0] , determinante(nm)));
        }
        return suma;
    }

}
