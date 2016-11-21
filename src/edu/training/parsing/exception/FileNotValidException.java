package edu.training.parsing.exception;

/**
 * Created by Roman on 19.11.2016.
 */
public class FileNotValidException extends Exception {
    public FileNotValidException() {
    }

    public FileNotValidException(String message) {
        super(message);
    }

    public FileNotValidException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileNotValidException(Throwable cause) {
        super(cause);
    }

    public FileNotValidException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
