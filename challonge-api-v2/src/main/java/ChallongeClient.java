package main.java;

import java.io.File;

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

    public void tournaments() throws ChallongeException {
        this.api.scope.requirePermissionScope(Scope.TOURNAMENTS_READ);
        System.out.println(this.api.apiGet(ChallongeApi.toURI("tournaments")));
    }
}