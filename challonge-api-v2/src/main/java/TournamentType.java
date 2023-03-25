package main.java;

public enum TournamentType implements EnumUtils.SearchableEnum {
    SINGLE_ELIMINATION(null, "single elimination"),
    DOUBLE_ELIMINATION("double_elimination_options", "double elimination"),
    ROUND_ROBIN("round_robin_options", "round robin"),
    SWISS("swiss_options", "swiss"),
    FREE_FOR_ALL("free_for_all_options", "free for all"),
    LEADERBOARD(null, "leaderboard"),
    TIME_TRIAL(null, "time trial"),
    SINGLE_RACE(null, "sincle race"),
    LGC_TIMETRIAL(null, "lgc timetrial"),
    GRAND_PRIX(null, "grand_prix"),
    LEAGUE(null, "league");

    public final String key,name;
    
    TournamentType(String key, String name) {
        this.key = key;
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }
}