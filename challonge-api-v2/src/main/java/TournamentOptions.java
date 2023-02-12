package main.java;

import java.util.AbstractMap.SimpleImmutableEntry;
import main.java.Exceptions.ChallongeException;

public final class TournamentOptions {
    public enum GrandFinalsModifier {
        NONE(""),
        SKIP("skip"),
        SINGLE_MATCH("single match");

        public final String name;

        GrandFinalsModifier(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return this.name;
        }
    }

    private static final String KEY_DOUBLE_ELIMINATION_OPTIONS = "double_elimination_options";
    private static final boolean DEFAULT_SPLIT_PARTICIPANTS = false;
    private static final GrandFinalsModifier DEFAILT_GRAND_FINALS_MODIFIER = GrandFinalsModifier.NONE;

    public static final class DoubleEliminationOptionsBuilder {
        private boolean splitParticipants = DEFAULT_SPLIT_PARTICIPANTS;
        private GrandFinalsModifier grandFinalsModifier = DEFAILT_GRAND_FINALS_MODIFIER;

        public DoubleEliminationOptionsBuilder() {}

        public DoubleEliminationOptionsBuilder splitParticipants(boolean splitParticipants) throws ChallongeException {
            this.splitParticipants = splitParticipants;
            return this;
        }

        public DoubleEliminationOptionsBuilder grandFinalsModifier(GrandFinalsModifier grandFinalsModifier) throws ChallongeException {
            this.grandFinalsModifier =
            TypeUtils.requireType(
                grandFinalsModifier,
                GrandFinalsModifier.class,
                "grandFinalsModifier"
            );
            return this;
        }

        public TournamentOptions build() {
            return new TournamentOptions(
                KEY_DOUBLE_ELIMINATION_OPTIONS,
                TournamentType.DOUBLE_ELIMINATION,
                new ImmutableMap<String, Object>(
                    new SimpleImmutableEntry<String, Object>(
                        "split_participants",
                        this.splitParticipants
                    ),
                    new SimpleImmutableEntry<String, Object>(
                        "grand_finals_modifier",
                        this.grandFinalsModifier.name
                    )
                )
            );
        }
    }

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

    public ImmutableMap<String, Object> getOptions() {
        return this.options;
    }
}