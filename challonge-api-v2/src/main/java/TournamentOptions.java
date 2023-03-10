package main.java;

public abstract class TournamentOptions {
    private final String key;
    private final TournamentType tournamentType;
    private final ImmutableMap<String, Object> options;

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