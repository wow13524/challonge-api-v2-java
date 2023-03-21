package main.java;

import org.json.simple.JSONObject;

import main.java.Exceptions.ChallongeException;

public final class SingleEliminationOptions extends TournamentOptions {
    public static final class SingleEliminationOptionsBuilder extends TournamentOptionsBuilder {
        private SingleEliminationOptionsBuilder() {}

        @Override
        public SingleEliminationOptions build() {
            return new SingleEliminationOptions();
        }
    }

    private SingleEliminationOptions() {
        super(TournamentType.SINGLE_ELIMINATION);
    }

    SingleEliminationOptions(JSONObject json) throws ChallongeException {
        this();
    }

    public static SingleEliminationOptionsBuilder newBuilder() {
        return new SingleEliminationOptionsBuilder();
    }

    @Override
    ImmutableMap<String, Object> getOptions() {
        return null;
    }
}