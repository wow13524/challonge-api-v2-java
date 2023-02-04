package main.java;

import java.util.HashMap;

import org.json.simple.JSONObject;

import main.java.Exceptions.ChallongeException;

public class ChallongeTournament extends ChallongeObject {
    private String name, url;
    private TournamentType tournamentType;

    ChallongeTournament(ChallongeApi api, JSONObject json) throws ChallongeException {
        super(
            api,
            TypeUtils.requireType(json, "id", String.class),
            TypeUtils.requireType(json, "type", String.class)
        );

        JSONObject attributes =
        TypeUtils.requireType(json, "attributes", JSONObject.class);

        this.name =
        TypeUtils.requireType(attributes, "name", String.class);
        this.url = 
        TypeUtils.requireType(attributes, "fullChallongeUrl", String.class);

        String tournamentType = 
        TypeUtils.requireType(attributes, "tournamentType", String.class);
        this.tournamentType =
        EnumUtils.valueFromString(TournamentType.class, tournamentType);
    }

    @SuppressWarnings("unchecked")
    private void update(String name, TournamentType tournamentType) throws ChallongeException {
        JSONObject body = new JSONObject();
        JSONObject data = new JSONObject();
        JSONObject attributes = new JSONObject();

        body.put("data", data);
        data.put("type", "tournaments");
        data.put("attributes", attributes);
        attributes.put("name", name);
        attributes.put("tournament_type", tournamentType.name);
        
        this.api.apiPut(
            ChallongeApi.toURI("tournaments", this.getId()),
            body
        );
    }

    public void setName(String name) throws ChallongeException {
        this.api.scopes.requirePermissionScope(Scope.TOURNAMENTS_WRITE);
        TypeUtils.requireType(
            name,
            String.class,
            "name"
        );

        this.update(name, this.getTournamentType());

        this.name = name;
    }

    public void setTournamentType(TournamentType tournamentType) throws ChallongeException {
        this.api.scopes.requirePermissionScope(Scope.TOURNAMENTS_WRITE);
        TypeUtils.requireType(
            tournamentType,
            TournamentType.class,
            "tournamentType"
        );

        this.update(this.getName(), tournamentType);

        this.tournamentType = tournamentType;
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