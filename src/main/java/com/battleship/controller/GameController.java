package com.battleship.controller;

import com.battleship.model.Board;
import com.battleship.model.CellState;
import com.battleship.persistence.GamePersistenceManager;
import com.battleship.persistence.GameState;
import com.battleship.persistence.PlayerData;
import com.battleship.exceptions.SaveGameException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;

/**
 * Main Game Controller for Battleship.
 * <p>
 * Manages visual game logic, user-AI interaction, real-time board updates,
 * and the auto-save system. Implements a GUI based on StackPanes to layer emojis over cells.
 * </p>
 *
 * @version 1.0.0
 * @author Martin
 */
public class GameController {

    // --- Game Logic ---
    private boolean gameOver = false;
    private Board playerBoard;
    private Board iaBoard;
    private boolean playerTurn = true;
    private boolean iaHunting = false; // AI state for "Hunt" mode
    private int lastHitRow = -1;
    private int lastHitCol = -1;

    // --- UI Elements ---
    private static final int CELL_SIZE = 40;
    private GridPane iaGrid;
    private GridPane playerGrid;
    private Label turnLabel;

    // Visual matrices to update state without redrawing everything
    private Rectangle[][] playerCells;
    private Rectangle[][] iaCells;
    private Label[][] playerLabels;
    private Label[][] iaLabels;

    // --- Data Persistence ---
    private GameState gameState;
    private PlayerData playerData;
    private GamePersistenceManager persistenceManager;

    /**
     * Starts a new game by setting up boards and the graphical interface.
     *
     * @param playerBoard Player board with ships already positioned.
     * @param iaBoard AI board generated randomly.
     */
    public void startGame(Board playerBoard, Board iaBoard) {
        this.playerBoard = playerBoard;
        this.iaBoard = iaBoard;

        // Initialize persistence system
        persistenceManager = GamePersistenceManager.getInstance();
        gameState = new GameState(playerBoard, iaBoard);
        playerData = new PlayerData("Player");

        Stage stage = new Stage();
        stage.setTitle("Battleship - In Combat");

        // Main layout configuration with themed background
        VBox root = new VBox(30);
        root.setPadding(new Insets(30));
        root.setAlignment(Pos.TOP_CENTER);
        root.getStyleClass().add("battle-background");

        // Top panel with Title and Turn label
        VBox topPanel = new VBox(15);
        topPanel.setAlignment(Pos.CENTER);

        Label title = new Label(" BATTLESHIP ");
        title.getStyleClass().add("title-label");

        turnLabel = new Label(playerTurn ? "AYE AYE, CAPTAIN" : "ENEMY IS FIRING...");
        turnLabel.setStyle("-fx-font-family: 'Cinzel'; -fx-font-size: 24px; -fx-text-fill: " +
                (playerTurn ? "#2ecc71" : "#e67e22") +
                "; -fx-effect: dropshadow(one-pass-box, black, 3, 0, 0, 1);");

        topPanel.getChildren().addAll(title, turnLabel);

        // Initialize visual matrices
        playerCells = new Rectangle[10][10];
        iaCells = new Rectangle[10][10];
        playerLabels = new Label[10][10];
        iaLabels = new Label[10][10];

        // Create visual grids
        playerGrid = createBoardGrid(playerBoard, true);
        iaGrid = createBoardGrid(iaBoard, false);

        HBox boardsBox = new HBox(40, playerGrid, iaGrid);
        boardsBox.setAlignment(Pos.CENTER);

        root.getChildren().addAll(topPanel, boardsBox);

        // Scene configuration and CSS loading
        Scene scene = new Scene(root);
        try {
            scene.getStylesheets().add(getClass().getResource("/com/battleship/view/styles.css").toExternalForm());
        } catch (Exception e) {
            System.err.println("Error: Could not load CSS style file.");
        }
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();

        // Initial auto-save
        autoSaveGame();
    }

