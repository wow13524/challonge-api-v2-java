package main.java;

import org.json.simple.JSONObject;

import main.java.Exceptions.ChallongeException;

public class ChallongeTournament extends ChallongeObject {
    private String name, url;
    private TournamentType tournamentType;

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
        this.tournamentType =
        EnumUtils.valueFromString(TournamentType.class, tournamentType);
    }

    public String getName() {
        return this.name;
    }

    public String getUrl() {
        return this.url;
    }

    public TournamentType getTournamentType() {
        return this.tournamentType;
    }

    @Override
    public String toString() {
        return String.format(
            "ChallongeTournament[id=%s, name=%s]",
            this.getId(),
            this.getName()
        );
    }
}