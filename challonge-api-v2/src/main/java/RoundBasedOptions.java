package main.java;

import org.json.simple.JSONObject;

import main.java.Exceptions.ChallongeException;

abstract class RoundBasedOptions extends TournamentOptions {
    static final double DEFAULT_POINTS_GAME_WIN = 1;
    static final double DEFAULT_POINTS_GAME_TIE = 0;
    static final double DEFAULT_POINTS_MATCH_WIN = 1;
    static final double DEFAULT_POINTS_MATCH_TIE = 0.5;

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
                "ptsGameWin",
                Double.class,
                DEFAULT_POINTS_GAME_WIN
            ),
            TypeUtils.requireOptionalType(
                json,
                "ptsGameTie",
                Double.class,
                DEFAULT_POINTS_GAME_TIE
            ),
            TypeUtils.requireOptionalType(
                json,
                "ptsMatchWin",
                Double.class,
                DEFAULT_POINTS_MATCH_WIN
            ),
            TypeUtils.requireOptionalType(
                json,
                "ptsMatchTie",
                Double.class,
                DEFAULT_POINTS_MATCH_TIE
            )
        );
    }

    private boolean doubleEquals(double a, double b) {
        return Math.abs(a - b) < Math.ulp(0d);
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