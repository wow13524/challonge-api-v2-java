package main.java.Exceptions;

public class UnexpectedResponseException extends ChallongeException {
    public UnexpectedResponseException(Throwable cause, int code) {
        super(
            String.format(
                "Received an unexpected response from API (%d)",
                code
            ),
            cause
            );
    }
}