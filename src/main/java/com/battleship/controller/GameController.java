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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * Controlador del juego con sistema de guardado automático integrado
 */
public class GameController {

    private boolean gameOver = false;
    private Board playerBoard;
    private Board iaBoard;
    private Rectangle[][] playerCells;
    private Rectangle[][] iaCells;
    private Label turnLabel;
    private boolean playerTurn = true;
    private static final int CELL_SIZE = 40;
    private GridPane iaGrid;
    private GridPane playerGrid;
    private boolean iaHunting = false;
    private int lastHitRow = -1;
    private int lastHitCol = -1;

    // Sistema de persistencia
    private GameState gameState;
    private PlayerData playerData;
    private GamePersistenceManager persistenceManager;

    /**
     * Inicia un juego NUEVO con tableros ya configurados
     */
    public void startGame(Board playerBoard, Board iaBoard) {
        this.playerBoard = playerBoard;
        this.iaBoard = iaBoard;

// Inicializar sistema de persistencia
        persistenceManager = GamePersistenceManager.getInstance();
        gameState = new GameState(playerBoard, iaBoard);
        playerData = new PlayerData("Player"); // El nickname se actualizará desde StartController

        Stage stage = new Stage();
        stage.setTitle("Batalla Naval - Juego");

        VBox root = new VBox(30);
        root.setPadding(new Insets(30));
        root.setAlignment(Pos.TOP_CENTER);
        root.setStyle("-fx-background-color: #2c3e50;");

        VBox topPanel = new VBox(15);
        topPanel.setAlignment(Pos.CENTER);

        Label title = new Label("🚢 BATALLA NAVAL 🚢");
        title.setStyle("-fx-font-size: 36px; -fx-font-weight: bold; -fx-text-fill: #ecf0f1;");

        turnLabel = new Label("🎯 Tu turno");
        turnLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: #2ecc71;");

        topPanel.getChildren().addAll(title, turnLabel);

        playerCells = new Rectangle[10][10];
        iaCells = new Rectangle[10][10];
        playerGrid = createBoardGrid(playerBoard, true);
        iaGrid = createBoardGrid(iaBoard, false);

        HBox boardsBox = new HBox(40, playerGrid, iaGrid);
        boardsBox.setAlignment(Pos.CENTER);

        root.getChildren().addAll(topPanel, boardsBox);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();

// Guardado inicial
        autoSaveGame();
    }

    /**
     * Carga un juego guardado previamente
     */
    public void loadGame(Stage stage, GameState loadedGameState, PlayerData loadedPlayerData) {
        this.gameState = loadedGameState;
        this.playerData = loadedPlayerData;
        this.playerBoard = loadedGameState.getPlayerBoard();
        this.iaBoard = loadedGameState.getEnemyBoard();
        this.playerTurn = loadedGameState.isPlayerTurn();
        this.gameOver = loadedGameState.isGameOver();

        persistenceManager = GamePersistenceManager.getInstance();

        stage.setTitle("Batalla Naval - Juego (Cargado)");

        VBox root = new VBox(30);
        root.setPadding(new Insets(30));
        root.setAlignment(Pos.TOP_CENTER);
        root.setStyle("-fx-background-color: #2c3e50;");

        VBox topPanel = new VBox(15);
        topPanel.setAlignment(Pos.CENTER);

        Label title = new Label("🚢 BATALLA NAVAL 🚢");
        title.setStyle("-fx-font-size: 36px; -fx-font-weight: bold; -fx-text-fill: #ecf0f1;");

        turnLabel = new Label(playerTurn ? "🎯 Tu turno" : "🤖 Turno de la IA");
        turnLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: " + (playerTurn ? "#2ecc71" : "#e67e22") + ";");

        topPanel.getChildren().addAll(title, turnLabel);

        playerCells = new Rectangle[10][10];
        iaCells = new Rectangle[10][10];
        playerGrid = createBoardGrid(playerBoard, true);
        iaGrid = createBoardGrid(iaBoard, false);

        HBox boardsBox = new HBox(40, playerGrid, iaGrid);
        boardsBox.setAlignment(Pos.CENTER);

        root.getChildren().addAll(topPanel, boardsBox);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();

// Si es turno de la IA, ejecutarla
        if (!playerTurn && !gameOver) {
            iaTurn();
        }
    }

    private GridPane createBoardGrid(Board board, boolean showShips) {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(2);
        grid.setVgap(2);

        Rectangle[][] cells = showShips ? playerCells : iaCells;

        for (int row = 0; row < board.getSize(); row++) {
            for (int col = 0; col < board.getSize(); col++) {

                Rectangle cell = new Rectangle(CELL_SIZE, CELL_SIZE);
                cells[row][col] = cell;

                cell.setFill(getColorForState(board.getCellState(row, col), showShips));
                cell.setStroke(Color.BLACK);

                if (!showShips) {
                    int r = row;
                    int c = col;
                    cell.setOnMouseClicked(e -> handlePlayerShot(r, c));
                }

                grid.add(cell, col, row);
            }
        }
        return grid;
    }

