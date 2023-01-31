package main.java.Exceptions;

public class AuthIOException extends ChallongeException {
    public AuthIOException(Throwable cause) {
        super("Failed to read/write to auth file", cause);
    }
}
