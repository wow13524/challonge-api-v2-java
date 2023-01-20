package main.java;

import java.io.IOException;
import java.net.http.HttpClient;
import java.util.Objects;

import main.java.Exceptions.MissingTokenException;

public class ChallongeClient {
    private ChallongeAuthorization auth;
    private HttpClient client;
    private String accessToken;
    
    public ChallongeClient(ChallongeAuthorization auth) throws IOException, InterruptedException, MissingTokenException {
        this.auth = Objects.requireNonNull(auth, "auth is null");
        this.client = HttpClient.newHttpClient();
        this.accessToken = auth.getAccessToken(client);
    }
}