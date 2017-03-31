package Interfaz;

import Aritmetica_Logica.CalculadoraElementales;
import Aritmetica_Logica.Fraccion;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import javax.swing.*;
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
    public ComboBox<Integer> seleccionadorFila1;
    @FXML
    public ComboBox<String> seleccionadorOperacion;
    @FXML
    public TextField entradaNumerador;
    @FXML
    public TextField entradaDenominador;
    @FXML
    public ComboBox<Integer> seleccionadorFila2;
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
    public Button nuevaMatriz;
    @FXML
    public Button definirMatriz;

    public CalculadoraElementales calculadoraElementales;

    //Otras Variables
    int filas;
    int columnas;
    ArrayList<ArrayList<Fraccion>> matrizActualLogica;
    ArrayList<ArrayList<ArrayList<Fraccion>>> historialMatricesLogica;
    ArrayList<String> operacionesAplicadas;

    public void initialize(URL fxmlFileLocation, ResourceBundle resources){
        filas = 1;
        columnas = 1;
        calculadoraElementales = new CalculadoraElementales();
        matrizActualLogica = new ArrayList<>();
        historialMatricesLogica = new ArrayList<>();
        operacionesAplicadas = new ArrayList<>();
        configurarMenusFilas();
        configurarMenusColumnas();
        botonResolver.setDisable(true);

        seleccionadorFila1.getItems().addAll(1,2,3,4,5);
        seleccionadorFila2.getItems().addAll(1,2,3,4,5);
        seleccionadorOperacion.getItems().addAll(
                "Multiplicar fila por escalar",
                "Intercambiar filas",
                "Sumar dos filas"
        );
        botonResolver.setOnAction(event -> aplicarOperacion());
        definirMatriz.setOnAction(event -> definirTamano());
        nuevaMatriz.setOnAction(event -> nuevaMatriz());
        botonSiguiente.setOnAction(event -> mostrarSiguiente());
        botonAnterior.setOnAction(event -> mostrarAnterior());
    }

    public ArrayList<ArrayList<Fraccion>> construirEsqueletoMatriz(){
        ArrayList<ArrayList<Fraccion>> esqueleto = new ArrayList<>();
        for(int i = 0; i < filas; i++){
            ArrayList<Fraccion> filaActual = new ArrayList<>();
            esqueleto.add(filaActual);
        }

        return esqueleto;
    }

    public Fraccion construirFracciondeVBox(VBox vbox){
        TextField entradaNumerador = (TextField) vbox.getChildren().get(0);
        TextField entradaDenominador = (TextField) vbox.getChildren().get(2);
        return new Fraccion(Integer.parseInt(entradaNumerador.getText()), Integer.parseInt(entradaDenominador.getText()));
    }

    public void construirMatrizLogica(){
        matrizActualLogica = construirEsqueletoMatriz();
        for(int i = 0; i < filas; i++){
            for(int j = 0; j < columnas; j++){
                VBox vbox = (VBox) matrizActual.getChildren().get(filas * i + j);
                matrizActualLogica.get(i).add(construirFracciondeVBox(vbox));
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

    public void construirMatriz(){
        matrizActual.getChildren().clear();
        for(int i = 0; i < filas; i++){
            for(int j = 0; j < columnas; j++){
                matrizActual.add(construirEntradaMatriz(),j,i);
            }
        }
    }

    public void construirMatrizActual(ArrayList<ArrayList<Fraccion>> matrizActualLogica) {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                matrizActual.add(construirEntradaMatrizActual(matrizActualLogica, i, j), j, i);
            }
        }
    }

    public VBox construirEntradaMatrizActual(ArrayList<ArrayList<Fraccion>> matrizActualLogica,int fila, int columna){
        Fraccion fraccion = matrizActualLogica.get(fila).get(columna);;
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

    public void construirMatrizHistorica(ArrayList<ArrayList<Fraccion>> matrizActualHistorica){
        matrizHistorica.getChildren().clear();
        for(int i = 0; i < filas; i++){
            for(int j = 0; j < columnas; j++){
                matrizHistorica.add(construirEntradaMatrizHistorica(matrizActualHistorica,i,j),j,i);
            }
        }
    }

    public VBox construirEntradaMatrizHistorica(ArrayList<ArrayList<Fraccion>> matrizActualHistorica, int fila, int columna){
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

    public void configurarMenusFilas(){
        filas1.setOnAction(event -> {
            filas = 1;
            construirMatriz();
        });

        filas2.setOnAction(event -> {
            filas = 2;
            construirMatriz();
        });

        filas3.setOnAction(event -> {
            filas = 3;
            construirMatriz();
        });

        filas4.setOnAction(event -> {
            filas = 4;
            construirMatriz();
        });

        filas5.setOnAction(event -> {
            filas = 5;
            construirMatriz();
        });
    }

    public void configurarMenusColumnas(){
        columnas1.setOnAction(event -> {
            columnas = 1;
            construirMatriz();
        });

        columnas2.setOnAction(event -> {
            columnas = 2;
            construirMatriz();
        });

        columnas3.setOnAction(event -> {
            columnas = 3;
            construirMatriz();
        });

        columnas4.setOnAction(event -> {
            columnas = 4;
            construirMatriz();
        });

        columnas5.setOnAction(event -> {
            columnas = 5;
            construirMatriz();
        });
    }

    public void aplicarOperacion(){
        int fila = seleccionadorFila1.getValue();
        if (fila > filas){
            final JPanel mensaje = new JPanel();
            JOptionPane.showMessageDialog(mensaje, "El número de fila excede el límite, por favor ingreselo nuevamente");
            return;
        }
        String sel = seleccionadorOperacion.getValue();
        if (sel == "Multiplicar fila por escalar"){
            try{
                int numerador = Integer.parseInt(entradaNumerador.getText());
                int denominador = Integer.parseInt(entradaDenominador.getText());
                Fraccion escalar = new Fraccion(numerador,denominador);
                operacionesAplicadas.add(sel);
                calculadoraElementales.multiplicarFila(matrizActualLogica,fila-1,escalar);
                historialMatricesLogica.add(copiarMatriz(matrizActualLogica));
                construirMatrizActual(matrizActualLogica);
            }catch(NumberFormatException e){
                final JPanel mensaje = new JPanel();
                JOptionPane.showMessageDialog(mensaje, "Los datos ingresados no son válidos");
            }
        }
        else if (sel == "Intercambiar filas"){
            int fila2 = seleccionadorFila2.getValue();
            if (fila2 > filas) {
                final JPanel mensaje = new JPanel();
                JOptionPane.showMessageDialog(mensaje, "El número de fila excede el límite, por favor ingreselo nuevamente");
                return;
            }
            operacionesAplicadas.add(sel);
            calculadoraElementales.intercambiarFilas(matrizActualLogica,fila-1,fila2-1);
            historialMatricesLogica.add(copiarMatriz(matrizActualLogica));
            construirMatrizActual(matrizActualLogica);
        }else{
            int fila2 = seleccionadorFila2.getValue();
            if (fila2 > filas) {
                final JPanel mensaje = new JPanel();
                JOptionPane.showMessageDialog(mensaje, "El número de fila excede el límite, por favor ingreselo nuevamente");
                return;
            }
            try{
                int numerador = Integer.parseInt(entradaNumerador.getText());
                int denominador = Integer.parseInt(entradaDenominador.getText());
                Fraccion escalar = new Fraccion(numerador,denominador);
                operacionesAplicadas.add(sel);
                calculadoraElementales.sumarFilas(matrizActualLogica,fila-1,fila2-1,escalar);
                historialMatricesLogica.add(copiarMatriz(matrizActualLogica));
                construirMatrizActual(matrizActualLogica);
            }catch(NumberFormatException e){
                final JPanel mensaje = new JPanel();
                JOptionPane.showMessageDialog(mensaje, "Los datos ingresados no son válidos");
            }
        }
        imprimirHistorial ();
    }

    public void definirTamano(){
        construirMatrizLogica();
        historialMatricesLogica.add(copiarMatriz(matrizActualLogica));
        definirMatriz.setDisable(true);
        botonResolver.setDisable(false);
        construirMatrizHistorica(matrizActualLogica);
    }

    public void nuevaMatriz(){
        int valor = JOptionPane.showConfirmDialog(null,
                "¿Está seguro de volver a iniciar?, se perderan todos los datos");
        if (valor == 1)
            return;
        else if (valor == 2)
            return;
        else{
            filas = 1;
            columnas = 1;
            calculadoraElementales = new CalculadoraElementales();
            matrizActualLogica = new ArrayList<>();
            historialMatricesLogica = new ArrayList<>();
            operacionesAplicadas = new ArrayList<>();
            construirMatriz();
            definirMatriz.setDisable(false);
            matrizHistorica.getChildren().clear();
            indicador.setText("0");
        }
    }

    public void mostrarSiguiente(){
        int ind = Integer.parseInt(indicador.getText()) + 1;
        if (ind > operacionesAplicadas.size()){
            final JPanel mensaje = new JPanel();
            JOptionPane.showMessageDialog(mensaje, "No existen más operaciones aplicadas");
            return;
        }else{
            indicador.setText(String.valueOf(ind));
            ArrayList<ArrayList<Fraccion>> matriz = historialMatricesLogica.get(ind);
            construirMatrizHistorica(matriz);
            imprimirMatriz(historialMatricesLogica.get(ind));
        }

    }

    public void mostrarAnterior() {
        int ind = Integer.parseInt(indicador.getText()) - 1;
        if (ind < 0) {
            final JPanel mensaje = new JPanel();
            JOptionPane.showMessageDialog(mensaje, "No es posible continuar");
            return;
        } else{
            indicador.setText(String.valueOf(ind));
            ArrayList<ArrayList<Fraccion>> matriz = historialMatricesLogica.get(ind);
            construirMatrizHistorica(matriz);
            imprimirMatriz(historialMatricesLogica.get(ind));
        }
    }
    //Metodos que dan funcionalidad a los controles de la ventana

    private ArrayList<ArrayList<Fraccion>> copiarMatriz (ArrayList<ArrayList<Fraccion>> matriz) {
        ArrayList<ArrayList<Fraccion>> copiaMatriz = new ArrayList<>();

        for (int i = 0; i < matriz.size(); i++) {
            copiaMatriz.add(new ArrayList<>());
            for (int j = 0; j < matriz.get(0).size(); j++) {
                copiaMatriz.get(i).add(new Fraccion(    matriz.get(i).get(j).numerador,
                                                        matriz.get(i).get(j).denominador));
            }
        }
        return copiaMatriz;
    }

    private void imprimirHistorial (){
        System.out.println("-------------------------------------------------------");
        System.out.println(historialMatricesLogica.size());
        for ( ArrayList<ArrayList<Fraccion>> matriz : historialMatricesLogica) {
            imprimirMatriz(matriz);
            System.out.println();
        }
    }

    private void imprimirMatriz (ArrayList<ArrayList<Fraccion>> matriz){
        for (ArrayList<Fraccion> subFilas : matriz) {
            for (Fraccion entrada : subFilas) {
                System.out.print(entrada.toString());
                System.out.print('\t');
            }
            System.out.println();
        }
    }
}