    /**
     * Loads a previously saved game and restores the game state.
     *
     * @param stage Main stage where the game will be rendered.
     * @param loadedGameState Game state retrieved from the serialized file.
     * @param loadedPlayerData Player data retrieved.
     */
    public void loadGame(Stage stage, GameState loadedGameState, PlayerData loadedPlayerData) {
        this.gameState = loadedGameState;
        this.playerData = loadedPlayerData;
        this.playerBoard = loadedGameState.getPlayerBoard();
        this.iaBoard = loadedGameState.getEnemyBoard();
        this.playerTurn = loadedGameState.isPlayerTurn();
        this.gameOver = loadedGameState.isGameOver();

        persistenceManager = GamePersistenceManager.getInstance();

        stage.setTitle("Battleship - Game (Loaded)");

        VBox root = new VBox(30);
        root.setPadding(new Insets(30));
        root.setAlignment(Pos.TOP_CENTER);
        root.getStyleClass().add("battle-background");

        VBox topPanel = new VBox(15);
        topPanel.setAlignment(Pos.CENTER);

        Label title = new Label("BATTLESHIP ");
        title.getStyleClass().add("title-label");

        turnLabel = new Label(playerTurn ? "AYE AYE, CAPTAIN" : "ENEMY IS FIRING...");
        turnLabel.setStyle("-fx-font-family: 'Cinzel'; -fx-font-size: 24px; -fx-text-fill: " +
                (playerTurn ? "#2ecc71" : "#e67e22") +
                "; -fx-effect: dropshadow(one-pass-box, black, 3, 0, 0, 1);");

        topPanel.getChildren().addAll(title, turnLabel);

        playerCells = new Rectangle[10][10];
        iaCells = new Rectangle[10][10];
        playerLabels = new Label[10][10];
        iaLabels = new Label[10][10];

        playerGrid = createBoardGrid(playerBoard, true);
        iaGrid = createBoardGrid(iaBoard, false);

        HBox boardsBox = new HBox(40, playerGrid, iaGrid);
        boardsBox.setAlignment(Pos.CENTER);

        root.getChildren().addAll(topPanel, boardsBox);

        Scene scene = new Scene(root);
        try {
            scene.getStylesheets().add(getClass().getResource("/com/battleship/view/styles.css").toExternalForm());
        } catch (Exception e) {
            System.err.println("Error: Could not load CSS style file.");
        }
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();

        // If loaded during AI turn, resume AI logic
        if (!playerTurn && !gameOver) {
            iaTurn();
        }
    }

    /**
     * Generates the visual board grid using StackPanes.
     * Each cell contains a Rectangle (background) and a Label (emoji).
     *
     * @param board The logical board model.
     * @param showShips Indicates if ships should be shown (True for player, False for AI).
     * @return Configured GridPane with cells.
     */
    private GridPane createBoardGrid(Board board, boolean showShips) {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.getStyleClass().add("game-grid");

        Rectangle[][] cells = showShips ? playerCells : iaCells;
        Label[][] labels = showShips ? playerLabels : iaLabels;

        for (int row = 0; row < board.getSize(); row++) {
            for (int col = 0; col < board.getSize(); col++) {

                // Container for layers (Background + Emoji)
                StackPane cellContainer = new StackPane();

                // Layer 1: Background
                Rectangle cell = new Rectangle(CELL_SIZE, CELL_SIZE);
                cell.getStyleClass().add("grid-cell");
                cell.setFill(getColorForState(board.getCellState(row, col), showShips));
                cells[row][col] = cell;

                // Layer 2: Emoji (Text)
                Label emojiLabel = new Label();
                emojiLabel.setAlignment(Pos.CENTER);
                labels[row][col] = emojiLabel;

                cellContainer.getChildren().addAll(cell, emojiLabel);

                // Interaction events (only on enemy board)
                if (!showShips) {
                    int r = row;
                    int c = col;
                    cellContainer.setOnMouseClicked(e -> handlePlayerShot(r, c));
                    cellContainer.setCursor(javafx.scene.Cursor.HAND);
                }

                grid.add(cellContainer, col, row);
            }
        }
        return grid;
    }

