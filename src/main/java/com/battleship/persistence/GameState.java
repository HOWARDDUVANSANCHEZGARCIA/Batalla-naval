package com.battleship.persistence;

import com.battleship.model.Board;
import com.battleship.model.Ship;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Representa el estado completo de una partida de Batalla Naval.
 * Esta clase es serializable para poder guardar/cargar el juego.
 *
 * @author Howard Duvan Sanchez Garcia
 * @version 1.0
 * @since 2025-12-13
 */
public class GameState implements Serializable {

    private static final long serialVersionUID = 1L;

    // Tableros
    private Board playerBoard;
    private Board enemyBoard;

    // Control del juego
    private String currentTurn; // "PLAYER" o "ENEMY"
    private boolean gameOver;
    private String winner; // "PLAYER", "ENEMY", o null si no ha terminado

    // Estadísticas
    private int playerShipsSunk;
    private int enemyShipsSunk;
    private int totalPlayerShots;
    private int totalEnemyShots;
    private int playerHits;
    private int enemyHits;

    // Metadatos
    private LocalDateTime lastSaved;
    private String gameId; // Identificador único del juego

    /**
     * Constructor por defecto para un juego nuevo
     */
    public GameState() {
        this.currentTurn = "PLAYER"; // El jugador siempre empieza
        this.gameOver = false;
        this.winner = null;
        this.playerShipsSunk = 0;
        this.enemyShipsSunk = 0;
        this.totalPlayerShots = 0;
        this.totalEnemyShots = 0;
        this.playerHits = 0;
        this.enemyHits = 0;
        this.lastSaved = LocalDateTime.now();
        this.gameId = generateGameId();
    }

    /**
     * Constructor completo para crear un estado específico
     */
    public GameState(Board playerBoard, Board enemyBoard) {
        this();
        this.playerBoard = playerBoard;
        this.enemyBoard = enemyBoard;
    }

    /**
     * Genera un ID único para el juego basado en timestamp
     */
    private String generateGameId() {
        return "GAME_" + System.currentTimeMillis();
    }

    /**
     * Actualiza el timestamp de último guardado
     */
    public void updateLastSaved() {
        this.lastSaved = LocalDateTime.now();
    }

    /**
     * Cambia el turno entre jugador y enemigo
     */
    public void switchTurn() {
        this.currentTurn = currentTurn.equals("PLAYER") ? "ENEMY" : "PLAYER";
    }

    /**
     * Verifica si es el turno del jugador
     */
    public boolean isPlayerTurn() {
        return "PLAYER".equals(currentTurn);
    }

    /**
     * Incrementa el contador de disparos del jugador
     */
    public void incrementPlayerShots() {
        this.totalPlayerShots++;
    }

    /**
     * Incrementa el contador de disparos del enemigo
     */
    public void incrementEnemyShots() {
        this.totalEnemyShots++;
    }

    /**
     * Incrementa el contador de impactos del jugador
     */
    public void incrementPlayerHits() {
        this.playerHits++;
    }

    /**
     * Incrementa el contador de impactos del enemigo
     */
    public void incrementEnemyHits() {
        this.enemyHits++;
    }

    /**
     * Calcula la precisión del jugador
     */
    public double getPlayerAccuracy() {
        if (totalPlayerShots == 0) return 0.0;
        return (double) playerHits / totalPlayerShots * 100.0;
    }

    /**
     * Calcula la precisión del enemigo
     */
    public double getEnemyAccuracy() {
        if (totalEnemyShots == 0) return 0.0;
        return (double) enemyHits / totalEnemyShots * 100.0;
    }

    // ============== GETTERS Y SETTERS ==============

    public Board getPlayerBoard() {
        return playerBoard;
    }

    public void setPlayerBoard(Board playerBoard) {
        this.playerBoard = playerBoard;
    }

    public Board getEnemyBoard() {
        return enemyBoard;
    }

    public void setEnemyBoard(Board enemyBoard) {
        this.enemyBoard = enemyBoard;
    }

    public String getCurrentTurn() {
        return currentTurn;
    }

    public void setCurrentTurn(String currentTurn) {
        this.currentTurn = currentTurn;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public int getPlayerShipsSunk() {
        return playerShipsSunk;
    }

    public void setPlayerShipsSunk(int playerShipsSunk) {
        this.playerShipsSunk = playerShipsSunk;
    }

    public int getEnemyShipsSunk() {
        return enemyShipsSunk;
    }

    public void setEnemyShipsSunk(int enemyShipsSunk) {
        this.enemyShipsSunk = enemyShipsSunk;
    }

    public int getTotalPlayerShots() {
        return totalPlayerShots;
    }

    public void setTotalPlayerShots(int totalPlayerShots) {
        this.totalPlayerShots = totalPlayerShots;
    }

    public int getTotalEnemyShots() {
        return totalEnemyShots;
    }

    public void setTotalEnemyShots(int totalEnemyShots) {
        this.totalEnemyShots = totalEnemyShots;
    }

    public int getPlayerHits() {
        return playerHits;
    }

    public void setPlayerHits(int playerHits) {
        this.playerHits = playerHits;
    }

    public int getEnemyHits() {
        return enemyHits;
    }

    public void setEnemyHits(int enemyHits) {
        this.enemyHits = enemyHits;
    }

    public LocalDateTime getLastSaved() {
        return lastSaved;
    }

    public void setLastSaved(LocalDateTime lastSaved) {
        this.lastSaved = lastSaved;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    @Override
    public String toString() {
        return "GameState{" +
                "gameId='" + gameId + '\'' +
                ", currentTurn='" + currentTurn + '\'' +
                ", gameOver=" + gameOver +
                ", winner='" + winner + '\'' +
                ", playerShipsSunk=" + playerShipsSunk +
                ", enemyShipsSunk=" + enemyShipsSunk +
                ", lastSaved=" + lastSaved +
                '}';
    }
}
