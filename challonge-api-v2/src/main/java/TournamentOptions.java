package main.java;

public abstract class TournamentOptions {
    private final String key;
    private final TournamentType tournamentType;
    private final ImmutableMap<String, Object> options;

    //TODO override .equals()

    TournamentOptions(String key, TournamentType tournamentType, ImmutableMap<String, Object> options) {
        this.key = key;
        this.tournamentType = tournamentType;
        this.options = options;
    }

    public String getKey() {
        return this.key;
    }

    public TournamentType getTournamentType() {
        return this.tournamentType;
    }

    ImmutableMap<String, Object> getOptions() {
        return this.options;
    }
}