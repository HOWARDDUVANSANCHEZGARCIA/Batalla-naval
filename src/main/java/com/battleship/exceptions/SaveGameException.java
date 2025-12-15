package com.battleship.exceptions;

/**
 * Excepción marcada (checked) que se lanza cuando ocurre un error al guardar el juego.
 * Esta es una excepción propia del sistema de persistencia.
 *
 * Al ser una excepción marcada (extends Exception), obliga al programador a manejarla
 * explícitamente con try-catch o declarándola con throws.
 *
 * @author Howard Duvan Sanchez Garcia
 * @version 1.0
 * @since 2025-12-13
 */
public class SaveGameException extends Exception {

    private static final long serialVersionUID = 1L;

    private String filePath;
    private ErrorType errorType;

    /**
     * Tipos de errores al guardar
     */
    public enum ErrorType {
        FILE_NOT_WRITABLE,
        DISK_FULL,
        SERIALIZATION_ERROR,
        PERMISSION_DENIED,
        UNKNOWN
    }

    /**
     * Constructor con mensaje
     */
    public SaveGameException(String message) {
        super(message);
        this.errorType = ErrorType.UNKNOWN;
    }

    /**
     * Constructor con mensaje y causa
     */
    public SaveGameException(String message, Throwable cause) {
        super(message, cause);
        this.errorType = ErrorType.UNKNOWN;
    }

    /**
     * Constructor completo con tipo de error y ruta del archivo
     */
    public SaveGameException(String message, ErrorType errorType, String filePath) {
        super(message);
        this.errorType = errorType;
        this.filePath = filePath;
    }

    /**
     * Constructor completo con causa
     */
    public SaveGameException(String message, Throwable cause, ErrorType errorType, String filePath) {
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
        return "SaveGameException{" +
                "message='" + getMessage() + '\'' +
                ", errorType=" + errorType +
                ", filePath='" + filePath + '\'' +
                '}';
    }
}
