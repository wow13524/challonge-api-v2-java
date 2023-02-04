package main.java;

public enum TournamentType {
    SINGLE_ELIMINATION("Single Elimination"),
    DOUBLE_ELIMINATION("Double Elimination"),
    ROUND_ROBIN("Round Robin"),
    SWISS("Swiss"),
    FREE_FOR_ALL("Free For All");

    public final String name;

    TournamentType(String name) {
        this.name = name;
    }

    public static TournamentType fromString(String name) {
        for (TournamentType type : TournamentType.values()) {
            if (type.name.equals(name)) {
                return type;
            }
        }
        throw new IllegalArgumentException(String.format(
            "No TournamentType with name '%s'",
            name
        ));
    }

    @Override
    public String toString() {
        return this.name;
    }
}