    private Color getColorForState(CellState state, boolean showShips) {
        switch (state) {
            case SHIP:
                return showShips ? Color.DARKGRAY : Color.LIGHTBLUE;
            case WATER:
                return Color.RED;
            case HIT:
                return Color.ORANGE;
            case SUNK:
                return Color.GREEN;
            default:
                return Color.LIGHTBLUE;
        }
    }

    private void handlePlayerShot(int row, int col) {
        if (!playerTurn || gameOver) return;

        CellState result = iaBoard.processShot(row, col);

// Actualizar estadísticas en gameState
        gameState.incrementPlayerShots();
        if (result == CellState.HIT || result == CellState.SUNK) {
            gameState.incrementPlayerHits();
        }

        refreshBoards();

// Guardar automáticamente después de cada disparo del jugador
        autoSaveGame();

        if (result == CellState.WATER) {
            playerTurn = false;
            gameState.switchTurn();
            turnLabel.setText("🤖 Turno de la IA");
            turnLabel.setStyle("-fx-text-fill: #e67e22;");
            iaTurn();
        }

        if (iaBoard.allShipsSunk()) {
            gameOver = true;
            gameState.setGameOver(true);
            gameState.setWinner("PLAYER");
            gameState.setEnemyShipsSunk(iaBoard.getShips().size());
            playerData.registerWin();
            turnLabel.setText("🏆 ¡GANASTE!");
            turnLabel.setStyle("-fx-text-fill: #2ecc71; -fx-font-size: 24px;");
            autoSaveGame();
        }
    }

    private void iaTurn() {
        new Thread(() -> {
            try { Thread.sleep(800); } catch (Exception ignored) {}

            int row, col;

            if (!iaHunting) {
                do {
                    row = (int)(Math.random() * 10);
                    col = (int)(Math.random() * 10);
                } while (alreadyShot(playerBoard, row, col));
            } else {
                int[][] directions = {
                        {-1, 0}, {1, 0}, {0, -1}, {0, 1}
                };

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

                if (!shotDone) {
                    iaHunting = false;
                    iaTurn();
                    return;
                }
            }

            CellState result = playerBoard.processShot(row, col);

// Actualizar estadísticas de la IA
            gameState.incrementEnemyShots();
            if (result == CellState.HIT || result == CellState.SUNK) {
                gameState.incrementEnemyHits();
            }

            int finalRow = row;
            int finalCol = col;

            Platform.runLater(() -> {
                refreshBoards();

// Guardar automáticamente después de cada disparo de la IA
                autoSaveGame();

                if (playerBoard.allShipsSunk()) {
                    gameOver = true;
                    gameState.setGameOver(true);
                    gameState.setWinner("ENEMY");
                    gameState.setPlayerShipsSunk(playerBoard.getShips().size());
                    playerData.registerLoss();
                    turnLabel.setText("💀 LA IA GANÓ");
                    turnLabel.setStyle("-fx-text-fill: #e74c3c; -fx-font-size: 24px;");
                    autoSaveGame();
                    return;
                }

                if (result == CellState.HIT) {
                    iaHunting = true;
                    lastHitRow = finalRow;
                    lastHitCol = finalCol;
                    iaTurn();
                    return;
                }

                if (result == CellState.SUNK) {
                    iaHunting = false;
                    lastHitRow = -1;
                    lastHitCol = -1;
                    iaTurn();
                    return;
                }

                playerTurn = true;
                gameState.switchTurn();
                turnLabel.setText("🎯 Tu turno");
                turnLabel.setStyle("-fx-text-fill: #2ecc71;");
            });

        }).start();
    }

    /**
     * 🔥 GUARDADO AUTOMÁTICO después de cada jugada
     */
    private void autoSaveGame() {
        try {
            persistenceManager.saveGame(gameState, playerData);
            System.out.println("✅ Partida guardada automáticamente");
        } catch (SaveGameException e) {
            System.err.println("⚠️ Error al guardar: " + e.getMessage());
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

    private void refreshBoards() {
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                playerCells[row][col].setFill(
                        getColorForState(playerBoard.getCellState(row, col), true)
                );

                iaCells[row][col].setFill(
                        getColorForState(iaBoard.getCellState(row, col), false)
                );
            }
        }
    }

    /**
     * Actualiza el nickname del jugador (llamado desde StartController)
     */
    public void setPlayerNickname(String nickname) {
        if (playerData != null) {
            playerData.setNickname(nickname);
        }
    }

    @FXML
    public void initialize() {
        System.out.println("Controlador iniciado");
    }
}
