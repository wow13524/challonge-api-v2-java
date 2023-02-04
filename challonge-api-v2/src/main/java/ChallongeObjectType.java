package main.java;

public enum ChallongeObjectType {
    TOURNAMENT("tournament"),
    USER("user");

    public final String name;

    ChallongeObjectType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}