package com.battleship.persistence;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Representa los datos simples del jugador que se almacenarán en archivo plano.
 * Según el enunciado, debe incluir: nickname y cantidad de barcos hundidos.
 *
 * Esta clase NO es Serializable porque se guardará en formato de texto plano.
 *
 * @author Howard Duvan Sanchez Garcia
 * @version 1.0
 * @since 2025-12-13
 */
public class PlayerData {

    private String nickname;
    private int playerShipsDestroyed; // Barcos del enemigo que el jugador ha hundido
    private int enemyShipsDestroyed;  // Barcos del jugador que el enemigo ha hundido
    private LocalDateTime lastPlayed;
    private int gamesPlayed;
    private int gamesWon;
    private int gamesLost;

    /**
     * Constructor vacío
     */
    public PlayerData() {
        this.nickname = "Player";
        this.playerShipsDestroyed = 0;
        this.enemyShipsDestroyed = 0;
        this.lastPlayed = LocalDateTime.now();
        this.gamesPlayed = 0;
        this.gamesWon = 0;
        this.gamesLost = 0;
    }

    /**
     * Constructor con nickname
     */
    public PlayerData(String nickname) {
        this();
        this.nickname = nickname;
    }

    /**
     * Constructor completo para crear desde archivo
     */
    public PlayerData(String nickname, int playerShipsDestroyed, int enemyShipsDestroyed,
                      LocalDateTime lastPlayed, int gamesPlayed, int gamesWon, int gamesLost) {
        this.nickname = nickname;
        this.playerShipsDestroyed = playerShipsDestroyed;
        this.enemyShipsDestroyed = enemyShipsDestroyed;
        this.lastPlayed = lastPlayed;
        this.gamesPlayed = gamesPlayed;
        this.gamesWon = gamesWon;
        this.gamesLost = gamesLost;
    }

    /**
     * Convierte los datos a formato de texto plano para guardar en archivo.
     * Formato: Una línea por campo con el formato "CAMPO=VALOR"
     *
     * @return String con todos los datos en formato de texto plano
     */
    public String toPlainText() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        StringBuilder sb = new StringBuilder();

        sb.append("NICKNAME=").append(nickname).append("\n");
        sb.append("PLAYER_SHIPS_DESTROYED=").append(playerShipsDestroyed).append("\n");
        sb.append("ENEMY_SHIPS_DESTROYED=").append(enemyShipsDestroyed).append("\n");
        sb.append("LAST_PLAYED=").append(lastPlayed.format(formatter)).append("\n");
        sb.append("GAMES_PLAYED=").append(gamesPlayed).append("\n");
        sb.append("GAMES_WON=").append(gamesWon).append("\n");
        sb.append("GAMES_LOST=").append(gamesLost).append("\n");

        return sb.toString();
    }

    /**
     * Crea un objeto PlayerData desde texto plano.
     * Parsea el formato "CAMPO=VALOR" línea por línea.
     *
     * @param plainText Texto en formato plano
     * @return PlayerData con los datos parseados
     */
    public static PlayerData fromPlainText(String plainText) {
        PlayerData data = new PlayerData();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String[] lines = plainText.split("\n");
        for (String line : lines) {
            line = line.trim();
            if (line.isEmpty() || !line.contains("=")) continue;

            String[] parts = line.split("=", 2);
            if (parts.length != 2) continue;

            String key = parts[0].trim();
            String value = parts[1].trim();

            try {
                switch (key) {
                    case "NICKNAME":
                        data.nickname = value;
                        break;
                    case "PLAYER_SHIPS_DESTROYED":
                        data.playerShipsDestroyed = Integer.parseInt(value);
                        break;
                    case "ENEMY_SHIPS_DESTROYED":
                        data.enemyShipsDestroyed = Integer.parseInt(value);
                        break;
                    case "LAST_PLAYED":
                        data.lastPlayed = LocalDateTime.parse(value, formatter);
                        break;
                    case "GAMES_PLAYED":
                        data.gamesPlayed = Integer.parseInt(value);
                        break;
                    case "GAMES_WON":
                        data.gamesWon = Integer.parseInt(value);
                        break;
                    case "GAMES_LOST":
                        data.gamesLost = Integer.parseInt(value);
                        break;
                }
            } catch (Exception e) {
                System.err.println("Error parsing line: " + line + " - " + e.getMessage());
            }
        }

        return data;
    }

    /**
     * Actualiza el timestamp de última jugada
     */
    public void updateLastPlayed() {
        this.lastPlayed = LocalDateTime.now();
    }

    /**
     * Incrementa el contador de juegos jugados
     */
    public void incrementGamesPlayed() {
        this.gamesPlayed++;
    }

    /**
     * Registra una victoria
     */
    public void registerWin() {
        this.gamesWon++;
        incrementGamesPlayed();
    }

    /**
     * Registra una derrota
     */
    public void registerLoss() {
        this.gamesLost++;
        incrementGamesPlayed();
    }

    /**
     * Calcula el porcentaje de victorias
     */
    public double getWinRate() {
        if (gamesPlayed == 0) return 0.0;
        return (double) gamesWon / gamesPlayed * 100.0;
    }

    // ============== GETTERS Y SETTERS ==============

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getPlayerShipsDestroyed() {
        return playerShipsDestroyed;
    }

    public void setPlayerShipsDestroyed(int playerShipsDestroyed) {
        this.playerShipsDestroyed = playerShipsDestroyed;
    }

    public int getEnemyShipsDestroyed() {
        return enemyShipsDestroyed;
    }

    public void setEnemyShipsDestroyed(int enemyShipsDestroyed) {
        this.enemyShipsDestroyed = enemyShipsDestroyed;
    }

    public LocalDateTime getLastPlayed() {
        return lastPlayed;
    }

    public void setLastPlayed(LocalDateTime lastPlayed) {
        this.lastPlayed = lastPlayed;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public int getGamesWon() {
        return gamesWon;
    }

    public void setGamesWon(int gamesWon) {
        this.gamesWon = gamesWon;
    }

    public int getGamesLost() {
        return gamesLost;
    }

    public void setGamesLost(int gamesLost) {
        this.gamesLost = gamesLost;
    }

    @Override
    public String toString() {
        return "PlayerData{" +
                "nickname='" + nickname + '\'' +
                ", playerShipsDestroyed=" + playerShipsDestroyed +
                ", enemyShipsDestroyed=" + enemyShipsDestroyed +
                ", gamesPlayed=" + gamesPlayed +
                ", winRate=" + String.format("%.2f", getWinRate()) + "%" +
                '}';
    }
}
