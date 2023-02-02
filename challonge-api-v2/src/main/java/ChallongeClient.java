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

    public ChallongeUser me() throws ChallongeException {
        this.api.scope.requirePermissionScope(Scope.ME);
        return new ChallongeUser(TypeUtils.requireType(
            this.api.apiGet(ChallongeApi.toURI("me")),
            "data",
            JSONObject.class
        ));
    }

    public Object[] tournaments() throws ChallongeException {
        this.api.scope.requirePermissionScope(Scope.TOURNAMENTS_READ);
        JSONObject response = this.api.apiGet(ChallongeApi.toURI("tournaments"));
        JSONObject meta =
        TypeUtils.requireType(response, "meta", JSONObject.class);
        long count =
        TypeUtils.requireType(meta, "count", Long.class);

        Object[] tournaments = new Object[(int)count];
        int i = 0;
        while (true) {
            JSONArray data =
            TypeUtils.requireType(response, "data", JSONArray.class);

            for (Object raw : data) {
                JSONObject tournament = 
                TypeUtils.requireType(raw, JSONObject.class);
                tournaments[i++] = tournament;
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
}