package main.java;

public enum ChallongeObjectType implements EnumUtils.SearchableEnum {
    TOURNAMENT("tournament"),
    USER("user");

    public final String name;

    ChallongeObjectType(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }
}