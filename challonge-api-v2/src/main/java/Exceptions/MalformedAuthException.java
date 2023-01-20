package main.java.Exceptions;

public class MalformedAuthException extends Exception {
    public <T,U> MalformedAuthException(String fileName, String fieldName, Class<T> expectedClass, Class<U> valueClass) {
        super(String.format(
            "%s: expected '%s' to be of type '%s', got '%s'",
            fileName,
            fieldName,
            expectedClass.getSimpleName(),
            valueClass.getSimpleName()
        ));
    }
}
