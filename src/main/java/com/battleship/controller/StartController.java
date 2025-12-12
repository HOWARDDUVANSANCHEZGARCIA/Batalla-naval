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

public class StartController {

    @FXML
    private TextField nicknameField;

    @FXML
    private VBox mainContainer;

    @FXML
    public void initialize() {
        Platform.runLater(() -> nicknameField.requestFocus());

        nicknameField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                onNewGame();
            }
        });

        nicknameField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.isEmpty()) {
                nicknameField.setStyle(null);
            }
        });

        animateEntrance();
    }

    private void animateEntrance() {
        mainContainer.setOpacity(0);
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(1.5), mainContainer);
        fadeOut.setFromValue(0.0);
        fadeOut.setToValue(1.0);
        fadeOut.play();
    }

    @FXML
    protected void onNewGame() {
        String nickname = nicknameField.getText().trim();

        if (nickname.isEmpty()) {
            nicknameField.setStyle("-fx-border-color: #ff4444; -fx-effect: dropshadow(three-pass-box, red, 10, 0, 0, 0);");
            showAlert("¡Alto ahí, pirata!", "Falta tu nombre", "Debes registrarte en la bitácora antes de zarpar.");
        } else {
            System.out.println("Zarpando con el Capitán: " + nickname);

            // Obtener el Stage actual
            Stage currentStage = (Stage) nicknameField.getScene().getWindow();

            // Crear y mostrar la vista de colocación de barcos
            ShipPlacementController shipPlacement = new ShipPlacementController();
            shipPlacement.show(currentStage);
        }
    }

    @FXML
    protected void onLoadGame() {
        String nickname = nicknameField.getText().trim();

        if (nickname.isEmpty()) {
            // Feedback Visual: Borde Rojo neón en el campo de texto
            nicknameField.setStyle("-fx-border-color: #ff4444; -fx-effect: dropshadow(three-pass-box, red, 10, 0, 0, 0);");

            // Alerta Pirata de Bloqueo
            showAlert(
                    "¡Bitácora Cerrada!",
                    "Capitán Desconocido",
                    "No puedes recuperar tu nave si no sabemos quién eres.\nPor favor, firma con tu nombre antes de abrir la bitácora."
            );
        } else {
            // Si hay nombre, procedemos
            System.out.println("Buscando bitácora antigua del Capitán: " + nickname);
            // TODO: Llamar al método de persistencia para cargar el objeto GameMatch
        }
    }

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
}
