package main.java;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import main.java.Exceptions.PermissionsScopeException;

final record ScopeField(String name) {}

final class Scope {
    public static final ScopeField
        ME = new ScopeField("me"),
        TOURNAMENTS_READ = new ScopeField("tournaments:read"),
        TOURNAMENTS_WRITE = new ScopeField("tournaments:write"),
        MATCHES_READ = new ScopeField("matches:read"),
        MATCHES_WRITE = new ScopeField("matches:write"),
        PARTICIPANTS_READ = new ScopeField("participants:read"),
        PARTICIPANTS_WRITE = new ScopeField("participants:write"),
        ATTACHMENTS_READ = new ScopeField("attachments:read"),
        ATTACHMENTS_WRITE = new ScopeField("attachments:write"),
        COMMUNITIES_MANAGE = new ScopeField("communities:manage");

    public final LinkedHashMap<ScopeField, Boolean> scopes;
    
    public Scope(String raw) {
        ArrayList<String> scopes =
        new ArrayList<String>(Arrays.asList(raw.split(" ")));

        this.scopes = new LinkedHashMap<ScopeField, Boolean>();

        this.scopes.put(
            ME,
            scopes.indexOf(ME.name()) != -1
        );
        this.scopes.put(
            TOURNAMENTS_READ,
            scopes.indexOf(TOURNAMENTS_READ.name()) != -1
        );
        this.scopes.put(
            TOURNAMENTS_WRITE,
            scopes.indexOf(TOURNAMENTS_WRITE.name()) != -1
        );
        this.scopes.put(
            MATCHES_READ,
            scopes.indexOf(MATCHES_READ.name()) != -1
        );
        this.scopes.put(
            MATCHES_WRITE,
            scopes.indexOf(MATCHES_WRITE.name()) != -1
        );
        this.scopes.put(
            PARTICIPANTS_READ,
            scopes.indexOf(PARTICIPANTS_READ.name()) != -1
        );
        this.scopes.put(
            PARTICIPANTS_WRITE,
            scopes.indexOf(PARTICIPANTS_WRITE.name()) != -1
        );
        this.scopes.put(
            ATTACHMENTS_READ,
            scopes.indexOf(ATTACHMENTS_READ.name()) != -1
        );
        this.scopes.put(
            ATTACHMENTS_WRITE,
            scopes.indexOf(ATTACHMENTS_WRITE.name()) != -1
        );
        this.scopes.put(
            COMMUNITIES_MANAGE,
            scopes.indexOf(COMMUNITIES_MANAGE.name()) != -1
        );
    }

    public void requirePermissionScope(ScopeField scope) throws PermissionsScopeException {
        if (!this.scopes.get(scope)) {
            throw new PermissionsScopeException(scope.name());
        }
    }
    
    @Override
    public String toString() {
        ArrayList<String> scopes = new ArrayList<String>();
        for (Entry<ScopeField, Boolean> scope : this.scopes.entrySet()) {
            if (scope.getValue()) {
                scopes.add(scope.getKey().name());
            }
        }
        
        return String.format(
            "Scope[%s]",
            String.join(", ", scopes)
        );
    }
}