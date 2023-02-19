package main.java;

import java.io.File;
import java.net.URI;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import main.java.Exceptions.ChallongeException;

public class ChallongeClient {
    private ChallongeApi api;

    public ChallongeClient(File authFile) throws ChallongeException {
        TypeUtils.requireType(authFile, File.class, "authFile");
        this.api = new ChallongeApi(authFile);
    }

    public ChallongeClient(String authFilePath) throws ChallongeException {
        this(
            new File(
                TypeUtils.requireType(
                    authFilePath,
                    String.class,
                    "authFilePath"
                )
            )
        );
    }

    public ChallongeUser getMe() throws ChallongeException {
        this.api.scopes.requirePermissionScope(Scope.ME);
        return new ChallongeUser(
            this.api,
            this.api.apiGet(ChallongeApi.toURI("me"))
        );
    }

    public ChallongeTournament[] getAllTournaments() throws ChallongeException {
        this.api.scopes.requirePermissionScope(Scope.TOURNAMENTS_READ);
        JSONObject response = this.api.apiGet(ChallongeApi.toURI("tournaments"));
        JSONObject meta =
        TypeUtils.requireType(response, "meta", JSONObject.class);
        int count =
        (int)(long)TypeUtils.requireType(meta, "count", Long.class);

        ChallongeTournament[] tournaments =
        new ChallongeTournament[count];
        int i = 0;
        while (true) {
            JSONArray data =
            TypeUtils.requireType(response, "data", JSONArray.class);

            for (Object raw : data) {
                JSONObject tournament = 
                TypeUtils.requireType(raw, JSONObject.class);
                tournaments[i++] = new ChallongeTournament(this.api, tournament);
            }

            if (i < count) {
                JSONObject links =
                TypeUtils.requireType(response, "links", JSONObject.class);
                response =
                this.api.apiGet(URI.create(
                    TypeUtils.requireType(links, "next", String.class)
                ));
            }
            else {
                break;
            }
        }

        return tournaments;
    }

    public ChallongeTournament getTournament(String tournamentId) throws ChallongeException {
        this.api.scopes.requirePermissionScope(Scope.TOURNAMENTS_READ);
        TypeUtils.requireType(tournamentId, String.class, "tournamentId");
        JSONObject response =
        this.api.apiGet(ChallongeApi.toURI("tournaments", tournamentId));
        JSONObject data =
        TypeUtils.requireType(response, "data", JSONObject.class);
        return new ChallongeTournament(this.api, data);
    }
}