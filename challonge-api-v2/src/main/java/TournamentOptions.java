package main.java;

public abstract class TournamentOptions {
    public static abstract class TournamentOptionsBuilder {
        public abstract TournamentOptions build();
    }

    private final TournamentType tournamentType;

    TournamentOptions(TournamentType tournamentType) {
        this.tournamentType = tournamentType;
    }

    public String getKey() {
        return this.tournamentType.key;
    }

    public TournamentType getTournamentType() {
        return this.tournamentType;
    }

    abstract ImmutableMap<String, Object> getOptions();

    //Meant to be overridden by subclasses to check exact member fields
    @Override
    public boolean equals(Object o) {
        return this.getClass().isInstance(o);
    }
}