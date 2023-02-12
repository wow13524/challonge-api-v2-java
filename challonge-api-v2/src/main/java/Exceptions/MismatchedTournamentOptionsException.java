package main.java.Exceptions;

public class MismatchedTournamentOptionsException extends ChallongeException {
    public MismatchedTournamentOptionsException(String expected, String value) {
        super(String.format(
            "Expected TournamentOptions for '%s', got '%s'",
            expected,
            value
        ));
    }
}