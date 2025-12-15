package com.battleship.exceptions;

/**
 * Excepción no marcada (unchecked) que se lanza cuando los datos guardados están corruptos
 * de manera irrecuperable.
 *
 * Al extender de RuntimeException, es una excepción no marcada. Se usa para indicar
 * que los datos están tan dañados que no se pueden recuperar y el juego debe reiniciarse.
 *
 * @author Howard Duvan Sanchez Garcia
 * @version 1.0
 * @since 2025-12-13
 */
public class GameDataCorruptedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String fileName;
    private String corruptionDetails;

    /**
     * Constructor con mensaje
     */
    public GameDataCorruptedException(String message) {
        super(message);
    }

    /**
     * Constructor con mensaje y causa
     */
    public GameDataCorruptedException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor con detalles de la corrupción
     */
    public GameDataCorruptedException(String message, String fileName, String corruptionDetails) {
        super(message);
        this.fileName = fileName;
        this.corruptionDetails = corruptionDetails;
    }

    /**
     * Constructor completo
     */
    public GameDataCorruptedException(String message, Throwable cause, String fileName, String corruptionDetails) {
        super(message, cause);
        this.fileName = fileName;
        this.corruptionDetails = corruptionDetails;
    }

    public String getFileName() {
        return fileName;
    }

    public String getCorruptionDetails() {
        return corruptionDetails;
    }

    @Override
    public String toString() {
        return "GameDataCorruptedException{" +
                "message='" + getMessage() + '\'' +
                ", fileName='" + fileName + '\'' +
                ", corruptionDetails='" + corruptionDetails + '\'' +
                '}';
    }
}
