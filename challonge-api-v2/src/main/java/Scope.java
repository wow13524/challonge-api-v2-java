package main.java;

public enum Scope {
    ME("me"),
    TOURNAMENTS_READ("tournaments:read"),
    TOURNAMENTS_WRITE("tournaments:write"),
    MATCHES_READ("matches:read"),
    MATCHES_WRITE("matches:write"),
    PARTICIPANTS_READ("participants:read"),
    PARTICIPANTS_WRITE("participants:write"),
    ATTACHMENTS_READ("attachments:read"),
    ATTACHMENTS_WRITE("attachments:write"),
    COMMUNITIES_MANAGE("communities:manage");

    public final String name;

    Scope(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}