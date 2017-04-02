package Interfaz;

import Aritmetica_Logica.EcuacionMatriz;
import Aritmetica_Logica.Fraccion;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeType;

import javax.swing.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Creado por David Valverde Garro - 2016034774
 * Fecha: 30-Mar-17 Tiempo: 10:07 PM
 */
public class ControladorEcuaciones implements Initializable {
    @FXML
    public Pane panel;
    @FXML
    public GridPane matrizA;
    @FXML
    public GridPane matrizB;
    @FXML
    public GridPane matrizC;
    @FXML
    public GridPane matrizX;
    @FXML
    public Button botonReset;
    @FXML
    public Button botonResolver;
    @FXML
    public MenuItem orden1;
    @FXML
    public MenuItem orden2;
    @FXML
    public MenuItem orden3;
    @FXML
    public MenuItem orden4;
    @FXML
    public MenuItem orden5;

    public int orden;
    public ArrayList<ArrayList<Fraccion>> matrizLogicaA;
    public ArrayList<ArrayList<Fraccion>> matrizLogicaB;
    public ArrayList<ArrayList<Fraccion>> matrizLogicaC;
    public ArrayList<ArrayList<Fraccion>> matrizLogicaX;
    Group parentesisA = new Group();
    Group parentesisB = new Group();
    Group parentesisC = new Group();
    Group parentesisX = new Group();

    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        configurarMenuOrden();

        panel.getChildren().add(parentesisA);
        panel.getChildren().add(parentesisB);
        panel.getChildren().add(parentesisC);
        panel.getChildren().add(parentesisX);

        botonResolver.setOnAction(event -> {
            matrizLogicaA = construirMatrizLogica(matrizA);
            matrizLogicaB = construirMatrizLogica(matrizB);
            matrizLogicaC = construirMatrizLogica(matrizC);

            Fraccion[][] matrizArrayA = EcuacionMatriz.pasarArray(matrizLogicaA);
            Fraccion[][] matrizArrayB = EcuacionMatriz.pasarArray(matrizLogicaB);
            Fraccion[][] matrizArrayC = EcuacionMatriz.pasarArray(matrizLogicaC);

            if (EcuacionMatriz.determinante(matrizArrayA).numerador == 0){
                JOptionPane.showMessageDialog(null, "La ecuación matricial no posee solución única \nFavor pruebe con otra ecuación");
            }
            else {
                Fraccion[][] matrizArrayX = EcuacionMatriz.solucionarEcuacion(matrizArrayA, matrizArrayB, matrizArrayC);

                matrizLogicaX = EcuacionMatriz.pasarVector(matrizArrayX);

                construirMatrizX(matrizLogicaX);
            }
        });

