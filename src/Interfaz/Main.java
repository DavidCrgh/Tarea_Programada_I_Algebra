package Interfaz;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Inicio.fxml"));
        primaryStage.setTitle("Tarea Programada 1 - Álgebra Lineal para Computación");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {
        //launch(args);
        ArrayList<String> a1 = new ArrayList<>();
        a1.add(0,"0");
        a1.add(1,"1");
        a1.add(2,"2");
        a1.add(3,"3");

        String buffer = a1.get(0);
        a1.set(0,a1.get(3));
        a1.set(3,buffer);

        for(int i = 0; i < 4; i++){
            System.out.print(a1.get(i) + "\t");
        }
    }
}
