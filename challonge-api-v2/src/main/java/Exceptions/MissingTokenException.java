package main.java.Exceptions;

public class MissingTokenException extends ChallongeException {
    public MissingTokenException(Throwable cause) {
        super("Failed to retrieve tokens from API", cause);
    }
}
