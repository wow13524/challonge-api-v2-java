package main.java;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public final class ChallongeAuthorization {
    private final String clientId, clientSecret, redirectUri;
    private String refreshToken;
    private FileReader reader;
    private FileWriter writer;

    public ChallongeAuthorization(File authFile) {
        try {
            this.reader = new FileReader(authFile);
            this.writer = new FileWriter(authFile);
        }
        catch (FileNotFoundException e) {
            //TODO error handling for FileNotFoundException
        }
        catch (IOException e) {
            //TODO error handling for IOException
        }

        JSONObject data = (JSONObject)JSONValue.parse(reader);
        Object clientId = data.get("client_id");
        Object clientSecret = data.get("client_secret");
        Object redirectUri = data.get("redirect_uri");
        Object refreshToken = data.get("refresh_token");
        assert clientId instanceof String : "client_id must be a String";
        assert clientSecret instanceof String : "client_secret must be a String";
        assert redirectUri instanceof String : "redirect_uri must be a String";
        assert refreshToken instanceof String : "refresh_token must be a String";

        this.clientId = (String)clientId;
        this.clientSecret = (String)clientSecret;
        this.redirectUri = (String)redirectUri;
        this.refreshToken = (String)refreshToken;
    }
}