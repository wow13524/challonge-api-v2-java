package main.java.Exceptions;

public class MissingTokenException extends Exception {
    public MissingTokenException() {
        super("Failed to retrieve tokens from API");
    }
}
