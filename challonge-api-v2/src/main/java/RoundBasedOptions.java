package main.java;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Map.Entry;

import org.json.simple.JSONObject;

import main.java.Exceptions.ChallongeException;

abstract class RoundBasedOptions extends TournamentOptions {
    static final double DEFAULT_POINTS_GAME_WIN = 1;
    static final double DEFAULT_POINTS_GAME_TIE = 0;
    static final double DEFAULT_POINTS_MATCH_WIN = 1;
    static final double DEFAULT_POINTS_MATCH_TIE = 0.5;
    private static final int NUM_ENTRIES = 4;

    protected final double pointsGameWin,pointsGameTie,pointsMatchWin,pointsMatchTie;

    RoundBasedOptions(TournamentType tournamentType, double pointsGameWin, double pointsGameTie, double pointsMatchWin, double pointsMatchTie) {
        super(tournamentType);
        this.pointsGameWin = pointsGameWin;
        this.pointsGameTie = pointsGameTie;
        this.pointsMatchWin = pointsMatchWin;
        this.pointsMatchTie = pointsMatchTie;
    }

    RoundBasedOptions(TournamentType tournamentType, JSONObject json) throws ChallongeException {
        this(
            tournamentType,
            TypeUtils.requireOptionalType(
                json,
                "ptsForGameWin",
                Double.class,
                DEFAULT_POINTS_GAME_WIN
            ),
            TypeUtils.requireOptionalType(
                json,
                "ptsForGameTie",
                Double.class,
                DEFAULT_POINTS_GAME_TIE
            ),
            TypeUtils.requireOptionalType(
                json,
                "ptsForMatchWin",
                Double.class,
                DEFAULT_POINTS_MATCH_WIN
            ),
            TypeUtils.requireOptionalType(
                json,
                "ptsForMatchTie",
                Double.class,
                DEFAULT_POINTS_MATCH_TIE
            )
        );
    }

    public double getPointsGameWin() {
        return this.pointsGameWin;
    }

    public double getPointsGameTie() {
        return this.pointsGameTie;
    }

    public double getPointsMatchWin() {
        return this.pointsMatchWin;
    }

    public double getPointsMatchTie() {
        return this.pointsMatchTie;
    }

    @SuppressWarnings("unchecked")
    protected ImmutableMap<String, Object> getOptions(Entry<String, Object>... additionalEntries) {
        Entry<String, Object>[] entries =
        new Entry[NUM_ENTRIES + additionalEntries.length];
        int i = 0;
        entries[i++] = new SimpleImmutableEntry<String, Object>(
            "pts_for_game_win",
            this.pointsGameWin
        );
        entries[i++] = new SimpleImmutableEntry<String, Object>(
            "pts_for_game_tie",
            this.pointsGameTie
        );
        entries[i++] = new SimpleImmutableEntry<String, Object>(
            "pts_for_match_win",
            this.pointsMatchWin
        );
        entries[i++] = new SimpleImmutableEntry<String, Object>(
            "pts_for_match_tie",
            this.pointsMatchTie
        );
        System.arraycopy(
            additionalEntries,
            0,
            entries,
            i,
            additionalEntries.length
        );
        return new ImmutableMap<String, Object>(entries);
    }

    private boolean doubleEquals(double a, double b) {
        return Math.abs(a - b) < Math.ulp(0d);
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) {
            return false;
        }
        RoundBasedOptions other = (RoundBasedOptions)o;
        return doubleEquals(other.pointsGameWin, this.pointsGameWin)
        && doubleEquals(other.pointsGameTie, this.pointsGameTie)
        && doubleEquals(other.pointsMatchWin, this.pointsMatchWin)
        && doubleEquals(other.pointsMatchTie, this.pointsMatchTie);
    }
}