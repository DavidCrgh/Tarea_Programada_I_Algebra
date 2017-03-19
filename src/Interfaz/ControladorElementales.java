package Interfaz;

import Aritmetica_Logica.Fraccion;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Creado por David Valverde Garro - 2016034774
 * Fecha: 15-Mar-17 Tiempo: 6:09 PM
 */
public class ControladorElementales implements Initializable{
    //Controles
    @FXML
    public GridPane matrizActual;
    @FXML
    public GridPane matrizHistorica;
    @FXML
    public ComboBox<String> seleccionadorFila1;
    @FXML
    public ComboBox<String> seleccionadorOperacion;
    @FXML
    public TextField entradaEscalar;
    @FXML
    public ComboBox<String> seleccionadorFila2;
    @FXML
    public Button botonResolver;
    @FXML
    public Button botonAnterior;
    @FXML
    public Label indicador;
    @FXML
    public Button botonSiguiente;
    @FXML
    public MenuItem filas1;
    @FXML
    public MenuItem filas2;
    @FXML
    public MenuItem filas3;
    @FXML
    public MenuItem filas4;
    @FXML
    public MenuItem filas5;
    @FXML
    public MenuItem columnas1;
    @FXML
    public MenuItem columnas2;
    @FXML
    public MenuItem columnas3;
    @FXML
    public MenuItem columnas4;
    @FXML
    public MenuItem columnas5;
    @FXML
    public MenuItem reset;

    //Otras Variables
    int filas;
    int columnas;
    ArrayList<ArrayList<Fraccion>> matrizActualLogica;
    ArrayList<ArrayList<ArrayList<Fraccion>>> historialMatricesLogica;

    public void initialize(URL fxmlFileLocation, ResourceBundle resources){
        filas = 1;
        columnas = 1;
        matrizActualLogica = new ArrayList<>();
        historialMatricesLogica = new ArrayList<>();
        configurarMenusFilas();
        configurarMenusColumnas();
    }

    public VBox construirEntradaMatriz(){
        VBox contenedorVertical = new VBox();
        contenedorVertical.setAlignment(Pos.CENTER);
        TextField entradaNumerador = new TextField();
        Separator separador = new Separator(Orientation.HORIZONTAL);
        TextField entradaDenominador = new TextField();

        contenedorVertical.getChildren().addAll(entradaNumerador,separador,entradaDenominador);
        return contenedorVertical;
    }

    public void construirMatrizActual(){
        matrizActual.getChildren().clear();

        for(int i = 0; i < filas; i++){
            for(int j = 0; j < columnas; j++){
                matrizActual.add(construirEntradaMatriz(),j,i);
            }
        }
    }

    public void configurarMenusFilas(){
        filas1.setOnAction(event -> {
            filas = 1;
            construirMatrizActual();
        });

        filas2.setOnAction(event -> {
            filas = 2;
            construirMatrizActual();
        });

        filas3.setOnAction(event -> {
            filas = 3;
            construirMatrizActual();
        });

        filas4.setOnAction(event -> {
            filas = 4;
            construirMatrizActual();
        });

        filas5.setOnAction(event -> {
            filas = 5;
            construirMatrizActual();
        });
    }

    public void configurarMenusColumnas(){
        columnas1.setOnAction(event -> {
            columnas = 1;
            construirMatrizActual();
        });

        columnas2.setOnAction(event -> {
            columnas = 2;
            construirMatrizActual();
        });

        columnas3.setOnAction(event -> {
            columnas = 3;
            construirMatrizActual();
        });

        columnas4.setOnAction(event -> {
            columnas = 4;
            construirMatrizActual();
        });

        columnas5.setOnAction(event -> {
            columnas = 5;
            construirMatrizActual();
        });
    }

    //Metodos que dan funcionalidad a los controles de la ventana
}
