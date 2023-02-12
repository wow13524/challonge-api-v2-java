package main.java.Exceptions;

public class UnexpectedTypeException extends ChallongeException {
    public <T,U> UnexpectedTypeException(Class<T> expectedClass, Class<U> valueClass) {
        super(String.format(
            "expected type '%s', got '%s'",
            expectedClass.getSimpleName(),
            valueClass.getSimpleName()
        ));
    }

    public <T,U> UnexpectedTypeException(Class<T> expectedClass, Class<U> valueClass, String name) {
        super(String.format(
            "expected '%s' to be of type '%s', got '%s'",
            name,
            expectedClass.getSimpleName(),
            valueClass.getSimpleName()
        ));
    }
}