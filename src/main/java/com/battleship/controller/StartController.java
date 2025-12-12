package com.battleship.controller;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * Controlador de la pantalla de inicio con animaciones y lógica de entrada.
 */
public class StartController {

    @FXML
    private TextField nicknameField;

    @FXML
    private VBox mainContainer; // Referencia al contenedor para animarlo

    /**
     * Inicializa la vista, configura listeners y ejecuta la animación de entrada.
     */
    @FXML
    public void initialize() {
        // 1. Configuración del foco y eventos (Igual que antes)
        Platform.runLater(() -> nicknameField.requestFocus());

        nicknameField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) onNewGame();
        });

        nicknameField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.isEmpty()) {
                nicknameField.setStyle(null); // Limpia estilos inline al escribir
            }
        });

        // 2. NUEVO: Animación de Fade-In (Aparición suave)
        animateEntrance();
    }

    /**
     * Ejecuta una transición de opacidad para que el menú aparezca suavemente.
     */
    private void animateEntrance() {
        // Hacemos el contenedor invisible al principio
        mainContainer.setOpacity(0);

        // Creamos la animación de 1.5 segundos
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(1.5), mainContainer);
        fadeOut.setFromValue(0.0);
        fadeOut.setToValue(1.0);
        fadeOut.play();
    }

    @FXML
    protected void onNewGame() {
        String nickname = nicknameField.getText().trim();
        if (nickname.isEmpty()) {
            // Feedback visual rojo usando estilos CSS inline para el error
            nicknameField.setStyle("-fx-border-color: #ff4444; -fx-effect: dropshadow(three-pass-box, red, 10, 0, 0, 0);");
            showAlert("¡Alto ahí, pirata!", "Falta tu nombre", "Debes registrarte en la bitácora antes de zarpar.");
        } else {
            System.out.println("Zarpando con el Capitán: " + nickname);
            // TODO: Cargar GameView
        }
    }

    @FXML
    protected void onLoadGame() {
        System.out.println("Buscando en los archivos antiguos...");
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        // --- SOLUCIÓN ROBUSTA ---

        // 1. Obtenemos el DialogPane
        javafx.scene.control.DialogPane dialogPane = alert.getDialogPane();

        // 2. Cargamos el CSS de forma segura
        String cssPath = "/com/battleship/view/styles.css";
        java.net.URL cssResource = getClass().getResource(cssPath);

        if (cssResource != null) {
            dialogPane.getStylesheets().add(cssResource.toExternalForm());
            dialogPane.getStyleClass().add("my-dialog"); // Clase personalizada
        } else {
            System.err.println("ERROR CRÍTICO: No se encontró el archivo styles.css en la ruta: " + cssPath);
        }

        // 3. Quitamos el ícono de la ventana (opcional, para estética limpia)
        // Stage stage = (Stage) dialogPane.getScene().getWindow();
        // stage.getIcons().add(new Image(...));

        alert.showAndWait();
    }
}