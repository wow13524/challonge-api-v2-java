package main.java;

import org.json.simple.JSONObject;

import main.java.Exceptions.ChallongeException;

public class ChallongeTournament extends ChallongeObject {
    public String name, url;
    public TournamentType tournamentType;

    ChallongeTournament(JSONObject json) throws ChallongeException {
        super(
            TypeUtils.requireType(json, "id", String.class),
            TypeUtils.requireType(json, "type", String.class)
        );

        JSONObject attributes =
        TypeUtils.requireType(json, "attributes", JSONObject.class);

        this.name =
        TypeUtils.requireType(attributes, "name", String.class);
        this.url = 
        TypeUtils.requireType(attributes, "url", String.class);

        String tournamentType = 
        TypeUtils.requireType(attributes, "tournamentType", String.class);
        this.tournamentType = TournamentType.fromString(tournamentType);
    }

    @Override
    public String toString() {
        return String.format(
            "ChallongeTournament[id=%s, name=%s, url=%s, type=%s]",
            this.id,
            this.name,
            this.url,
            this.tournamentType
        );
    }
}