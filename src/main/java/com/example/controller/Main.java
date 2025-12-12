package com.example.controller;

import com.example.controller.DraggableMakerGrid;
import model.Board;
import model.Ship;
import model.ShipType;
import view.GridHandler;
import view.ShipView;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * Aplicación principal - Batalla Naval
 * VERSIÓN RESPONSIVE: Se adapta al tamaño de la pantalla sin modo fullscreen
 * @author Tu nombre
 */
public class Main extends Application {

    private Pane boardPane;
    private VBox shipsPanel;
    private DraggableMakerGrid draggableMaker;
    private Label instructionLabel;
    private Board board;

    private static final double BOARD_SIZE_PX = 500;  // 500x500 píxeles
    private static final double SHIP_INITIAL_X = 550; // Posición X inicial de barcos

    @Override
    public void start(Stage primaryStage) {
        // Crear panel principal
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: #2c3e50;");

        // Panel superior con título e instrucciones
        VBox topPanel = createTopPanel();
        root.setTop(topPanel);

        // Panel central con el tablero (más grande)
        boardPane = new Pane();
        boardPane.setPrefSize(BOARD_SIZE_PX, BOARD_SIZE_PX);
        boardPane.setMaxSize(BOARD_SIZE_PX, BOARD_SIZE_PX);
        boardPane.setMinSize(BOARD_SIZE_PX, BOARD_SIZE_PX);
        boardPane.setStyle("-fx-background-color: white; -fx-border-color: #34495e; -fx-border-width: 4;");

        // Dibujar el grid
        GridHandler gridHandler = new GridHandler(boardPane);

        // Centrar el tablero
        HBox centerContainer = new HBox(boardPane);
        centerContainer.setAlignment(Pos.CENTER);
        centerContainer.setPadding(new Insets(30));
        root.setCenter(centerContainer);

        // Panel derecho con los barcos
        shipsPanel = createShipsPanel();
        root.setRight(shipsPanel);

        // Panel inferior con botones CENTRADOS
        VBox bottomPanel = createBottomPanel();
        root.setBottom(bottomPanel);

        // ← INICIALIZAR BOARD
        board = new Board();

        // Inicializar sistema de drag & drop CON EL BOARD
        draggableMaker = new DraggableMakerGrid(board);

        // Crear los barcos arrastrables (APILADOS)
        createDraggableShips();

        // Obtener dimensiones de la pantalla (sin contar barra de tareas)
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

        // Configurar la escena con tamaño de la pantalla
        Scene scene = new Scene(root, screenBounds.getWidth(), screenBounds.getHeight());

        primaryStage.setTitle("Batalla Naval - Colocación de Barcos");
        primaryStage.setScene(scene);

        // Maximizar la ventana (pero NO fullscreen)
        primaryStage.setMaximized(true);

        // Permitir redimensionar si el usuario quiere
        primaryStage.setResizable(true);

        // Posicionar en 0,0 para que ocupe desde arriba
        primaryStage.setX(screenBounds.getMinX());
        primaryStage.setY(screenBounds.getMinY());

        primaryStage.show();
    }

    /**
     * Crea el panel superior con título e instrucciones
     */
    private VBox createTopPanel() {
        VBox panel = new VBox(15);
        panel.setAlignment(Pos.CENTER);
        panel.setPadding(new Insets(0, 0, 20, 0));

        Label title = new Label("🚢 BATALLA NAVAL 🚢");
        title.setStyle("-fx-font-size: 36px; -fx-font-weight: bold; -fx-text-fill: #ecf0f1;");

        instructionLabel = new Label("Arrastra los barcos al tablero | Click derecho para rotar");
        instructionLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: #bdc3c7;");

        panel.getChildren().addAll(title, instructionLabel);
        return panel;
    }

    /**
     * Crea el panel lateral con información de los barcos
     */
    private VBox createShipsPanel() {
        VBox panel = new VBox(25);
        panel.setPadding(new Insets(30));
        panel.setAlignment(Pos.TOP_CENTER);
        panel.setStyle("-fx-background-color: #34495e; -fx-background-radius: 10;");
        panel.setPrefWidth(320);

        Label title = new Label("FLOTA DISPONIBLE");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #ecf0f1;");

        // Información de los barcos
        VBox info = new VBox(12);
        info.getChildren().addAll(
                createShipInfoLabel("1x Portaaviones (4 casillas)", Color.DARKBLUE),
                createShipInfoLabel("2x Submarinos (3 casillas)", Color.GREEN),
                createShipInfoLabel("3x Destructores (2 casillas)", Color.ORANGE),
                createShipInfoLabel("4x Fragatas (1 casilla)", Color.GRAY)
        );

        // Nota adicional
        Label note = new Label("💡 Los barcos del mismo tipo\nestán apilados");
        note.setStyle("-fx-font-size: 14px; -fx-text-fill: #95a5a6; -fx-text-alignment: center;");
        note.setWrapText(true);

        panel.getChildren().addAll(title, info, note);
        return panel;
    }

