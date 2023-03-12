package main.java;

public abstract class TournamentOptions {
    public static abstract class TournamentOptionsBuilder {
        public abstract TournamentOptions build();
    }

    private final TournamentType tournamentType;
    private final ImmutableMap<String, Object> options;

    TournamentOptions(TournamentType tournamentType, ImmutableMap<String, Object> options) {
        this.tournamentType = tournamentType;
        this.options = options;
    }

    public String getKey() {
        return this.tournamentType.key;
    }

    public TournamentType getTournamentType() {
        return this.tournamentType;
    }

    ImmutableMap<String, Object> getOptions() {
        return this.options;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof TournamentOptions)) {
            return false;
        }
        TournamentOptions to = (TournamentOptions)o;
        if (this.tournamentType != to.tournamentType) {
            return false;
        }
        for (String key : this.options.keySet()) {
            Object myValue = this.options.get(key);
            Object toValue = to.options.get(key);
            if (myValue == null || toValue == null) {
                if (myValue != toValue) {
                    return false;
                }
            }
            else if (!myValue.equals(toValue)) {
                return false;
            }
        }
        return true;
    }
}