package main.java;

import java.util.AbstractMap.SimpleImmutableEntry;

import org.json.simple.JSONObject;

import main.java.Exceptions.ChallongeException;

public final class SwissOptions extends RoundBasedOptions {
     public static final class SwissOptionsBuilder extends TournamentOptionsBuilder {
        private double pointsGameWin = DEFAULT_POINTS_GAME_WIN;
        private double pointsGameTie = DEFAULT_POINTS_GAME_TIE;
        private double pointsMatchWin = DEFAULT_POINTS_MATCH_WIN;
        private double pointsMatchTie = DEFAULT_POINTS_MATCH_TIE;
        private double pointsBye = DEFAULT_POINTS_BYE;
        private int rounds = DEFAULT_ROUNDS;

        private SwissOptionsBuilder() {}

        public SwissOptionsBuilder pointsGameWin(double pointsGameWin) {
            this.pointsGameWin = pointsGameWin;
            return this;
        }
        
        public SwissOptionsBuilder pointsGameTie(double pointsGameTie) {
            this.pointsGameTie = pointsGameTie;
            return this;
        }

        public SwissOptionsBuilder pointsMatchWin(double pointsMatchWin) {
            this.pointsMatchWin = pointsMatchWin;
            return this;
        }

        public SwissOptionsBuilder pointsMatchTie(double pointsMatchTie) {
            this.pointsMatchTie = pointsMatchTie;
            return this;
        }

        public SwissOptionsBuilder pointsBye(double pointsBye) {
            this.pointsBye = pointsBye;
            return this;
        }

        public SwissOptionsBuilder rounds(int rounds) {
            this.rounds = rounds;
            return this;
        }

        @Override
        public TournamentOptions build() {
            return new SwissOptions(
                this.pointsGameWin,
                this.pointsGameTie,
                this.pointsMatchWin,
                this.pointsMatchTie,
                this.pointsBye,
                this.rounds
            );
        }
    }

    private static final double DEFAULT_POINTS_BYE = 1;
    private static final int DEFAULT_ROUNDS = 2;

    private final double pointsBye;
    private final int rounds;

    private SwissOptions(double pointsGameWin, double pointsGameTie, double pointsMatchWin, double pointsMatchTie, double pointsBye, int rounds) {
        super(
            TournamentType.SWISS,
            pointsGameWin,
            pointsGameTie,
            pointsMatchWin,
            pointsMatchTie
        );
        this.pointsBye = pointsBye;
        this.rounds = rounds;
    }

    SwissOptions(JSONObject json) throws ChallongeException {
        super(
            TournamentType.SWISS,
            json
        );
        this.pointsBye = Double.parseDouble(TypeUtils.requireOptionalType(
            json,
            "ptsForBye",
            String.class,
            DEFAULT_POINTS_BYE+""
        ));
        this.rounds = (int)(long)TypeUtils.requireOptionalType(
            json,
            "swissRounds",
            Long.class,
            (long)DEFAULT_ROUNDS
        );
    }

    public static SwissOptionsBuilder newBuilder() {
        return new SwissOptionsBuilder();
    }

    public double getPointsBye() {
        return this.pointsBye;
    }

    public int getRounds() {
        return this.rounds;
    }

    @Override
    ImmutableMap<String, Object> getOptions() {
        return super.getOptions(
            new SimpleImmutableEntry<String, Object>(
                "pts_for_bye",
                this.pointsBye
            ),
            new SimpleImmutableEntry<String, Object>(
                "rounds",
                this.rounds
            )
        );
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) {
            return false;
        }
        SwissOptions other = (SwissOptions)o;
        return doubleEquals(other.pointsBye, this.pointsBye)
        && other.rounds == this.rounds;
    }
}