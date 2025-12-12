package com.battleship.main;

import com.battleship.view.StartView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Usamos la clase StartView para cargar la interfaz
        StartView startView = new StartView();

        // Configuramos la escena con el root que nos devuelve la vista
        Scene scene = new Scene(startView.getInstance(), 1000, 900);

        primaryStage.setTitle("Battleship - Inicio");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false); // Opcional: Bloquear redimensionamiento
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}