    /**
     * Crea un label de información de barco
     */
    private Label createShipInfoLabel(String text, Color color) {
        Label label = new Label("• " + text);
        label.setStyle("-fx-font-size: 16px; -fx-text-fill: #ecf0f1;");
        label.setTextFill(Color.web("#ecf0f1"));
        return label;
    }

    /**
     * Crea el panel inferior con botones CENTRADOS
     */
    private VBox createBottomPanel() {
        VBox panel = new VBox(15);
        panel.setAlignment(Pos.CENTER);
        panel.setPadding(new Insets(25, 0, 0, 0));

        HBox buttonBox = new HBox(25);
        buttonBox.setAlignment(Pos.CENTER);

        Button resetButton = new Button("🔄 Reiniciar");
        resetButton.setStyle("-fx-font-size: 18px; -fx-padding: 15 30; -fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5;");
        resetButton.setOnAction(e -> resetBoard());

        Button startButton = new Button("▶ Iniciar Juego");
        startButton.setStyle("-fx-font-size: 18px; -fx-padding: 15 30; -fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5;");
        startButton.setOnAction(e -> startGame());

        buttonBox.getChildren().addAll(resetButton, startButton);
        panel.getChildren().add(buttonBox);

        return panel;
    }

    /**
     * Crea todos los barcos arrastrables (APILADOS POR TIPO)
     */
    private void createDraggableShips() {
        double startX = SHIP_INITIAL_X;
        double startY = 100;
        double spacing = 90;

        // 1 Portaaviones (solo 1, no necesita apilado)
        createDraggableShip(ShipType.PORTAAVIONES, true, startX, startY);
        startY += spacing;

        // 2 Submarinos APILADOS en la misma posición
        for (int i = 0; i < 2; i++) {
            createDraggableShip(ShipType.SUBMARINO, true, startX, startY);
        }
        startY += spacing;

        // 3 Destructores APILADOS en la misma posición
        for (int i = 0; i < 3; i++) {
            createDraggableShip(ShipType.DESTRUCTOR, true, startX, startY);
        }
        startY += spacing;

        // 4 Fragatas APILADAS en la misma posición
        for (int i = 0; i < 4; i++) {
            createDraggableShip(ShipType.FRAGATA, true, startX, startY);
        }
    }

    /**
     * Crea un barco arrastrable individual
     */
    private void createDraggableShip(ShipType type, boolean horizontal, double x, double y) {
        Ship ship = new Ship(type, horizontal);
        ShipView shipView = new ShipView(ship);

        // Posicionar el barco usando LayoutX/Y
        shipView.setLayoutX(x);
        shipView.setLayoutY(y);

        // Hacer que se pueda arrastrar
        draggableMaker.makeDraggable(shipView);

        // Click derecho para rotar
        shipView.setOnMouseClicked(event -> {
            if (event.getButton() == javafx.scene.input.MouseButton.SECONDARY) {
                Ship rotatingShip = shipView.getShip();

                // Obtener posición actual
                double currentX = shipView.getLayoutX();
                double currentY = shipView.getLayoutY();
                int gridCol = (int)(currentX / 50);
                int gridRow = (int)(currentY / 50);

                // Cambiar orientación en el MODELO primero
                rotatingShip.setHorizontal(!rotatingShip.isHorizontal());

                // Actualizar orientación visual
                shipView.updateOrientation();

                // Validar que el barco rotado quepa en el tablero
                boolean fitsAfterRotation = true;
                if (currentX < BOARD_SIZE_PX && currentY < BOARD_SIZE_PX) {
                    if (rotatingShip.isHorizontal()) {
                        if (gridCol + rotatingShip.getSize() > 10) {
                            fitsAfterRotation = false;
                        }
                    } else {
                        if (gridRow + rotatingShip.getSize() > 10) {
                            fitsAfterRotation = false;
                        }
                    }
                }

                // Si no cabe, deshacer la rotación
                if (!fitsAfterRotation && currentX < BOARD_SIZE_PX) {
                    rotatingShip.setHorizontal(!rotatingShip.isHorizontal());
                    shipView.updateOrientation();
                }
            }
        });

        // Agregar al panel del tablero
        boardPane.getChildren().add(shipView);
    }




    private void resetBoard() {
        // Limpiar todos los barcos
        boardPane.getChildren().removeIf(node -> node instanceof ShipView);

        // Reiniciar el board
        board = new Board();
        draggableMaker = new DraggableMakerGrid(board);

        // Volver a crear los barcos
        createDraggableShips();

        instructionLabel.setText("Tablero reiniciado | Arrastra los barcos al tablero");
        instructionLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: #bdc3c7;");
    }


    /**
     * Intenta iniciar el juego
     */
    private void startGame() {
        // TODO: Validar que todos los barcos estén colocados correctamente
        instructionLabel.setText("¡Juego iniciado! (Esta funcionalidad se implementará después)");
        instructionLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: #2ecc71; -fx-font-weight: bold;");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
