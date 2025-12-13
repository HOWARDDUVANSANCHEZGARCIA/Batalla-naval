package com.battleship.exceptions;

/**
 * Excepción marcada (checked) que se lanza cuando ocurre un error al cargar el juego.
 * Esta es una excepción propia del sistema de persistencia.
 *
 * Al ser una excepción marcada (extends Exception), obliga al programador a manejarla
 * explícitamente con try-catch o declarándola con throws.
 *
 * @author Howard Duvan Sanchez Garcia
 * @version 1.0
 * @since 2025-12-13
 */
public class LoadGameException extends Exception {

    private static final long serialVersionUID = 1L;

    private String filePath;
    private ErrorType errorType;

    /**
     * Tipos de errores al cargar
     */
    public enum ErrorType {
        FILE_NOT_FOUND,
        FILE_CORRUPTED,
        DESERIALIZATION_ERROR,
        INVALID_FORMAT,
        VERSION_MISMATCH,
        PERMISSION_DENIED,
        UNKNOWN
    }

    /**
     * Constructor con mensaje
     */
    public LoadGameException(String message) {
        super(message);
        this.errorType = ErrorType.UNKNOWN;
    }

    /**
     * Constructor con mensaje y causa
     */
    public LoadGameException(String message, Throwable cause) {
        super(message, cause);
        this.errorType = ErrorType.UNKNOWN;
    }

    /**
     * Constructor completo con tipo de error y ruta del archivo
     */
    public LoadGameException(String message, ErrorType errorType, String filePath) {
        super(message);
        this.errorType = errorType;
        this.filePath = filePath;
    }

    /**
     * Constructor completo con causa
     */
    public LoadGameException(String message, Throwable cause, ErrorType errorType, String filePath) {
        super(message, cause);
        this.errorType = errorType;
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }

    public ErrorType getErrorType() {
        return errorType;
    }

    @Override
    public String toString() {
        return "LoadGameException{" +
                "message='" + getMessage() + '\'' +
                ", errorType=" + errorType +
                ", filePath='" + filePath + '\'' +
                '}';
    }
}
