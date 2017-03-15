package Interfaz;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Creado por David Valverde Garro - 2016034774
 * el 10-Mar-17.
 */
public class ControladorInicio implements Initializable{
    @FXML
    public Button botonOpsElementales;
    @FXML
    public Button ecuacionesMatriciales;

    public void initialize(URL fxmlFileLocation, ResourceBundle resources){
       botonOpsElementales.setOnAction(event -> {
           abrirVentanaElementales();
       });
    }

    //Metodos que dan funcionalidad a los controles de la ventana
    public void abrirVentanaElementales(){

    }
}
