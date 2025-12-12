package com.battleship.main;

import com.battleship.view.StartView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        StartView startView = new StartView();
        Scene scene = new Scene(startView.getInstance(), 1000, 900);

        primaryStage.setTitle("Battleship - Inicio");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
