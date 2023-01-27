package main.java.Exceptions;

public class PermissionsScopeException extends Exception {
    public PermissionsScopeException(String scope) {
        super(String.format("Missing scope '%s'", scope));
    }
}
