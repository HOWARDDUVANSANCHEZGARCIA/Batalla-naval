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
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * Controller for the Ship Placement Phase (Setup).
 * <p>
 * Handles the drag-and-drop interface where the player positions their fleet
 * before the battle begins. Includes logic for rotation (right-click) and
 * validation of ship placement.
 * </p>
 *
 * @version 1.0.0
 * @author Martin
 */
public class ShipPlacementController {

    // --- UI Components ---
    private Pane boardPane;
    private VBox shipsPanel;
    private Label instructionLabel;

    // --- Logic & Model ---
    private DraggableMakerGrid draggableMaker;
    private Board board;

    // --- Constants ---
    private static final double BOARD_SIZE_PX = 500;
    private static final double SHIP_INITIAL_X = 550;

    /**
     * Initializes and displays the ship placement screen.
     *
     * @param stage The primary stage of the application.
     */
    public void show(Stage stage) {
        // Main layout with the pirate theme background
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(30));
        root.getStyleClass().add("ship-placement-background");

        // Top Panel: Title and Instructions
        VBox topPanel = createTopPanel();
        root.setTop(topPanel);

        // Center Panel: The Grid/Board
        boardPane = new Pane();
        boardPane.setPrefSize(BOARD_SIZE_PX, BOARD_SIZE_PX);
        boardPane.setMaxSize(BOARD_SIZE_PX, BOARD_SIZE_PX);
        boardPane.setMinSize(BOARD_SIZE_PX, BOARD_SIZE_PX);
        boardPane.getStyleClass().add("game-grid"); // Applies the wood/parchment style

        // Draw the visual grid lines
        GridHandler gridHandler = new GridHandler(boardPane);

        // Container to center the board
        HBox centerContainer = new HBox(boardPane);
        centerContainer.setAlignment(Pos.CENTER);
        centerContainer.setPadding(new Insets(30));
        root.setCenter(centerContainer);

        // Right Panel: Available Fleet
        shipsPanel = createShipsPanel();
        root.setRight(shipsPanel);

        // Bottom Panel: Control Buttons
        VBox bottomPanel = createBottomPanel();
        root.setBottom(bottomPanel);

        // Initialize Logic Board
        board = new Board();
        draggableMaker = new DraggableMakerGrid(board);

        // Spawn draggable ships
        createDraggableShips();

        // Get screen dimensions
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

        // Scene setup
        Scene scene = new Scene(root, screenBounds.getWidth(), screenBounds.getHeight());

        // Load CSS Styles safely
        try {
            scene.getStylesheets().add(getClass().getResource("/com/battleship/view/styles.css").toExternalForm());
        } catch (Exception e) {
            System.err.println("Error: Could not load CSS style file.");
        }

