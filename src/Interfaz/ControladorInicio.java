package Interfaz;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Creado por David Valverde Garro - 2016034774
 * el 10-Mar-17.
 */
public class ControladorInicio implements Initializable{                                                                 //los controladores deben implementar Initializable
    //Controles
    @FXML
    public Button botonOpsElementales;
    @FXML
    public Button ecuacionesMatriciales;

    //Otras variables

    //Metodo inicializador de la ventana                                                                                 Aqui va el codigo que asigna los atributos de los controles
    public void initialize(URL fxmlFileLocation, ResourceBundle resources){
       botonOpsElementales.setOnAction(event -> {
           abrirVentanaElementales();
       });
    }

    //Metodos de Funcionalidad                                                                                           Dan fancionalidad a botones, cajas de texto, etc.
    public void abrirVentanaElementales(){                                                                               //Ejemplo de como abrir una nueva ventana desde otra
        Stage escenario = new Stage();
        FXMLLoader loader = new FXMLLoader();
        Parent raiz = null;                                                                                              //Se crean estos tres objetos
        try {
            raiz = loader.load(getClass().getResource("Elementales.fxml").openStream());                           //Con esto se indica el FXML de la nueva ventana
        } catch (IOException e) {
            e.printStackTrace();
        }
        ControladorElementales controlador = (ControladorElementales) loader.getController();                            //Se instancia el controlador respectivo, se debe hacer un casting para que funcione.
        escenario.setTitle("Calculadora de Operaciones Elementales");
        escenario.setScene(new Scene(raiz));
        escenario.show();

        Stage temporal = (Stage) botonOpsElementales.getScene().getWindow();                                             //Se obtiene el stage al que pertenece el boton que abre la nueva ventana para poder cerrarla
        temporal.close();
    }
}
