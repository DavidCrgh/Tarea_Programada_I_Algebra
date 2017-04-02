package Interfaz;

import Aritmetica_Logica.CalculadoraElementales;
import Aritmetica_Logica.Fraccion;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.*;
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
 * Fecha: 15-Mar-17 Tiempo: 6:09 PM
 */
public class ControladorElementales implements Initializable{
    //Controles
    @FXML
    public Pane panel;
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
    public Label ultimaOperacion;
    @FXML
    public Button botonSiguiente;
    @FXML
    public MenuBar menuConfiguracion;
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
    Group parentesisA = new Group();
    Group parentesisB = new Group();

    public void initialize(URL fxmlFileLocation, ResourceBundle resources){
        panel.getChildren().add(parentesisA);
        panel.getChildren().add(parentesisB);
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
                VBox vbox = (VBox) matrizActual.getChildren().get(columnas * i + j);
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
        Fraccion fraccion = matrizActualLogica.get(fila).get(columna);
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
        entradaNumerador.setEditable(false);
        entradaNumerador.setAlignment(Pos.CENTER);
        entradaNumerador.setText(String.valueOf(fraccion.numerador));
        Separator separador = new Separator(Orientation.HORIZONTAL);
        TextField entradaDenominador = new TextField();
        entradaDenominador.setEditable(false);
        entradaDenominador.setAlignment(Pos.CENTER);
        entradaDenominador.setText(String.valueOf(fraccion.denominador));

        contenedorVertical.getChildren().addAll(entradaNumerador,separador,entradaDenominador);
        return contenedorVertical;
    }

    public void configurarMenusFilas(){
        filas1.setOnAction(event -> {
            filas = 1;
            construirMatriz();
            setParentesis (filas, columnas);
        });

        filas2.setOnAction(event -> {
            filas = 2;
            construirMatriz();
            setParentesis (filas, columnas);
        });

        filas3.setOnAction(event -> {
            filas = 3;
            construirMatriz();
            setParentesis (filas, columnas);
        });

        filas4.setOnAction(event -> {
            filas = 4;
            construirMatriz();
            setParentesis (filas, columnas);
        });

        filas5.setOnAction(event -> {
            filas = 5;
            construirMatriz();
            setParentesis (filas, columnas);
        });
    }

    public void configurarMenusColumnas(){
        columnas1.setOnAction(event -> {
            columnas = 1;
            construirMatriz();
            setParentesis (filas, columnas);
        });

        columnas2.setOnAction(event -> {
            columnas = 2;
            construirMatriz();
            setParentesis (filas, columnas);
        });

        columnas3.setOnAction(event -> {
            columnas = 3;
            construirMatriz();
            setParentesis (filas, columnas);
        });

        columnas4.setOnAction(event -> {
            columnas = 4;
            construirMatriz();
            setParentesis (filas, columnas);
        });

        columnas5.setOnAction(event -> {
            columnas = 5;
            construirMatriz();
            setParentesis (filas, columnas);
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
                operacionesAplicadas.add(armarStringOperaciones(fila,sel,escalar,-1));
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
            operacionesAplicadas.add(armarStringOperaciones(fila,sel,null,fila2));
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
                operacionesAplicadas.add(armarStringOperaciones(fila,sel,escalar,fila2));
                calculadoraElementales.sumarFilas(matrizActualLogica,fila-1,fila2-1,escalar);
                historialMatricesLogica.add(copiarMatriz(matrizActualLogica));
                construirMatrizActual(matrizActualLogica);
            }catch(NumberFormatException e){
                final JPanel mensaje = new JPanel();
                JOptionPane.showMessageDialog(mensaje, "Los datos ingresados no son válidos");
            }
        }
        //imprimirHistorial ();
    }

    public String armarStringOperaciones(int fila1, String operacion, Fraccion escalar, int fila2){
        String resultado = "";
        switch (operacion){
            case "Multiplicar fila por escalar":
                resultado += escalar.numerador + "/" + escalar.denominador + " f" + fila1;
                break;
            case "Intercambiar filas":
                resultado += "f" + fila1 + " <-> " + "f" + fila2;
                break;
            case "Sumar dos filas":
                resultado += "f" + fila1 + " + " + escalar.numerador + "/" + escalar.denominador +" f" + fila2;
                break;
        }
        return resultado;
    }

    public void definirTamano(){
        construirMatrizLogica();
        historialMatricesLogica.add(copiarMatriz(matrizActualLogica));
        definirMatriz.setDisable(true);
        botonResolver.setDisable(false);
        construirMatrizHistorica(matrizActualLogica);
        ultimaOperacion.setText("Inicial");
        menuConfiguracion.setDisable(true);
        switchEntradasMatrizAcutal(false);
    }

    public void switchEntradasMatrizAcutal(boolean valorEditable){
        int limite = matrizActual.getChildren().size();

        for(int i = 0; i < limite; i++){
            VBox entradaActual = (VBox) matrizActual.getChildren().get(i);
            TextField entradaNumerador = (TextField) entradaActual.getChildren().get(0);
            TextField entradaDenominador = (TextField) entradaActual.getChildren().get(2);
            entradaNumerador.setEditable(valorEditable);
            entradaDenominador.setEditable(valorEditable);
            entradaActual.getChildren().set(0,entradaNumerador);
            entradaActual.getChildren().set(2,entradaDenominador);
        }
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
            panel.getChildren().remove(parentesisA);
            panel.getChildren().remove(parentesisB);
            menuConfiguracion.setDisable(false);
            switchEntradasMatrizAcutal(true);
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
            ultimaOperacion.setText(operacionesAplicadas.get(ind-1));
            //imprimirMatriz(historialMatricesLogica.get(ind));
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
            if(ind == 0){
                ultimaOperacion.setText("Inicial");
            } else {
                ultimaOperacion.setText(operacionesAplicadas.get(ind - 1));
            }
            //imprimirMatriz(historialMatricesLogica.get(ind));
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

    private void setParentesis (int ordenN, int ordenM){
        panel.getChildren().remove(parentesisA);
        panel.getChildren().remove(parentesisB);
        Double[] espacios = {50.0, 30.0, 4.1, 1.5, 3.0};
        Double[] espaciosH= {50.0, 30.0, 11.5, 1.7, 4.8};
        parentesisA = createParenthesis(14.0, 65.0, ordenN, ordenM, espacios);
        parentesisB = createParenthesis(570.0, 73.0, ordenN, ordenM, espaciosH);
        panel.getChildren().add(parentesisA);
        panel.getChildren().add(parentesisB);
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
