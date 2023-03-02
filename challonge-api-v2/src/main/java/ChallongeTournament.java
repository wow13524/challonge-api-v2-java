package main.java;

import org.json.simple.JSONObject;

import main.java.Exceptions.ChallongeException;
import main.java.Exceptions.MismatchedTournamentOptionsException;

public class ChallongeTournament extends ChallongeObject {
    private boolean isPrivate;
    private String description, name, url;
    //private TournamentType tournamentType;
    private TournamentDescription tournamentOptions;

    /*TODO
     * figure out how to handle tournamentType and tournamentOptions once and for all!!!
     * In terms of parsing from the API, access through ChallongeTournament, and updating through setTournamentType()!!!
     */
    ChallongeTournament(ChallongeApi api, JSONObject json) throws ChallongeException {
        super(
            api,
            TypeUtils.requireType(json, "id", String.class),
            TypeUtils.requireType(json, "type", String.class)
        );

        JSONObject attributes =
        TypeUtils.requireType(json, "attributes", JSONObject.class);

        this.isPrivate =
        TypeUtils.requireType(attributes, "private", Boolean.class);

        this.description =
        TypeUtils.requireType(attributes, "description", String.class);

        this.name =
        TypeUtils.requireType(attributes, "name", String.class);

        this.url = 
        TypeUtils.requireType(attributes, "url", String.class);

        String tournamentType = 
        TypeUtils.requireType(attributes, "tournamentType", String.class);

        /*this.tournamentType =
        EnumUtils.valueFromString(TournamentType.class, tournamentType);*/
    }

    @SuppressWarnings("unchecked")
    private JSONObject toAttributes() {
        JSONObject attributes = new JSONObject();
        attributes.put("name", this.getName());
        attributes.put("url", this.getUrl());
        attributes.put("tournament_type", this.getTournamentType().name);
        attributes.put("private", this.isPrivate());
        //
        attributes.put("description", this.getDescription());
        //
        attributes.put(
            this.getTournamentOptions().getKey(),
            this.getTournamentOptions().getOptions()
        );

        return attributes;
    }

    @SuppressWarnings("unchecked")
    private void update() throws ChallongeException {
        JSONObject body = new JSONObject();
        JSONObject data = new JSONObject();

        body.put("data", data);
        data.put("type", "tournaments");
        data.put("attributes", this.toAttributes());
        
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

        String originalName = this.getName();
        try {
            this.name = name;
            this.update();
        }
        catch (ChallongeException e) {
            this.name = originalName;
            throw e;
        }
    }

    public void setTournamentType(TournamentType tournamentType, TournamentDescription tournamentOptions) throws ChallongeException {
        this.api.scopes.requirePermissionScope(Scope.TOURNAMENTS_WRITE);
        TypeUtils.requireType(
            tournamentType,
            TournamentType.class,
            "tournamentType"
        );
        TypeUtils.requireType(
            tournamentOptions,
            TournamentDescription.class,
            "tournamentOptions"
        );

        if (tournamentOptions.getTournamentType() != tournamentType) {
            throw new MismatchedTournamentOptionsException(
                tournamentType.name(),
                tournamentOptions.getTournamentType().name()
            );
        }

        //TournamentType originalTournamentType = this.getTournamentType();
        TournamentDescription originalTournamentOptions = this.getTournamentOptions();
        try {
            //this.tournamentType = tournamentType;
            this.tournamentOptions = tournamentOptions;
            this.update();
        }
        catch (ChallongeException e) {
            //this.tournamentType = originalTournamentType;
            this.tournamentOptions = originalTournamentOptions;
            throw e;
        }
    }

    public void delete() throws ChallongeException {
        this.api.scopes.requirePermissionScope(Scope.TOURNAMENTS_WRITE);
        this.api.apiDelete(ChallongeApi.toURI("tournaments", this.getId()));
    }

    public boolean isPrivate() {
        return this.isPrivate;
    }

    public String getDescription() {
        return this.description;
    }

    public String getName() {
        return this.name;
    }

    public String getUrl() {
        return this.url;
    }

    /*public TournamentType getTournamentType() {
        return this.tournamentType;
    }*/

    public TournamentDescription getTournamentOptions() {
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