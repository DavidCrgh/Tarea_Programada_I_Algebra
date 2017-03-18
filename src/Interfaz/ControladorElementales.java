package Interfaz;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.net.URL;
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

    public void initialize(URL fxmlFileLocation, ResourceBundle resources){

    }

    //Metodos que dan funcionalidad a los controles de la ventana
}
