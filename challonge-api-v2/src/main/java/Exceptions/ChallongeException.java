package main.java.Exceptions;

public class ChallongeException extends Exception {
    public ChallongeException(String message) {
        super(message);
    }

    public ChallongeException(String message, Throwable cause) {
        super(message, cause);
    }
}
