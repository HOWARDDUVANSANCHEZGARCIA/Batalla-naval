package com.battleship.main;

import com.battleship.view.StartView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main entry point for the Battleship application.
 * <p>
 * This class extends the JavaFX {@link Application} class and is responsible for
 * setting up the primary stage and loading the initial start view.
 * </p>
 */
public class Main extends Application {

    /**
     * Starts the JavaFX application.
     * <p>
     * Initializes the primary stage, configures the scene dimensions (1000x900),
     * sets the window title, disables resizing, and displays the initial {@link StartView}.
     * </p>
     *
     * @param primaryStage The primary stage for this application, onto which
     * the application scene can be set.
     * @throws Exception If an error occurs during application startup.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        StartView startView = new StartView();
        Scene scene = new Scene(startView.getInstance(), 1000, 900);

        primaryStage.setTitle("Battleship - Inicio");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * The main method is the entry point for the Java application.
     * <p>
     * It calls the {@link #launch(String...)} method to start the JavaFX lifecycle.
     * </p>
     *
     * @param args Command line arguments passed to the application.
     */
    public static void main(String[] args) {
        launch(args);
    }
}