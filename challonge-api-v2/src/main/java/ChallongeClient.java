package main.java;

import java.io.IOException;
import java.net.http.HttpClient;

public class ChallongeClient {
    private static final String GRANT_REDIRECT_URI = "";
    private static final String GRANT_REQUEST_URI = "https://api.challonge.com/oauth/authorize?scope=%s&client_id=%s&redirect_uri=%s&response_type=code";

    private ChallongeAuthorization auth;
    private HttpClient client;
    private String accessToken;
    
    public ChallongeClient(ChallongeAuthorization auth) throws IOException, InterruptedException {
        this.auth = auth;
        this.client = HttpClient.newHttpClient();
        this.accessToken = auth.getAccessToken(client);
    }
}