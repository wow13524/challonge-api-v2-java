package main.java;

public enum ChallongeObjectType {
    TOURNAMENT("Tournament"),
    USER("User");

    public final String name;

    ChallongeObjectType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}