    /**
     * Gets the emoji corresponding to the cell state.
     *
     * @param state Current state of the cell.
     * @return String containing the emoji or empty.
     */
    private String getEmojiForState(CellState state) {
        switch (state) {
            case WATER: return "❌";  // Water / Miss
            case HIT:   return "💣";  // Hit
            case SUNK:  return "🔥";  // Sunk
            default:    return "";
        }
    }

    /**
     * Determines the cell background color based on its state.
     */
    private Color getColorForState(CellState state, boolean showShips) {
        switch (state) {
            case SHIP:
                // Ships visible only to the player
                return showShips ? Color.web("#5D4037", 0.9) : Color.TRANSPARENT;
            case WATER:
                // Pale blue transparent to highlight the red X
                return Color.web("#E0F7FA", 0.6);
            case HIT:
                // Soft red to highlight the black bomb
                return Color.web("#FFCDD2", 0.8);
            case SUNK:
                // Dark color to highlight the fire
                return Color.web("#1A1A1A", 0.95);
            default:
                return Color.TRANSPARENT;
        }
    }

    /**
     * Visually updates both boards reflecting changes in the model.
     * Applies shadow effects and specific colors to emojis.
     */
    private void refreshBoards() {
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                CellState pState = playerBoard.getCellState(row, col);
                CellState iState = iaBoard.getCellState(row, col);

                // 1. Update backgrounds
                playerCells[row][col].setFill(getColorForState(pState, true));
                iaCells[row][col].setFill(getColorForState(iState, false));

                // 2. Update emoji text
                playerLabels[row][col].setText(getEmojiForState(pState));
                iaLabels[row][col].setText(getEmojiForState(iState));

                // 3. Apply specific styles (Shadows and Colors)
                applyEmojiStyle(playerLabels[row][col], pState);
                applyEmojiStyle(iaLabels[row][col], iState);
            }
        }
    }

    /**
     * Applies dynamic CSS styles to emojis to improve contrast.
     */
    private void applyEmojiStyle(Label label, CellState state) {
        switch (state) {
            case WATER: // Red X with thin black border
                label.setStyle("-fx-font-size: 26px; -fx-text-fill: red; -fx-effect: dropshadow(one-pass-box, black, 2, 1.0, 0, 0);");
                break;
            case HIT: // Black Bomb with white glow
                label.setStyle("-fx-font-size: 28px; -fx-text-fill: black; -fx-effect: dropshadow(gaussian, white, 8, 0.8, 0, 0);");
                break;
            case SUNK: // Intense RED Fire with black shadow
                label.setStyle("-fx-font-size: 28px; -fx-text-fill: #ff2400; -fx-effect: dropshadow(one-pass-box, black, 3, 1.0, 0, 0);");
                break;
            default:
                label.setStyle("");
        }
    }

    /**
     * Processes the shot fired by the player.
     *
     * @param row Selected row.
     * @param col Selected column.
     */
    private void handlePlayerShot(int row, int col) {
        if (!playerTurn || gameOver) return;

        // Avoid shooting at already attacked cells
        if (alreadyShot(iaBoard, row, col)) return;

        CellState result = iaBoard.processShot(row, col);

        // Update statistics
        gameState.incrementPlayerShots();
        if (result == CellState.HIT || result == CellState.SUNK) {
            gameState.incrementPlayerHits();
        }

        refreshBoards();
        autoSaveGame();

        // If missed, AI turn
        if (result == CellState.WATER) {
            playerTurn = false;
            gameState.switchTurn();
            turnLabel.setText("⚠️ ENEMY TURN");
            turnLabel.setStyle("-fx-font-family: 'Cinzel'; -fx-font-size: 24px; -fx-text-fill: #e67e22; -fx-effect: dropshadow(one-pass-box, black, 3, 0, 0, 1);");
            iaTurn();
        }

        // Check victory condition
        if (iaBoard.allShipsSunk()) {
            gameOver = true;
            gameState.setGameOver(true);
            gameState.setWinner("PLAYER");
            gameState.setEnemyShipsSunk(iaBoard.getShips().size());
            playerData.registerWin();
            turnLabel.setText("🏆 VICTORY! ENEMY FLEET SUNK");
            turnLabel.setStyle("-fx-font-family: 'Cinzel'; -fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: #ffd700; -fx-effect: dropshadow(three-pass-box, black, 10, 0, 0, 0);");
            autoSaveGame();
        }
    }

    /**
     * Artificial Intelligence turn logic.
     * Runs on a separate thread to avoid freezing the UI.
     */
    private void iaTurn() {
        new Thread(() -> {
            try { Thread.sleep(800); } catch (Exception ignored) {} // Simulate thinking

            int row, col;

            // Simple hunting and shooting strategy
            if (!iaHunting) {
                // Random shot if no previous targets
                do {
                    row = (int)(Math.random() * 10);
                    col = (int)(Math.random() * 10);
                } while (alreadyShot(playerBoard, row, col));
            } else {
                // Try shooting around the last hit
                int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
                boolean shotDone = false;
                row = lastHitRow;
                col = lastHitCol;

                for (int[] d : directions) {
                    int r = lastHitRow + d[0];
                    int c = lastHitCol + d[1];

                    if (isValid(r, c) && !alreadyShot(playerBoard, r, c)) {
                        row = r;
                        col = c;
                        shotDone = true;
                        break;
                    }
                }

                // If no valid shots around, go back to random
                if (!shotDone) {
                    iaHunting = false;
                    iaTurn();
                    return;
                }
            }

            // Execute shot
            CellState result = playerBoard.processShot(row, col);

            gameState.incrementEnemyShots();
            if (result == CellState.HIT || result == CellState.SUNK) {
                gameState.incrementEnemyHits();
            }

            int finalRow = row;
            int finalCol = col;

            // Update UI on the main JavaFX thread
            Platform.runLater(() -> {
                refreshBoards();
                autoSaveGame();

                // Check player defeat
                if (playerBoard.allShipsSunk()) {
                    gameOver = true;
                    gameState.setGameOver(true);
                    gameState.setWinner("ENEMY");
                    gameState.setPlayerShipsSunk(playerBoard.getShips().size());
                    playerData.registerLoss();
                    turnLabel.setText("💀 DEFEAT... YOUR FLEET HAS FALLEN");
                    turnLabel.setStyle("-fx-font-family: 'Cinzel'; -fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: #c0392b; -fx-effect: dropshadow(three-pass-box, black, 10, 0, 0, 0);");
                    autoSaveGame();
                    return;
                }

                if (result == CellState.HIT) {
                    iaHunting = true;
                    lastHitRow = finalRow;
                    lastHitCol = finalCol;
                    iaTurn(); // AI shoots again if hits
                    return;
                }

                if (result == CellState.SUNK) {
                    iaHunting = false;
                    lastHitRow = -1;
                    lastHitCol = -1;
                    iaTurn(); // AI shoots again if sinks
                    return;
                }

                // Return to player turn
                playerTurn = true;
                gameState.switchTurn();
                turnLabel.setText("🎯 YOUR TURN, CAPTAIN");
                turnLabel.setStyle("-fx-font-family: 'Cinzel'; -fx-font-size: 24px; -fx-text-fill: #2ecc71; -fx-effect: dropshadow(one-pass-box, black, 3, 0, 0, 1);");
            });

        }).start();
    }

    /**
     * Automatically saves the current game state.
     */
    private void autoSaveGame() {
        try {
            persistenceManager.saveGame(gameState, playerData);
            System.out.println("✅ Game saved automatically");
        } catch (SaveGameException e) {
            System.err.println("⚠️ Error saving game: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private boolean alreadyShot(Board board, int row, int col) {
        CellState state = board.getCellState(row, col);
        return state == CellState.WATER || state == CellState.HIT || state == CellState.SUNK;
    }

    private boolean isValid(int row, int col) {
        return row >= 0 && row < 10 && col >= 0 && col < 10;
    }

    /**
     * Sets the player's nickname in the persistence data.
     */
    public void setPlayerNickname(String nickname) {
        if (playerData != null) {
            playerData.setNickname(nickname);
        }
    }

    @FXML
    public void initialize() {
        System.out.println("Game Controller initialized successfully.");
    }
}