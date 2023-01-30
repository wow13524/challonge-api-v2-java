package main.java;

import java.util.Arrays;
import java.util.ArrayList;

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

    public final boolean
        me,
        tournamentsRead,
        tournamentsWrite,
        matchesRead,
        matchesWrite,
        participantsRead,
        participantsWrite,
        attachmentsRead,
        attachmentsWrite,
        communitiesManage;
    
    public Scope(String raw) {
        ArrayList<String> scopes = new ArrayList<String>(Arrays.asList(raw.split(" ")));
        this.me = scopes.indexOf("me") != -1;
        this.tournamentsRead = scopes.indexOf("tournaments:read") != -1;
        this.tournamentsWrite = scopes.indexOf("tournaments:write") != -1;
        this.matchesRead = scopes.indexOf("matches:read") != -1;
        this.matchesWrite = scopes.indexOf("matches:write") != -1;
        this.participantsRead = scopes.indexOf("participants:read") != -1;
        this.participantsWrite = scopes.indexOf("participants:write") != -1;
        this.attachmentsRead = scopes.indexOf("attachments:read") != -1;
        this.attachmentsWrite = scopes.indexOf("attachments:write") != -1;
        this.communitiesManage = scopes.indexOf("communities:manage") != -1;
    }
    
    @Override
    public String toString() {
        ArrayList<String> scopes = new ArrayList<String>();
        if (this.me) scopes.add("me");
        if (this.tournamentsRead) scopes.add("tournaments:read");
        if (this.tournamentsWrite) scopes.add("tournaments:write");
        if (this.matchesRead) scopes.add("matches:read");
        if (this.matchesWrite) scopes.add("matches:write");
        if (this.participantsRead) scopes.add("participants:read");
        if (this.participantsWrite) scopes.add("participants:write");
        if (this.attachmentsRead) scopes.add("attachments:read");
        if (this.attachmentsWrite) scopes.add("attachments:write");
        if (this.communitiesManage) scopes.add("communities:manage");
        
        return String.format("Scope[%s]", String.join(", ", scopes));
    }
}
