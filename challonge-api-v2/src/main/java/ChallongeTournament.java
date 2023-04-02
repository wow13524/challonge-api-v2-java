package main.java;

import java.time.Instant;

import org.json.simple.JSONObject;

import main.java.Exceptions.ChallongeException;

public class ChallongeTournament extends ChallongeObject {

    private boolean isPrivate;
    private String description, name, url;
    private TournamentOptions tournamentOptions;
    private Instant createdAt, completedAt, startsAt, startedAt, updatedAt;

    ChallongeTournament(ChallongeApi api, JSONObject json) throws ChallongeException {
        super(
            api,
            TypeUtils.requireType(json, "id", String.class),
            TypeUtils.requireType(json, "type", String.class)
        );

        JSONObject attributes =
        TypeUtils.requireType(json, "attributes", JSONObject.class);

        this.loadFromAttributes(attributes);
    }

    private static TournamentOptions parseTournamentOptions(JSONObject attributes, TournamentType tournamentType) throws ChallongeException {
        switch (tournamentType) {
            case SINGLE_ELIMINATION:
            return new SingleEliminationOptions(attributes);
            case DOUBLE_ELIMINATION:
                return new DoubleEliminationOptions(attributes);
            case ROUND_ROBIN:
                return new RoundRobinOptions(attributes);
            case SWISS:
                return new SwissOptions(attributes);
            case FREE_FOR_ALL:
                return new FreeForAllOptions(attributes);
            default:
                throw new ChallongeException(
                    String.format(
                        "Unsupported tournamentType '%s'",
                        tournamentType.name
                    )
                );
        }
    }

    private void loadFromAttributes(JSONObject attributes) throws ChallongeException {
        this.isPrivate =
        TypeUtils.requireType(attributes, "private", Boolean.class);

        this.description =
        TypeUtils.requireType(attributes, "description", String.class);

        this.name =
        TypeUtils.requireType(attributes, "name", String.class);

        this.url = 
        TypeUtils.requireType(attributes, "url", String.class);

        String tournamentTypeString = 
        TypeUtils.requireType(attributes, "tournamentType", String.class);

        TournamentType tournamentType =
        EnumUtils.valueFromString(
            TournamentType.class,
            tournamentTypeString,
            String::startsWith
            );

        this.tournamentOptions =
        ChallongeTournament.parseTournamentOptions(attributes, tournamentType);

        JSONObject timestamps =
        TypeUtils.requireType(attributes, "timestamps", JSONObject.class);
        String createdAt = TypeUtils.requireOptionalType(
            timestamps,
            "createdAt",
            String.class
        );
        String completedAt = TypeUtils.requireOptionalType(
            timestamps,
            "completedAt",
            String.class
        );
        String startsAt = TypeUtils.requireOptionalType(
            timestamps,
            "startsAt",
            String.class
        );
        String startedAt = TypeUtils.requireOptionalType(
            timestamps,
            "startedAt",
            String.class
        );
        String updatedAt = TypeUtils.requireOptionalType(
            timestamps,
            "updatedAt",
            String.class
        );

        this.createdAt =
        createdAt == null ? null : Instant.parse(createdAt);

        this.completedAt =
        completedAt == null ? null : Instant.parse(completedAt);

        this.startsAt =
        startsAt == null ? null : Instant.parse(startsAt);

        this.startedAt =
        startedAt == null ? null : Instant.parse(startedAt);

        this.updatedAt =
        updatedAt == null ? null : Instant.parse(updatedAt);
    }

    @SuppressWarnings("unchecked")
    private JSONObject toAttributes() {
        JSONObject attributes = new JSONObject();
        attributes.put("name", this.getName());
        attributes.put("url", this.getUrl());
        attributes.put(
            "tournament_type",
            this.getTournamentOptions().getTournamentType().name
        );
        attributes.put("private", this.isPrivate());
        //
        attributes.put("description", this.getDescription());
        //
        TournamentOptions options = this.getTournamentOptions();
        if (options.getTournamentType().key != null) {
            attributes.put(
                options.getKey(),
                options.getOptions()
            );
        }

        return attributes;
    }

    @SuppressWarnings("unchecked")
    private void update() throws ChallongeException {
        JSONObject body = new JSONObject();
        JSONObject data = new JSONObject();

        body.put("data", data);
        data.put("type", "tournaments");
        data.put("attributes", this.toAttributes());
        
        try {
            this.api.apiPut(
                ChallongeApi.toURI("tournaments", this.getId()),
                body
            );
        }
        catch (ChallongeException e) {
            JSONObject response =
            this.api.apiGet(ChallongeApi.toURI("tournaments", this.getId()));
            JSONObject rData =
            TypeUtils.requireType(response, "data", JSONObject.class);
            JSONObject rAttributes =
            TypeUtils.requireType(rData, "attributes", JSONObject.class);
            this.loadFromAttributes(rAttributes);
            throw e;
        }
    }

    public void setName(String name) throws ChallongeException {
        this.api.scopes.requirePermissionScope(Scope.TOURNAMENTS_WRITE);
        TypeUtils.requireType(
            name,
            String.class,
            "name"
        );
        this.name = name;
        this.update();
    }

    public void setTournamentOptions(TournamentOptions tournamentOptions) throws ChallongeException {
        this.api.scopes.requirePermissionScope(Scope.TOURNAMENTS_WRITE);
        TypeUtils.requireType(
            tournamentOptions,
            TournamentOptions.class,
            "tournamentOptions"
        );
        this.tournamentOptions = tournamentOptions;
        this.update();
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

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public Instant getCompletedAt() {
        return this.completedAt;
    }

    public Instant getStartsAt() {
        return this.startsAt;
    }

    public Instant getStartedAt() {
        return this.startedAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public String getUrl() {
        return this.url;
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