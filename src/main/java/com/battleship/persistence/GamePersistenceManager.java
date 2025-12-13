package com.battleship.persistence;

import com.battleship.exceptions.*;
import java.io.*;
import java.nio.file.*;
import java.util.logging.Level;
import java.util.logging.Logger;



public class GamePersistenceManager {

    private static final Logger LOGGER = Logger.getLogger(GamePersistenceManager.class.getName());

    // Singleton instance
    private static GamePersistenceManager instance;

    // Rutas de los archivos
    private static final String SAVE_DIRECTORY = "saves/";
    private static final String GAME_STATE_FILE = SAVE_DIRECTORY + "battleship_game.ser";
    private static final String PLAYER_DATA_FILE = SAVE_DIRECTORY + "player_data.txt";

    /**
     * Constructor privado para Singleton
     */
    private GamePersistenceManager() {
        initializeSaveDirectory();
    }

    /**
     * Obtiene la instancia única del gestor (Patrón Singleton)
     */
    public static synchronized GamePersistenceManager getInstance() {
        if (instance == null) {
            instance = new GamePersistenceManager();
        }
        return instance;
    }

    /**
     * Inicializa el directorio de guardado si no existe
     */
    private void initializeSaveDirectory() {
        try {
            Path savePath = Paths.get(SAVE_DIRECTORY);
            if (!Files.exists(savePath)) {
                Files.createDirectories(savePath);
                LOGGER.info("Directorio de guardado creado: " + SAVE_DIRECTORY);
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error al crear directorio de guardado", e);
        }
    }

    // ============== GUARDADO ==============

    /**
     * Guarda el estado completo del juego.
     *
     * @param gameState Estado del juego a guardar
     * @param playerData Datos del jugador a guardar
     * @throws SaveGameException Si ocurre un error al guardar
     */
    public void saveGame(GameState gameState, PlayerData playerData) throws SaveGameException {
        if (gameState == null) {
            throw new SaveGameException("GameState no puede ser null",
                    SaveGameException.ErrorType.UNKNOWN, GAME_STATE_FILE);
        }

        if (playerData == null) {
            throw new SaveGameException("PlayerData no puede ser null",
                    SaveGameException.ErrorType.UNKNOWN, PLAYER_DATA_FILE);
        }

        // Actualizar timestamps
        gameState.updateLastSaved();
        playerData.updateLastPlayed();

        // Guardar estado del juego (serializado)
        saveGameState(gameState);

        // Guardar datos del jugador (texto plano)
        savePlayerData(playerData);

        LOGGER.info("Juego guardado exitosamente");
    }

    /**
     * Guarda el estado del juego en archivo serializado
     */
    private void saveGameState(GameState gameState) throws SaveGameException {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(GAME_STATE_FILE))) {

            oos.writeObject(gameState);
            LOGGER.info("Estado del juego guardado en: " + GAME_STATE_FILE);

        } catch (NotSerializableException e) {
            throw new SaveGameException(
                    "El estado del juego contiene objetos no serializables",
                    e,
                    SaveGameException.ErrorType.SERIALIZATION_ERROR,
                    GAME_STATE_FILE
            );
        } catch (IOException e) {
            // Determinar el tipo de error específico
            SaveGameException.ErrorType errorType =
                    e.getMessage().contains("Permission denied")
                            ? SaveGameException.ErrorType.PERMISSION_DENIED
                            : SaveGameException.ErrorType.FILE_NOT_WRITABLE;

            throw new SaveGameException(
                    "Error al guardar el estado del juego",
                    e,
                    errorType,
                    GAME_STATE_FILE
            );
        }
    }

    /**
     * Guarda los datos del jugador en archivo de texto plano
     */
    private void savePlayerData(PlayerData playerData) throws SaveGameException {
        try {
            String content = playerData.toPlainText();
            Files.write(
                    Paths.get(PLAYER_DATA_FILE),
                    content.getBytes(),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING
            );
            LOGGER.info("Datos del jugador guardados en: " + PLAYER_DATA_FILE);

        } catch (IOException e) {
            SaveGameException.ErrorType errorType =
                    e.getMessage().contains("Permission denied")
                            ? SaveGameException.ErrorType.PERMISSION_DENIED
                            : SaveGameException.ErrorType.FILE_NOT_WRITABLE;

            throw new SaveGameException(
                    "Error al guardar los datos del jugador",
                    e,
                    errorType,
                    PLAYER_DATA_FILE
            );
        }
    }

    // ============== CARGA ==============

    /**
     * Carga el estado completo del juego guardado.
     *
     * @return GameState cargado desde el archivo
     * @throws LoadGameException Si ocurre un error al cargar o no existe partida guardada
     */
    public GameState loadGameState() throws LoadGameException {
        if (!hasSavedGame()) {
            throw new LoadGameException(
                    "No existe una partida guardada",
                    LoadGameException.ErrorType.FILE_NOT_FOUND,
                    GAME_STATE_FILE
            );
        }

        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(GAME_STATE_FILE))) {

            Object obj = ois.readObject();

            if (!(obj instanceof GameState)) {
                throw new LoadGameException(
                        "El archivo no contiene un GameState válido",
                        LoadGameException.ErrorType.INVALID_FORMAT,
                        GAME_STATE_FILE
                );
            }

            GameState gameState = (GameState) obj;
            LOGGER.info("Estado del juego cargado exitosamente");

            // Validar integridad básica
            validateGameState(gameState);

            return gameState;

        } catch (InvalidClassException | ClassNotFoundException e) {
            throw new LoadGameException(
                    "Versión incompatible del archivo guardado",
                    e,
                    LoadGameException.ErrorType.VERSION_MISMATCH,
                    GAME_STATE_FILE
            );
        } catch (StreamCorruptedException e) {
            throw new LoadGameException(
                    "El archivo de guardado está corrupto",
                    e,
                    LoadGameException.ErrorType.FILE_CORRUPTED,
                    GAME_STATE_FILE
            );
        } catch (IOException e) {
            throw new LoadGameException(
                    "Error al leer el archivo de guardado",
                    e,
                    LoadGameException.ErrorType.DESERIALIZATION_ERROR,
                    GAME_STATE_FILE
            );
        } catch (InvalidGameStateException e) {
            throw new LoadGameException(
                    "El estado del juego cargado es inválido: " + e.getMessage(),
                    e,
                    LoadGameException.ErrorType.INVALID_FORMAT,
                    GAME_STATE_FILE
            );
        }
    }

    /**
     * Carga los datos del jugador desde archivo de texto plano
     *
     * @return PlayerData cargado desde el archivo
     * @throws LoadGameException Si ocurre un error al cargar
     */
    public PlayerData loadPlayerData() throws LoadGameException {
        if (!Files.exists(Paths.get(PLAYER_DATA_FILE))) {
            LOGGER.info("No existe archivo de datos del jugador, creando uno nuevo");
            return new PlayerData();
        }

        try {
            String content = new String(Files.readAllBytes(Paths.get(PLAYER_DATA_FILE)));
            PlayerData playerData = PlayerData.fromPlainText(content);
            LOGGER.info("Datos del jugador cargados exitosamente");
            return playerData;

        } catch (IOException e) {
            throw new LoadGameException(
                    "Error al leer el archivo de datos del jugador",
                    e,
                    LoadGameException.ErrorType.FILE_CORRUPTED,
                    PLAYER_DATA_FILE
            );
        } catch (Exception e) {
            throw new LoadGameException(
                    "Error al parsear los datos del jugador",
                    e,
                    LoadGameException.ErrorType.INVALID_FORMAT,
                    PLAYER_DATA_FILE
            );
        }
    }

    // ============== VALIDACIONES ==============

    /**
     * Valida que el estado del juego cargado sea consistente
     */
    private void validateGameState(GameState gameState) throws InvalidGameStateException {
        if (gameState.getPlayerBoard() == null) {
            throw new InvalidGameStateException(
                    "El tablero del jugador es null",
                    gameState.getGameId(),
                    "playerBoard=null"
            );
        }

        if (gameState.getEnemyBoard() == null) {
            throw new InvalidGameStateException(
                    "El tablero del enemigo es null",
                    gameState.getGameId(),
                    "enemyBoard=null"
            );
        }

        if (gameState.getCurrentTurn() == null ||
                (!gameState.getCurrentTurn().equals("PLAYER") &&
                        !gameState.getCurrentTurn().equals("ENEMY"))) {
            throw new InvalidGameStateException(
                    "Turno inválido: " + gameState.getCurrentTurn(),
                    gameState.getGameId(),
                    "currentTurn=" + gameState.getCurrentTurn()
            );
        }
    }

    // ============== UTILIDADES ==============

    /**
     * Verifica si existe una partida guardada
     */
    public boolean hasSavedGame() {
        return Files.exists(Paths.get(GAME_STATE_FILE));
    }

    /**
     * Verifica si existen datos del jugador guardados
     */
    public boolean hasPlayerData() {
        return Files.exists(Paths.get(PLAYER_DATA_FILE));
    }

    /**
     * Elimina la partida guardada
     */
    public void deleteSavedGame() {
        try {
            Files.deleteIfExists(Paths.get(GAME_STATE_FILE));
            LOGGER.info("Partida guardada eliminada");
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Error al eliminar partida guardada", e);
        }
    }

    /**
     * Elimina todos los archivos de guardado
     */
    public void deleteAllSavedData() {
        deleteSavedGame();
        try {
            Files.deleteIfExists(Paths.get(PLAYER_DATA_FILE));
            LOGGER.info("Todos los datos guardados eliminados");
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Error al eliminar datos del jugador", e);
        }
    }

    /**
     * Obtiene información sobre la partida guardada
     */
    public String getSavedGameInfo() throws LoadGameException {
        if (!hasSavedGame()) {
            return "No hay partida guardada";
        }

        GameState gameState = loadGameState();
        return String.format(
                "Partida guardada: %s\nÚltima vez jugada: %s\nTurno: %s",
                gameState.getGameId(),
                gameState.getLastSaved(),
                gameState.getCurrentTurn()
        );
    }
}
