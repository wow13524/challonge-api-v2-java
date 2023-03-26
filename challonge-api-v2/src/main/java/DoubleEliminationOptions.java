package main.java;

import java.util.AbstractMap.SimpleImmutableEntry;

import org.json.simple.JSONObject;

import main.java.Exceptions.ChallongeException;

public final class DoubleEliminationOptions extends TournamentOptions {
    public enum GrandFinalsModifier implements EnumUtils.SearchableEnum {
        NONE(""),
        SKIP("skip"),
        SINGLE_MATCH("single match");

        public final String name;

        GrandFinalsModifier(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return this.name;
        }
    }
    
    public static final class DoubleEliminationOptionsBuilder extends TournamentOptionsBuilder {
        private boolean splitParticipants = DEFAULT_SPLIT_PARTICIPANTS;
        private GrandFinalsModifier grandFinalsModifier = DEFAILT_GRAND_FINALS_MODIFIER;

        private DoubleEliminationOptionsBuilder() {}

        public DoubleEliminationOptionsBuilder splitParticipants(boolean splitParticipants) {
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

        @Override
        public DoubleEliminationOptions build() {
            return new DoubleEliminationOptions(
                this.splitParticipants,
                this.grandFinalsModifier
            );
        }
    }

    private static final boolean DEFAULT_SPLIT_PARTICIPANTS = false;
    private static final GrandFinalsModifier DEFAILT_GRAND_FINALS_MODIFIER = GrandFinalsModifier.NONE;

    private final boolean splitParticipants;
    private final GrandFinalsModifier grandFinalsModifier;

    private DoubleEliminationOptions(boolean splitParticipants, GrandFinalsModifier grandFinalsModifier) {
        super(TournamentType.DOUBLE_ELIMINATION);
        this.splitParticipants = splitParticipants;
        this.grandFinalsModifier = grandFinalsModifier;
    }

    DoubleEliminationOptions(JSONObject json) throws ChallongeException {
        this(
            TypeUtils.requireOptionalType(
                json,
                "splitParticipants",
                Boolean.class,
                DEFAULT_SPLIT_PARTICIPANTS
            ),
            EnumUtils.valueFromString(
                GrandFinalsModifier.class,
                TypeUtils.requireOptionalType(
                    json,
                    "grandFinalsModifier",
                    String.class,
                    DEFAILT_GRAND_FINALS_MODIFIER.name
                )
            )
        );
    }

    public static DoubleEliminationOptionsBuilder newBuilder() {
        return new DoubleEliminationOptionsBuilder();
    }

    public boolean getSplitParticipants() {
        return this.splitParticipants;
    }

    public GrandFinalsModifier getGrandFinalsModifier() {
        return this.grandFinalsModifier;
    }

    @Override
    ImmutableMap<String, Object> getOptions() {
        return new ImmutableMap<String, Object>(
            new SimpleImmutableEntry<String, Object>(
                "split_participants",
                this.splitParticipants
            ),
            this.grandFinalsModifier == GrandFinalsModifier.NONE ? null :
            new SimpleImmutableEntry<String, Object>(
                "grand_finals_modifier",
                this.grandFinalsModifier.name
            )
        );
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) {
            return false;
        }
        DoubleEliminationOptions other = (DoubleEliminationOptions)o;
        return other.splitParticipants == this.splitParticipants
        && other.grandFinalsModifier == this.grandFinalsModifier;
    }
}