        stage.setTitle("Battleship - Deployment Phase");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.setResizable(true);
        stage.setX(screenBounds.getMinX());
        stage.setY(screenBounds.getMinY());
        stage.show();
    }

    /**
     * Creates the top section with the game title and user instructions.
     */
    private VBox createTopPanel() {
        VBox panel = new VBox(15);
        panel.setAlignment(Pos.CENTER);
        panel.setPadding(new Insets(0, 0, 20, 0));

        Label title = new Label("BATALLA NAVAL ");
        title.getStyleClass().add("title-label");

        instructionLabel = new Label("Arrastra los barcos al tablero | Click derecho para rotar");
        instructionLabel.getStyleClass().add("subtitle-epic");

        panel.getChildren().addAll(title, instructionLabel);
        return panel;
    }

    /**
     * Creates the right sidebar displaying the fleet inventory.
     */
    private VBox createShipsPanel() {
        VBox panel = new VBox(25);
        panel.setPadding(new Insets(30));
        panel.setAlignment(Pos.TOP_CENTER);
        panel.setPrefWidth(320);
        panel.getStyleClass().add("sidebar-panel");

        Label title = new Label("FLOTA DISPONIBLE");
        title.getStyleClass().add("sidebar-title");

        VBox info = new VBox(12);
        info.getChildren().addAll(
                createShipInfoLabel("1x Galeón (4 casillas)"),
                createShipInfoLabel("2x Carabelas (3 casillas)"),
                createShipInfoLabel("3x Bergantines (2 casillas)"),
                createShipInfoLabel("4x Balandros (1 casilla)")
        );

        Label note = new Label("💡 Los barcos del mismo tipo\nestán apilados");
        note.setWrapText(true);
        note.getStyleClass().add("instruction-text");

        panel.getChildren().addAll(title, info, note);
        return panel;
    }

    /**
     * Helper to create a formatted label for the ship list.
     */
    private Label createShipInfoLabel(String text) {
        Label label = new Label("• " + text);
        label.getStyleClass().add("sidebar-text");
        return label;
    }

    /**
     * Creates the bottom section with Reset and Start buttons.
     */
    private VBox createBottomPanel() {
        VBox panel = new VBox(15);
        panel.setAlignment(Pos.CENTER);
        panel.setPadding(new Insets(25, 0, 0, 0));

        HBox buttonBox = new HBox(25);
        buttonBox.setAlignment(Pos.CENTER);

        Button resetButton = new Button("REINICIAR FLOTA");
        resetButton.getStyleClass().add("button");
        resetButton.setOnAction(e -> resetBoard());

        Button startButton = new Button(" ZARPAR A BATALLA ");
        startButton.getStyleClass().add("button");
        startButton.setOnAction(e -> startGame());

        buttonBox.getChildren().addAll(resetButton, startButton);
        panel.getChildren().add(buttonBox);

        return panel;
    }

    /**
     * Instantiates the ship objects and places them in the initial sidebar area.
     */
    private void createDraggableShips() {
        double startX = SHIP_INITIAL_X;
        double startY = 100;
        double spacing = 90;

        // Create ships according to game rules
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

    /**
     * Creates a single draggable ship and defines its rotation logic (Right-Click).
     */
    private void createDraggableShip(ShipType type, boolean horizontal, double x, double y) {
        Ship ship = new Ship(type, horizontal);
        ShipView shipView = new ShipView(ship);

        shipView.setLayoutX(x);
        shipView.setLayoutY(y);

        // Attach drag-and-drop logic
        draggableMaker.makeDraggable(shipView);

        // Right-click to rotate logic
        shipView.setOnMouseClicked(event -> {
            if (event.getButton() == javafx.scene.input.MouseButton.SECONDARY) {
                Ship rotatingShip = shipView.getShip();

                // Current pixel coordinates
                double currentX = shipView.getLayoutX();
                double currentY = shipView.getLayoutY();

                // If outside the board (sidebar), just rotate visually
                if (currentX >= BOARD_SIZE_PX || currentY >= BOARD_SIZE_PX) {
                    rotatingShip.setHorizontal(!rotatingShip.isHorizontal());
                    shipView.updateOrientation();
                    return;
                }

                // If inside board, we need to validate against the grid
                int gridCol = (int) (currentX / 50);
                int gridRow = (int) (currentY / 50);

                boolean wasPlaced = rotatingShip.getPositions() != null && !rotatingShip.getPositions().isEmpty();

                // 1) Remove from logic board temporarily
                if (wasPlaced) {
                    board.removeShip(rotatingShip);
                }

                // 2) Rotate logic and view
                boolean oldHorizontal = rotatingShip.isHorizontal();
                rotatingShip.setHorizontal(!oldHorizontal);
                shipView.updateOrientation();

                // 3) Check if new position is valid
                boolean canStay = board.canPlaceShip(rotatingShip, gridRow, gridCol);

                if (canStay) {
                    // 4) Place in new orientation
                    board.placeShip(rotatingShip, gridRow, gridCol);
                } else {
                    // 5) Invalid? Revert rotation
                    rotatingShip.setHorizontal(oldHorizontal);
                    shipView.updateOrientation();

                    // Put it back where it was
                    if (wasPlaced) {
                        board.placeShip(rotatingShip, gridRow, gridCol);
                    }
                }
            }
        });

        boardPane.getChildren().add(shipView);
    }

    /**
     * Resets the board, removing all ships and creating new ones.
     */
    private void resetBoard() {
        boardPane.getChildren().removeIf(node -> node instanceof ShipView);
        board = new Board();
        draggableMaker = new DraggableMakerGrid(board);
        createDraggableShips();

        instructionLabel.setText("Tablero reiniciado | Arrastra los barcos al tablero");
        // Maintain Cinzel font consistency
        instructionLabel.setStyle("-fx-font-family: 'Cinzel'; -fx-font-size: 18px; -fx-text-fill: #bdc3c7; -fx-effect: dropshadow(one-pass-box, black, 2, 0, 0, 1);");
    }

    /**
     * Validates ship placement and transitions to the main Game Controller.
     */
    private void startGame() {
        // Validate that all ships (10 total) are placed
        if (board.getShips().size() < 10) {
            instructionLabel.setText("⚠️ Debes colocar todos los barcos antes de iniciar");
            instructionLabel.setStyle("-fx-font-family: 'Cinzel'; -fx-font-size: 18px; -fx-text-fill: #e74c3c; -fx-font-weight: bold; -fx-effect: dropshadow(one-pass-box, black, 2, 0, 0, 1);");
            return;
        }

        instructionLabel.setText("⚓ Iniciando batalla...");
        instructionLabel.setStyle("-fx-font-family: 'Cinzel'; -fx-font-size: 18px; -fx-text-fill: #2ecc71; -fx-font-weight: bold; -fx-effect: dropshadow(one-pass-box, black, 2, 0, 0, 1);");

        // Create AI board and place ships randomly
        Board iaBoard = new Board();
        placeIAShips(iaBoard);

        // Transition to Game Screen
        Stage stage = (Stage) boardPane.getScene().getWindow();
        GameController gameController = new GameController();
        gameController.startGame(board, iaBoard);
    }

    /**
     * Randomly places the enemy fleet on the AI board.
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

            // Try to place each ship up to 100 times to avoid infinite loops
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