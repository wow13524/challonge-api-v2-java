package main.java;

import java.util.AbstractMap.SimpleImmutableEntry;

import org.json.simple.JSONObject;

import main.java.Exceptions.ChallongeException;

abstract class RoundBasedTournamentOptions extends TournamentOptions {
    static final double DEFAULT_POINTS_GAME_WIN = 1;
    static final double DEFAULT_POINTS_GAME_TIE = 0;
    static final double DEFAULT_POINTS_MATCH_WIN = 1;
    static final double DEFAULT_POINTS_MATCH_TIE = 0.5;

    protected final double pointsGameWin,pointsGameTie,pointsMatchWin,pointsMatchTie;

    RoundBasedTournamentOptions() {
        
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
}