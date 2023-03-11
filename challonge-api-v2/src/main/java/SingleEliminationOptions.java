package main.java;

import org.json.simple.JSONObject;

import main.java.Exceptions.ChallongeException;

public final class SingleEliminationOptions extends TournamentOptions {
    public static final class SingleEliminationOptionsBuilder {
        private SingleEliminationOptionsBuilder() {}

        public TournamentOptions build() {
            return new SingleEliminationOptions();
        }
    }

    private SingleEliminationOptions() {
        super(
            TournamentType.SINGLE_ELIMINATION,
            null
        );
    }

    SingleEliminationOptions(JSONObject json) throws ChallongeException {
        this();
    }

    public static SingleEliminationOptionsBuilder newBuilder() {
        return new SingleEliminationOptionsBuilder();
    }
}