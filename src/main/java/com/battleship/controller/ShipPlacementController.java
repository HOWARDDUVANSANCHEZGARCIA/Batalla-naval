package com.battleship.controller;

import com.battleship.model.Board;
import com.battleship.model.Ship;
import com.battleship.model.ShipType;
import com.battleship.view.GridHandler;
import com.battleship.view.ShipView;
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
 * Controlador de la vista de colocación de barcos
 */
public class ShipPlacementController {

    private Pane boardPane;
    private VBox shipsPanel;
    private DraggableMakerGrid draggableMaker;
    private Label instructionLabel;
    private Board board;

    private static final double BOARD_SIZE_PX = 500;
    private static final double SHIP_INITIAL_X = 550;

    public void show(Stage stage) {
        // Crear panel principal
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: #2c3e50;");

        // Panel superior con título e instrucciones
        VBox topPanel = createTopPanel();
        root.setTop(topPanel);

        // Panel central con el tablero
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

        // Panel inferior con botones
        VBox bottomPanel = createBottomPanel();
        root.setBottom(bottomPanel);

        // Inicializar board
        board = new Board();
        draggableMaker = new DraggableMakerGrid(board);

        // Crear los barcos arrastrables
        createDraggableShips();

        // Obtener dimensiones de la pantalla
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

        // Configurar la escena
        Scene scene = new Scene(root, screenBounds.getWidth(), screenBounds.getHeight());

        stage.setTitle("Batalla Naval - Colocación de Barcos");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.setResizable(true);
        stage.setX(screenBounds.getMinX());
        stage.setY(screenBounds.getMinY());
        stage.show();
    }

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

    private VBox createShipsPanel() {
        VBox panel = new VBox(25);
        panel.setPadding(new Insets(30));
        panel.setAlignment(Pos.TOP_CENTER);
        panel.setStyle("-fx-background-color: #34495e; -fx-background-radius: 10;");
        panel.setPrefWidth(320);

        Label title = new Label("FLOTA DISPONIBLE");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #ecf0f1;");

        VBox info = new VBox(12);
        info.getChildren().addAll(
                createShipInfoLabel("1x Portaaviones (4 casillas)", Color.DARKBLUE),
                createShipInfoLabel("2x Submarinos (3 casillas)", Color.GREEN),
                createShipInfoLabel("3x Destructores (2 casillas)", Color.ORANGE),
                createShipInfoLabel("4x Fragatas (1 casilla)", Color.GRAY)
        );

        Label note = new Label("💡 Los barcos del mismo tipo\nestán apilados");
        note.setStyle("-fx-font-size: 14px; -fx-text-fill: #95a5a6; -fx-text-alignment: center;");
        note.setWrapText(true);

        panel.getChildren().addAll(title, info, note);
        return panel;
    }

    private Label createShipInfoLabel(String text, Color color) {
        Label label = new Label("• " + text);
        label.setStyle("-fx-font-size: 16px; -fx-text-fill: #ecf0f1;");
        return label;
    }

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

    private void createDraggableShips() {
        double startX = SHIP_INITIAL_X;
        double startY = 100;
        double spacing = 90;

        createDraggableShip(ShipType.PORTAAVIONES, true, startX, startY);
        startY += spacing;

        for (int i = 0; i < 2; i++) {
            createDraggableShip(ShipType.SUBMARINO, true, startX, startY);
        }
        startY += spacing;

        for (int i = 0; i < 3; i++) {
            createDraggableShip(ShipType.DESTRUCTOR, true, startX, startY);
        }
        startY += spacing;

        for (int i = 0; i < 4; i++) {
            createDraggableShip(ShipType.FRAGATA, true, startX, startY);
        }
    }

