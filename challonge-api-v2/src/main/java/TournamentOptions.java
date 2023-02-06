package main.java;

import main.java.Exceptions.ChallongeException;

public final class TournamentOptions {
    public final class DoubleEliminationOptions {
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

        private final boolean hideSeeds;
        private final GrandFinalsModifier grandFinalsModifier;

        public DoubleEliminationOptions(boolean hideSeeds, GrandFinalsModifier grandFinalsModifier) throws ChallongeException {
            TypeUtils.requireType(
                grandFinalsModifier,
                GrandFinalsModifier.class,
                "grandFinalsModifier"
            );
            this.hideSeeds = hideSeeds;
            this.grandFinalsModifier = grandFinalsModifier;
        }
    }

    public final class RoundRobinOptions {

    }

    public final class SwissOptions {

    }

    public final class FreeForAllOptions {

    }
}