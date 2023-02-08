package main.java;

import main.java.TournamentOptions.*;

public enum TournamentType {
    SINGLE_ELIMINATION("single elimination", null),
    DOUBLE_ELIMINATION("double elimination", DoubleEliminationOptions.class),
    ROUND_ROBIN("round robin", null),
    SWISS("swiss", null),
    FREE_FOR_ALL("free for all", FreeForAllOptions.class),
    LEADERBOARD("leaderboard", null),
    TIME_TRIAL("time trial", null),
    SINGLE_RACE("sincle race", null),
    LGC_TIMETRIAL("lgc timetrial", null),
    GRAND_PRIX("grand_prix", null),
    LEAGUE("league", null);

    public final String name;
    public final Class<? extends TournamentOptions> requiredOptions;

    <T extends TournamentOptions> TournamentType(String name, Class<T> requiredOptions) {
        this.name = name;
        this.requiredOptions = requiredOptions;
    }

    @Override
    public String toString() {
        return this.name;
    }
}