        botonReset.setOnAction(event -> {
            orden = 1;
            matrizA.getChildren().clear();
            matrizB.getChildren().clear();
            matrizC.getChildren().clear();
            matrizX.getChildren().clear();
            matrizLogicaA = null;
            matrizLogicaB = null;
            matrizLogicaC = null;
            matrizLogicaX = null;
            panel.getChildren().remove(parentesisA);
            panel.getChildren().remove(parentesisB);
            panel.getChildren().remove(parentesisC);
            panel.getChildren().remove(parentesisX);
        });
    }

    public Fraccion construirFracciondeVBox(VBox vbox){
        TextField entradaNumerador = (TextField) vbox.getChildren().get(0);
        TextField entradaDenominador = (TextField) vbox.getChildren().get(2);
        return new Fraccion(Integer.parseInt(entradaNumerador.getText()), Integer.parseInt(entradaDenominador.getText()));
    }

    public ArrayList<ArrayList<Fraccion>> construirEsqueletoMatriz(){
        ArrayList<ArrayList<Fraccion>> esqueleto = new ArrayList<>();
        for(int i = 0; i < orden; i++){
            ArrayList<Fraccion> filaActual = new ArrayList<>();
            esqueleto.add(filaActual);
        }

        return esqueleto;
    }

    public ArrayList<ArrayList<Fraccion>> construirMatrizLogica(GridPane matrizGrafica){
        ArrayList<ArrayList<Fraccion>> matrizLogica = construirEsqueletoMatriz();
        for(int i = 0; i < orden; i++){
            for(int j = 0; j < orden; j++){
                VBox vbox = (VBox) matrizGrafica.getChildren().get(orden * i + j);
                matrizLogica.get(i).add(construirFracciondeVBox(vbox));
            }
        }
        return matrizLogica;
    }

    public void construirMatrizX(ArrayList<ArrayList<Fraccion>> matrizResultado){
        matrizX.getChildren().clear();
        for(int i = 0; i < orden; i++){
            for(int j = 0; j < orden; j++){
                matrizX.add(construirEntradaMatrizX(matrizResultado,i,j),j,i);
            }
        }
    }

    public VBox construirEntradaMatrizX(ArrayList<ArrayList<Fraccion>> matrizActualHistorica, int fila, int columna){
        Fraccion fraccion = matrizActualHistorica.get(fila).get(columna);
        VBox contenedorVertical = new VBox();
        contenedorVertical.setAlignment(Pos.CENTER);
        TextField entradaNumerador = new TextField();
        entradaNumerador.setAlignment(Pos.CENTER);
        entradaNumerador.setText(String.valueOf(fraccion.numerador));
        Separator separador = new Separator(Orientation.HORIZONTAL);
        TextField entradaDenominador = new TextField();
        entradaDenominador.setAlignment(Pos.CENTER);
        entradaDenominador.setText(String.valueOf(fraccion.denominador));

        contenedorVertical.getChildren().addAll(entradaNumerador,separador,entradaDenominador);
        return contenedorVertical;
    }

    public void construirMatriz(GridPane matrizActual){
        matrizActual.getChildren().clear();
        for(int i = 0; i < orden; i++){
            for(int j = 0; j < orden; j++){
                matrizActual.add(construirEntradaMatriz(),j,i);
            }
        }
    }

    public VBox construirEntradaMatriz(){
        VBox contenedorVertical = new VBox();
        contenedorVertical.setAlignment(Pos.CENTER);
        TextField entradaNumerador = new TextField();
        entradaNumerador.setAlignment(Pos.CENTER);
        entradaNumerador.setText("0");
        Separator separador = new Separator(Orientation.HORIZONTAL);
        TextField entradaDenominador = new TextField();
        entradaDenominador.setAlignment(Pos.CENTER);
        entradaDenominador.setText("1");

        contenedorVertical.getChildren().addAll(entradaNumerador,separador,entradaDenominador);
        return contenedorVertical;
    }

    public void configurarMenuOrden(){
        orden1.setOnAction(event -> {
            orden = 1;
            construirMatriz(matrizA);
            construirMatriz(matrizB);
            construirMatriz(matrizC);
            construirMatriz(matrizX);
            setParentesis (orden);
        });

        orden2.setOnAction(event -> {
            orden = 2;
            construirMatriz(matrizA);
            construirMatriz(matrizB);
            construirMatriz(matrizC);
            construirMatriz(matrizX);
            setParentesis (orden);
        });

        orden3.setOnAction(event -> {
            orden = 3;
            construirMatriz(matrizA);
            construirMatriz(matrizB);
            construirMatriz(matrizC);
            construirMatriz(matrizX);
            setParentesis (orden);
        });

        orden4.setOnAction(event -> {
            orden = 4;
            construirMatriz(matrizA);
            construirMatriz(matrizB);
            construirMatriz(matrizC);
            construirMatriz(matrizX);
            setParentesis (orden);
        });

        orden5.setOnAction(event -> {
            orden = 5;
            construirMatriz(matrizA);
            construirMatriz(matrizB);
            construirMatriz(matrizC);
            construirMatriz(matrizX);
            setParentesis (orden);
        });
    }

    private void setParentesis (int orden){
        panel.getChildren().remove(parentesisA);
        panel.getChildren().remove(parentesisB);
        panel.getChildren().remove(parentesisC);
        panel.getChildren().remove(parentesisX);
        Double[] espacios = {50.0, 30.0, 2.0, 1.5, 2.7};
        Double[] espaciosX= {112.0, 30.0, 2.0, 1.5, 2.7};
        parentesisA = createParenthesis(23.0, 108.0, orden, orden, espacios);
        parentesisB = createParenthesis(470.0, 108.0, orden, orden, espacios);
        parentesisC = createParenthesis(819.0, 108.0, orden, orden, espacios);
        parentesisX = createParenthesis(340.0, 489.0, orden, orden, espaciosX);
        panel.getChildren().add(parentesisA);
        panel.getChildren().add(parentesisB);
        panel.getChildren().add(parentesisC);
        panel.getChildren().add(parentesisX);
    }

    private Group createParenthesis (Double inicioX, Double inicioY, int filas, int columnas, Double[] espacios){

        /*  X1,Y1.......         *****   *****
            .          .         *   *   *   * Altura
            .          . filas   *****   *****
            .          .         Ancho |      -EspaciadorV (Entre ambos entradas para fraccion)
            .......X2,Y2               | *****
              columnas                 |      -EspaciadorVV (Entre lo que representaría un nuevo
                                  espaciadorH                espacio a la siguiente fila)

        */

        Double anchoCasilla =   espacios[0];
        Double alturaCasilla =  espacios[1];
        Double espaciadorH =    espacios[2];
        Double espaciadorV =    espacios[3];
        Double espaciadorVV =   espacios[4];

        Double pointX1, pointY1, pointX2, pointY2;

        pointX1 = inicioX;
        pointY1 = inicioY;

        pointX2 = inicioX + (columnas * (anchoCasilla + espaciadorH)) + espaciadorH;
        pointY2 = inicioY + (filas * (alturaCasilla * 2 + espaciadorV + espaciadorVV)) + espaciadorVV;

        Line[] line = new Line[6];

        for (int i = 0; i < 6; i++) {
            line[i] = new Line();
            line[i].setStrokeType(StrokeType.OUTSIDE);
            line[i].setStrokeWidth(1.5);
        }

        //Linea vertical izquierda
        line[0].setStartX   (pointX1);
        line[0].setStartY   (pointY1);
        line[0].setEndX     (pointX1);
        line[0].setEndY     (pointY2);

        //Linea vertical derecha
        line[1].setStartX   (pointX2);
        line[1].setStartY   (pointY1);
        line[1].setEndX     (pointX2);
        line[1].setEndY     (pointY2);

        //Linea esquina superior izquierda
        line[2].setStartX   (pointX1);
        line[2].setStartY   (pointY1);
        line[2].setEndX     (pointX1+20.0);
        line[2].setEndY     (pointY1);

        //Linea esquina inferior izquierda
        line[3].setStartX   (pointX1);
        line[3].setStartY   (pointY2);
        line[3].setEndX     (pointX1+20.0);
        line[3].setEndY     (pointY2);

        //Linea esquina superior derecha
        line[4].setStartX   (pointX2-20.0);
        line[4].setStartY   (pointY1);
        line[4].setEndX     (pointX2);
        line[4].setEndY     (pointY1);

        //Linea esquina inferior derecha
        line[5].setStartX   (pointX2-20.0);
        line[5].setStartY   (pointY2);
        line[5].setEndX     (pointX2);
        line[5].setEndY     (pointY2);

        return new Group(line);
    }

}
