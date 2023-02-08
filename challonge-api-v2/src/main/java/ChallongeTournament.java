package main.java;

import org.json.simple.JSONObject;

import main.java.Exceptions.ChallongeException;

public class ChallongeTournament extends ChallongeObject {
    private String name, url;
    private TournamentType tournamentType;
    private TournamentOptions tournamentOptions;

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
    private void update(String name, TournamentType tournamentType, TournamentOptions tournamentOptions) throws ChallongeException {
        JSONObject body = new JSONObject();
        JSONObject data = new JSONObject();
        JSONObject attributes = new JSONObject();

        body.put("data", data);
        data.put("type", "tournaments");
        data.put("attributes", attributes);
        attributes.put("name", name);
        attributes.put("tournament_type", tournamentType.name);
        if (tournamentOptions != null) {
            attributes.put(
                tournamentOptions.getKey(),
                tournamentOptions.getOptions()
            );
        }
        
        this.api.apiPut(
            ChallongeApi.toURI("tournaments", this.getId()),
            body
        );

        this.name = name;
        this.tournamentType = tournamentType;
        this.tournamentOptions = tournamentOptions;
    }

    public void setName(String name) throws ChallongeException {
        this.api.scopes.requirePermissionScope(Scope.TOURNAMENTS_WRITE);
        TypeUtils.requireType(
            name,
            String.class,
            "name"
        );

        this.update(
            name,
            this.getTournamentType(),
            this.getTournamentOptions()
        );
    }

    public void setTournamentType(TournamentType tournamentType, TournamentOptions tournamentOptions) throws ChallongeException {
        this.api.scopes.requirePermissionScope(Scope.TOURNAMENTS_WRITE);
        TypeUtils.requireType(
            tournamentType,
            TournamentType.class,
            "tournamentType"
        );
        if (tournamentType.requiredOptions != null) {
            TypeUtils.requireType(
                tournamentOptions,
                tournamentType.requiredOptions,
                "tournamentOptions"
            );
        }

        this.update(
            this.getName(),
            tournamentType,
            tournamentOptions
        );
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

    public TournamentOptions getTournamentOptions() {
        return this.tournamentOptions;
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