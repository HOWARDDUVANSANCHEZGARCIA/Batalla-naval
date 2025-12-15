package com.battleship.exceptions;

/**
 * Excepción no marcada (unchecked) que se lanza cuando el estado del juego es inválido.
 *
 * Al extender de RuntimeException, es una excepción no marcada, por lo que NO es
 * obligatorio capturarla o declararla. Se usa para errores de programación o
 * estados inconsistentes que no deberían ocurrir en ejecución normal.
 *
 * Ejemplos:
 * - Tablero con estado inconsistente
 * - Barcos en posiciones imposibles
 * - Turno inválido
 *
 * @author Howard Duvan Sanchez Garcia
 * @version 1.0
 * @since 2025-12-13
 */
public class InvalidGameStateException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String gameId;
    private String invalidState;

    /**
     * Constructor con mensaje
     */
    public InvalidGameStateException(String message) {
        super(message);
    }

    /**
     * Constructor con mensaje y causa
     */
    public InvalidGameStateException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor con detalles del estado inválido
     */
    public InvalidGameStateException(String message, String gameId, String invalidState) {
        super(message);
        this.gameId = gameId;
        this.invalidState = invalidState;
    }

    public String getGameId() {
        return gameId;
    }

    public String getInvalidState() {
        return invalidState;
    }

    @Override
    public String toString() {
        return "InvalidGameStateException{" +
                "message='" + getMessage() + '\'' +
                ", gameId='" + gameId + '\'' +
                ", invalidState='" + invalidState + '\'' +
                '}';
    }
}
