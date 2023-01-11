package main.java;

import java.net.http.*;
import java.net.http.HttpClient.Version;

public class ChallongeClient {
    private static final String GRANT_REDIRECT_URI = "";
    private static final String GRANT_REQUEST_URI = "https://api.challonge.com/oauth/authorize?scope=%s&client_id=%s&redirect_uri=%s&response_type=code";

    private HttpClient client;
    private String accessToken;
    
    public ChallongeClient(ChallongeAuthorization auth) {
        this.client = HttpClient.newHttpClient();
    }
}