package main.java;

import java.util.AbstractMap.SimpleImmutableEntry;

import org.json.simple.JSONObject;

import main.java.Exceptions.ChallongeException;

public final class FreeForAllOptions extends TournamentOptions {
    public static final class FreeForAllOptionsBuilder {
        private int maxParticipants = DEFAULT_MAX_PARTICIPANTS;

        private FreeForAllOptionsBuilder() {}

        public FreeForAllOptionsBuilder maxParticipants(int maxParticipants) throws ChallongeException {
            this.maxParticipants = maxParticipants;
            return this;
        }

        public TournamentOptions build() {
            return new FreeForAllOptions(
                this.maxParticipants
            );
        }
    }

    private static final String OPTIONS_KEY = "free_for_all_options";
    private static final int DEFAULT_MAX_PARTICIPANTS = 4;

    private final int maxParticipants;

    private FreeForAllOptions(int maxParticipants) {
        super(
            OPTIONS_KEY,
            TournamentType.FREE_FOR_ALL,
            new ImmutableMap<String, Object>(
                new SimpleImmutableEntry<String, Object>(
                    "max_participants",
                    maxParticipants
                )
            )
        );
        this.maxParticipants = maxParticipants;
    }

    FreeForAllOptions(JSONObject json) throws ChallongeException {
        this(
            (int)(long)TypeUtils.requireOptionalType(
                json,
                "maxParticipants",
                Long.class,
                (long)DEFAULT_MAX_PARTICIPANTS
            )
        );
    }

    public static FreeForAllOptionsBuilder newBuilder() {
        return new FreeForAllOptionsBuilder();
    }

    public int getMaxParticipants() {
        return this.maxParticipants;
    }
}