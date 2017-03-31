package Interfaz;

import Aritmetica_Logica.EcuacionMatriz;
import Aritmetica_Logica.Fraccion;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Creado por David Valverde Garro - 2016034774
 * Fecha: 30-Mar-17 Tiempo: 10:07 PM
 */
public class ControladorEcuaciones implements Initializable {
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

    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        configurarMenuOrden();
        botonResolver.setOnAction(event -> {
            matrizLogicaA = construirMatrizLogica(matrizA);
            matrizLogicaB = construirMatrizLogica(matrizB);
            matrizLogicaC = construirMatrizLogica(matrizC);

            Fraccion[][] matrizArrayA = EcuacionMatriz.pasarArray(matrizLogicaA);
            Fraccion[][] matrizArrayB = EcuacionMatriz.pasarArray(matrizLogicaB);
            Fraccion[][] matrizArrayC = EcuacionMatriz.pasarArray(matrizLogicaC);

            Fraccion[][] matrizArrayX = EcuacionMatriz.ecuacionMatriz(matrizArrayA, matrizArrayB, matrizArrayC);

            matrizLogicaX = EcuacionMatriz.pasarVector(matrizArrayX);

            construirMatrizX(matrizLogicaX);
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
        });

        orden2.setOnAction(event -> {
            orden = 2;
            construirMatriz(matrizA);
            construirMatriz(matrizB);
            construirMatriz(matrizC);
            construirMatriz(matrizX);
        });

        orden3.setOnAction(event -> {
            orden = 3;
            construirMatriz(matrizA);
            construirMatriz(matrizB);
            construirMatriz(matrizC);
            construirMatriz(matrizX);
        });

        orden4.setOnAction(event -> {
            orden = 4;
            construirMatriz(matrizA);
            construirMatriz(matrizB);
            construirMatriz(matrizC);
            construirMatriz(matrizX);
        });

        orden5.setOnAction(event -> {
            orden = 5;
            construirMatriz(matrizA);
            construirMatriz(matrizB);
            construirMatriz(matrizC);
            construirMatriz(matrizX);
        });
    }
}
