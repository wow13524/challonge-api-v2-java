package main.java.Exceptions;

public class UnexpectedResponseException extends ChallongeException {
    public UnexpectedResponseException(Throwable cause) {
        super("Received an unexpected response from API", cause);
    }
}