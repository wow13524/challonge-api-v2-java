package main.java;

public enum TournamentType {
    SINGLE_ELIMINATION("single elimination"),
    DOUBLE_ELIMINATION("double elimination"),
    ROUND_ROBIN("round robin"),
    SWISS("swiss"),
    FREE_FOR_ALL("free for all"),
    LEADERBOARD("leaderboard"),
    TIME_TRIAL("time trial"),
    SINGLE_RACE("sincle race"),
    LGC_TIMETRIAL("lgc timetrial"),
    GRAND_PRIX("grand_prix"),
    LEAGUE("league");

    public final String name;

    TournamentType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}