    private void createDraggableShip(ShipType type, boolean horizontal, double x, double y) {
        Ship ship = new Ship(type, horizontal);
        ShipView shipView = new ShipView(ship);

        shipView.setLayoutX(x);
        shipView.setLayoutY(y);

        draggableMaker.makeDraggable(shipView);

        shipView.setOnMouseClicked(event -> {
            if (event.getButton() == javafx.scene.input.MouseButton.SECONDARY) {
                Ship rotatingShip = shipView.getShip();

                // Coordenadas actuales en píxeles
                double currentX = shipView.getLayoutX();
                double currentY = shipView.getLayoutY();

                // Si está fuera del tablero (en el panel derecho), solo rota la vista y el flag
                if (currentX >= BOARD_SIZE_PX || currentY >= BOARD_SIZE_PX) {
                    rotatingShip.setHorizontal(!rotatingShip.isHorizontal());
                    shipView.updateOrientation();
                    return;
                }

                // Está en el tablero → hay que sincronizar con Board
                int gridCol = (int) (currentX / 50); // mejor usar GridConfig.CELL_SIZE
                int gridRow = (int) (currentY / 50);

                boolean wasPlaced = rotatingShip.getPositions() != null && !rotatingShip.getPositions().isEmpty();

                // 1) Si estaba colocado, quitarlo del Board
                if (wasPlaced) {
                    board.removeShip(rotatingShip);
                }

                // 2) Guardar orientación anterior y cambiar a la nueva
                boolean oldHorizontal = rotatingShip.isHorizontal();
                rotatingShip.setHorizontal(!oldHorizontal);
                shipView.updateOrientation();

                // 3) Comprobar si con la nueva orientación cabe y no se solapa
                boolean canStay = board.canPlaceShip(rotatingShip, gridRow, gridCol);

                if (canStay) {
                    // 4) Recolocarlo en el Board con la nueva orientación
                    board.placeShip(rotatingShip, gridRow, gridCol);
                } else {
                    // 5) No cabe / hay superposición → revertir orientación
                    rotatingShip.setHorizontal(oldHorizontal);
                    shipView.updateOrientation();

                    // Volver a colocarlo en el Board como estaba antes
                    if (wasPlaced) {
                        board.placeShip(rotatingShip, gridRow, gridCol);
                    }
                }
            }
        });

        boardPane.getChildren().add(shipView);
    }

    private void resetBoard() {
        boardPane.getChildren().removeIf(node -> node instanceof ShipView);
        board = new Board();
        draggableMaker = new DraggableMakerGrid(board);
        createDraggableShips();

        instructionLabel.setText("Tablero reiniciado | Arrastra los barcos al tablero");
        instructionLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: #bdc3c7;");
    }

    private void startGame() {
        // Validar que todos los barcos estén colocados
        if (board.getShips().size() < 10) { // 10 barcos en total
            instructionLabel.setText("⚠️ Debes colocar todos los barcos antes de iniciar");
            instructionLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: #e74c3c; -fx-font-weight: bold;");
            return;
        }

        instructionLabel.setText("⚓ Iniciando batalla...");
        instructionLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: #2ecc71; -fx-font-weight: bold;");

        // Crear tablero de la IA con barcos aleatorios
        Board iaBoard = new Board();
        placeIAShips(iaBoard);

        // Iniciar el juego
        Stage stage = (Stage) boardPane.getScene().getWindow();
        GameController gameController = new GameController();
        gameController.startGame(board, iaBoard);
    }

    /**
     * Coloca los barcos de la IA de forma aleatoria
     */
    private void placeIAShips(Board iaBoard) {
        ShipType[] shipTypes = {
                ShipType.PORTAAVIONES,
                ShipType.SUBMARINO, ShipType.SUBMARINO,
                ShipType.DESTRUCTOR, ShipType.DESTRUCTOR, ShipType.DESTRUCTOR,
                ShipType.FRAGATA, ShipType.FRAGATA, ShipType.FRAGATA, ShipType.FRAGATA
        };

        for (ShipType type : shipTypes) {
            boolean placed = false;
            int attempts = 0;

            while (!placed && attempts < 100) {
                int row = (int) (Math.random() * 10);
                int col = (int) (Math.random() * 10);
                boolean horizontal = Math.random() > 0.5;

                Ship ship = new Ship(type, horizontal);

                if (iaBoard.canPlaceShip(ship, row, col)) {
                    iaBoard.placeShip(ship, row, col);
                    placed = true;
                }
                attempts++;
            }
        }
    }
}
