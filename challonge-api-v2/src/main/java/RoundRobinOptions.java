package main.java;

import java.util.AbstractMap.SimpleImmutableEntry;

import org.json.simple.JSONObject;

import main.java.Exceptions.ChallongeException;

public final class RoundRobinOptions extends RoundBasedOptions {
    public enum Ranking {
        MATCH_WINS("match wins"),
        GAME_WINS("game wins"),
        GAME_WIN_PERCENTAGE("game win percentage"),
        POINTS_SCORED("points scored"),
        POINTS_DIFFERENCE("points difference"),
        CUSTOM("custom");

        public final String name;

        Ranking(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return this.name;
        }
    }

    public static final class RoundRobinOptionsBuilder extends TournamentOptionsBuilder {
        private double pointsGameWin = DEFAULT_POINTS_GAME_WIN;
        private double pointsGameTie = DEFAULT_POINTS_GAME_TIE;
        private double pointsMatchWin = DEFAULT_POINTS_MATCH_WIN;
        private double pointsMatchTie = DEFAULT_POINTS_MATCH_TIE;
        private int iterations = DEFAULT_ITERATIONS;
        private Ranking ranking = DEFAULT_RANKING;

        private RoundRobinOptionsBuilder() {}

        public RoundRobinOptionsBuilder pointsGameWin(double pointsGameWin) {
            this.pointsGameWin = pointsGameWin;
            return this;
        }
        
        public RoundRobinOptionsBuilder pointsGameTie(double pointsGameTie) {
            this.pointsGameTie = pointsGameTie;
            return this;
        }

        public RoundRobinOptionsBuilder pointsMatchWin(double pointsMatchWin) {
            this.pointsMatchWin = pointsMatchWin;
            return this;
        }

        public RoundRobinOptionsBuilder pointsMatchTie(double pointsMatchTie) {
            this.pointsMatchTie = pointsMatchTie;
            return this;
        }

        public RoundRobinOptionsBuilder iterations(int iterations) {
            this.iterations = iterations;
            return this;
        }

        public RoundRobinOptionsBuilder ranking(Ranking ranking) throws ChallongeException {
            this.ranking =
            TypeUtils.requireType(
                ranking,
                Ranking.class,
                "ranking"
            );
            return this;
        }

        @Override
        public TournamentOptions build() {
            return new RoundRobinOptions(
                this.pointsGameWin,
                this.pointsGameTie,
                this.pointsMatchWin,
                this.pointsMatchTie,
                this.iterations,
                this.ranking
            );
        }
    }

    private static final int DEFAULT_ITERATIONS = 2;
    private static final Ranking DEFAULT_RANKING = Ranking.MATCH_WINS;

    private final int iterations;
    private final Ranking ranking;

    private RoundRobinOptions(double pointsGameWin, double pointsGameTie, double pointsMatchWin, double pointsMatchTie, int iterations, Ranking ranking) {
        super(
            TournamentType.ROUND_ROBIN,
            pointsGameWin,
            pointsGameTie,
            pointsMatchWin,
            pointsMatchTie
        );
        this.iterations = iterations;
        this.ranking = ranking;
    }

    RoundRobinOptions(JSONObject json) throws ChallongeException {
        super(
            TournamentType.ROUND_ROBIN,
            json
        );
        this.iterations = (int)(long)TypeUtils.requireOptionalType(
            json,
            "roundRobinIterations",
            Long.class,
            (long)DEFAULT_ITERATIONS
        );
        this.ranking = EnumUtils.valueFromString(
            Ranking.class,
            TypeUtils.requireOptionalType(
                json,
                "rankedBy",
                String.class,
                DEFAULT_RANKING.name
            )
        );
    }

    public static RoundRobinOptionsBuilder newBuilder() {
        return new RoundRobinOptionsBuilder();
    }

    public int getIterations() {
        return this.iterations;
    }

    public Ranking getRanking() {
        return this.ranking;
    }

    @Override
    ImmutableMap<String, Object> getOptions() {
        return super.getOptions(
            new SimpleImmutableEntry<String, Object>(
                "iterations",
                this.iterations
            ),
            new SimpleImmutableEntry<String, Object>(
                "ranking",
                this.ranking.name
            )
        );
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) {
            return false;
        }
        RoundRobinOptions other = (RoundRobinOptions)o;
        return other.iterations == this.iterations
        && other.ranking == this.ranking;
    }
}