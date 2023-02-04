package main.java;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import main.java.Exceptions.PermissionsScopeException;

final class Scopes {
    public final HashMap<Scope, Boolean> scopes;
    
    public Scopes(String raw) {
        ArrayList<String> scopes =
        new ArrayList<String>(Arrays.asList(raw.split(" ")));

        this.scopes = new HashMap<Scope, Boolean>();

        for (Scope scope : Scope.values()) {
            this.scopes.put(scope, scopes.indexOf(scope.name) != -1);
        }
    }

    public void requirePermissionScope(Scope scope) throws PermissionsScopeException {
        if (!this.scopes.get(scope)) {
            throw new PermissionsScopeException(scope.name);
        }
    }
    
    @Override
    public String toString() {
        ArrayList<String> scopes = new ArrayList<String>();
        for (Scope scope : Scope.values()) {
            if (this.scopes.get(scope)) {
                scopes.add(scope.name);
            }
        }
        
        return String.format(
            "Scope[%s]",
            String.join(", ", scopes)
        );
    }
}