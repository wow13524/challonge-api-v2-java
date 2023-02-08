package main.java;

import java.util.AbstractMap.SimpleImmutableEntry;
import main.java.Exceptions.ChallongeException;

public class TournamentOptions {
    public static final class DoubleEliminationOptions extends TournamentOptions {
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

        public DoubleEliminationOptions(boolean split_participants, GrandFinalsModifier grandFinalsModifier) throws ChallongeException {
            super(
                "double_elimination_options",
                new ImmutableMap<String, Object>(
                    new SimpleImmutableEntry<String, Object>(
                        "split_participants",
                        split_participants
                    ),
                    new SimpleImmutableEntry<String, Object>(
                        "grand_finals_modifier",
                        TypeUtils.requireType(
                            grandFinalsModifier,
                            GrandFinalsModifier.class,
                            "grandFinalsModifier"
                        ).name
                    )
                )
            );
        }
    }

    public static final class RoundRobinOptions {
        
    }

    public static final class SwissOptions {

    }

    public static final class FreeForAllOptions extends TournamentOptions {
        public FreeForAllOptions(int maxParticipants) throws ChallongeException {
            super(
                "free_for_all_options",
                new ImmutableMap<String, Object>(
                    new SimpleImmutableEntry<String, Object>(
                        "max_participants",
                        maxParticipants
                    )
                )
            );
        }
    }

    private final String key;
    private final ImmutableMap<String, Object> options;

    TournamentOptions(String key, ImmutableMap<String, Object> options) {
        this.key = key;
        this.options = options;
    }

    public String getKey() {
        return this.key;
    }

    public ImmutableMap<String, Object> getOptions() {
        return this.options;
    }
}