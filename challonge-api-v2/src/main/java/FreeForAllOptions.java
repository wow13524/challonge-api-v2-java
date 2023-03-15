package main.java;

import java.util.AbstractMap.SimpleImmutableEntry;

import org.json.simple.JSONObject;

import main.java.Exceptions.ChallongeException;

public final class FreeForAllOptions extends TournamentOptions {
    public static final class FreeForAllOptionsBuilder extends TournamentOptionsBuilder {
        private int maxParticipants = DEFAULT_MAX_PARTICIPANTS;

        private FreeForAllOptionsBuilder() {}

        public FreeForAllOptionsBuilder maxParticipants(int maxParticipants) {
            this.maxParticipants = maxParticipants;
            return this;
        }

        @Override
        public TournamentOptions build() {
            return new FreeForAllOptions(
                this.maxParticipants
            );
        }
    }

    private static final int DEFAULT_MAX_PARTICIPANTS = 4;

    private final int maxParticipants;

    private FreeForAllOptions(int maxParticipants) {
        super(TournamentType.FREE_FOR_ALL);
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

    @Override
    ImmutableMap<String, Object> getOptions() {
        return new ImmutableMap<String, Object>(
            new SimpleImmutableEntry<String, Object>(
                "max_participants",
                maxParticipants
            )
        );
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) {
            return false;
        }
        FreeForAllOptions other = (FreeForAllOptions)o;
        return other.maxParticipants == this.maxParticipants;
    }
}