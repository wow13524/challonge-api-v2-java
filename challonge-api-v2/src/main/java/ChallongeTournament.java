package main.java;

import org.json.simple.JSONObject;

import main.java.Exceptions.ChallongeException;

public class ChallongeTournament extends ChallongeObject {
    public final String name, url;

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
    }

    @Override
    public String toString() {
        return String.format(
            "ChallongeTournament[id=%s, name=%s, url=%s]",
            this.id,
            this.name,
            this.url
        );
    }
}