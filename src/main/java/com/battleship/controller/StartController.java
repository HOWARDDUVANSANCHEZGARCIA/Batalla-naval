package com.battleship.controller;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import com.battleship.persistence.GamePersistenceManager;
import com.battleship.persistence.GameState;
import com.battleship.persistence.PlayerData;
import com.battleship.exceptions.LoadGameException;
import com.battleship.exceptions.InvalidGameStateException;

/**
 * Controller for the start screen handling animations and input logic.
 * <p>
 * This class manages the main menu interactions, including nickname validation,
 * starting a new game, and loading a previously saved game.
 * </p>
 */
public class StartController {

    @FXML
    private TextField nicknameField;

    @FXML
    private VBox mainContainer;

    /**
     * Initializes the controller class.
     * <p>
     * Sets up the initial focus on the nickname field, adds key listeners to handle
     * the "Enter" key for starting the game, and adds a listener to clear error styles
     * when the user types. Finally, it triggers the entrance animation.
     * </p>
     */
    @FXML
    public void initialize() {
        Platform.runLater(() -> nicknameField.requestFocus());

        nicknameField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) onNewGame();
        });

        nicknameField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.isEmpty()) {
                nicknameField.setStyle(null);
            }
        });

        animateEntrance();
    }

    /**
     * Plays a fade-in animation for the main container.
     */
    private void animateEntrance() {
        mainContainer.setOpacity(0);
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(1.5), mainContainer);
        fadeOut.setFromValue(0.0);
        fadeOut.setToValue(1.0);
        fadeOut.play();
    }

    /**
     * Handles the "New Game" button action.
     * <p>
     * Validates that the nickname field is not empty. If valid, transitions to the
     * Ship Placement controller. If invalid, displays an error alert.
     * </p>
     */
    @FXML
    protected void onNewGame() {
        String nickname = nicknameField.getText().trim();

        if (nickname.isEmpty()) {
            nicknameField.setStyle("-fx-border-color: #ff4444; -fx-effect: dropshadow(three-pass-box, red, 10, 0, 0, 0);");
            showAlert("¡Alto ahí, pirata!", "Falta tu nombre", "Debes registrarte en la bitácora antes de zarpar.");
        } else {
            System.out.println("Zarpando con el Capitán: " + nickname);
            Stage currentStage = (Stage) nicknameField.getScene().getWindow();
            ShipPlacementController shipPlacement = new ShipPlacementController();
            shipPlacement.show(currentStage);
        }
    }

    /**
     * Handles the "Load Game" button action.
     * <p>
     * Checks if a nickname is provided and if a saved game exists. If successful,
     * restores the game state and transitions to the Game Controller.
     * Handles {@link LoadGameException} and {@link InvalidGameStateException} if the save file is corrupt.
     * </p>
     */
    @FXML
    protected void onLoadGame() {
        String nickname = nicknameField.getText().trim();

        if (nickname.isEmpty()) {
            nicknameField.setStyle("-fx-border-color: #ff4444; -fx-effect: dropshadow(three-pass-box, red, 10, 0, 0, 0);");
            showAlert(
                    "¡Bitácora Cerrada!",
                    "Capitán Desconocido",
                    "No puedes recuperar tu nave si no sabemos quién eres.\nPor favor, firma con tu nombre antes de abrir la bitácora."
            );
            return;
        }

        System.out.println("Buscando bitácora antigua del Capitán: " + nickname);

        GamePersistenceManager manager = GamePersistenceManager.getInstance();

        if (!manager.hasSavedGame()) {
            showAlert(
                    "¡Bitácora Vacía!",
                    "Sin Partidas Guardadas",
                    "No se encontró ninguna partida guardada, Capitán " + nickname + ".\n¿Por qué no empiezas una nueva aventura?"
            );
            return;
        }

        try {
            GameState gameState = manager.loadGameState();
            PlayerData playerData = manager.loadPlayerData();

            playerData.setNickname(nickname);

            showInfoAlert(
                    "¡Bitácora Encontrada!",
                    "Partida Recuperada",
                    "Bienvenido de vuelta, Capitán " + nickname + "!\n\n" +
                            "Última partida: " + gameState.getLastSaved() + "\n" +
                            "Turno: " + (gameState.isPlayerTurn() ? "Tu turno" : "Turno del enemigo")
            );

            Stage currentStage = (Stage) nicknameField.getScene().getWindow();

            GameController gameController = new GameController();
            gameController.loadGame(currentStage, gameState, playerData);


            System.out.println("Partida cargada exitosamente para: " + nickname);

        } catch (LoadGameException e) {
            System.err.println("Error al cargar la partida: " + e.getMessage());
            e.printStackTrace();

            showAlert(
                    "¡Error Fatal!",
                    "Bitácora Dañada",
                    "La bitácora está corrupta o ilegible, Capitán.\n\n" +
                            "Error: " + e.getErrorType() + "\n" +
                            "Detalles: " + e.getMessage() + "\n\n" +
                            "Tendrás que empezar una nueva aventura."
            );

            manager.deleteSavedGame();

        } catch (InvalidGameStateException e) {
            System.err.println("Estado del juego inválido: " + e.getMessage());

            showAlert(
                    "¡Estado Inválido!",
                    "Partida Corrupta",
                    "El estado del juego guardado no es válido.\n\n" +
                            "Detalles: " + e.getMessage()
            );

            manager.deleteSavedGame();
        }
    }

    /**
     * Displays a WARNING alert with custom styling.
     *
     * @param title   The title of the alert window.
     * @param header  The header text of the alert.
     * @param content The main content text of the alert.
     */
    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        javafx.scene.control.DialogPane dialogPane = alert.getDialogPane();
        String cssPath = "/com/battleship/view/styles.css";
        java.net.URL cssResource = getClass().getResource(cssPath);

        if (cssResource != null) {
            dialogPane.getStylesheets().add(cssResource.toExternalForm());
            dialogPane.getStyleClass().add("my-dialog");
        } else {
            System.err.println("ERROR CRÍTICO: No se encontró el archivo styles.css en la ruta: " + cssPath);
        }

        alert.showAndWait();
    }

    /**
     * Displays an INFORMATION alert with custom styling.
     *
     * @param title   The title of the alert window.
     * @param header  The header text of the alert.
     * @param content The main content text of the alert.
     */
    private void showInfoAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        javafx.scene.control.DialogPane dialogPane = alert.getDialogPane();
        String cssPath = "/com/battleship/view/styles.css";
        java.net.URL cssResource = getClass().getResource(cssPath);

        if (cssResource != null) {
            dialogPane.getStylesheets().add(cssResource.toExternalForm());
            dialogPane.getStyleClass().add("my-dialog");
        }

        alert.showAndWait();
    }
}