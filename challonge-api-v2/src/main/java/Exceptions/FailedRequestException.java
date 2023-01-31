package main.java.Exceptions;

public class FailedRequestException extends ChallongeException {
    public FailedRequestException(Throwable cause) {
        super("Failed to perform API request", cause);
    }
}
