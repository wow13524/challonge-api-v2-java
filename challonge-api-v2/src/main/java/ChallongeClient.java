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
            this.api.apiGet(ChallongeApi.toURI("me")).get("data"),
            JSONObject.class,
            "data"
        ));
    }

    public Object[] tournaments() throws ChallongeException {
        this.api.scope.requirePermissionScope(Scope.TOURNAMENTS_READ);
        JSONObject response = this.api.apiGet(ChallongeApi.toURI("tournaments"));
        JSONObject meta = TypeUtils.requireType(
            response.get("meta"),
            JSONObject.class,
            "meta"
        );
        long count = TypeUtils.requireType(
            meta.get("count"),
            Long.class,
            "count"
        );

        Object[] tournaments = new Object[(int)count];
        int i = 0;
        while (true) {
            JSONArray data = TypeUtils.requireType(
                response.get("data"),
                JSONArray.class,
                "data"
            );

            for (Object raw : data) {
                JSONObject tournament = TypeUtils.requireType(
                    raw,
                    JSONObject.class
                );
                tournaments[i++] = tournament;
            }

            if (i < count) {
                JSONObject links = TypeUtils.requireType(
                    response.get("links"),
                    JSONObject.class,
                    "links"
                );
                response = this.api.apiGet(URI.create(TypeUtils.requireType(
                    links.get("next"),
                    String.class,
                    "next"
                )));
            }
            else {
                break;
            }
        }

        return tournaments;
    }
}