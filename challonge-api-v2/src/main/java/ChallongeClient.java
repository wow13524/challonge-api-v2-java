package main.java;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;

public class ChallongeClient {
    private static final String GRANT_REDIRECT_URI = "";
    private static final String GRANT_REQUEST_URI = "https://api.challonge.com/oauth/authorize?scope=%s&client_id=%s&redirect_uri=%s&response_type=code";

    private ChallongeAuthorization auth;
    private HttpClient client;
    private String accessToken;
    
    public ChallongeClient(ChallongeAuthorization auth) {
        this.auth = auth;
        this.client = HttpClient.newHttpClient();
        this.accessToken = getNewAccessToken();
    }

    private String getNewAccessToken() {
        HashMap<String, String> body = new HashMap<String, String>();
        body.put("refresh_token", this.auth.refreshToken);
        body.put("client_id", this.auth.clientId);
        body.put("grant_type", "refresh_token");
        body.put("redirect_uri", this.auth.redirect_uri);

        HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create("https://api.challonge.com/oauth/token"))
        .header("Content-Type", "application/x-www-form-urlencoded")
        .POST(HttpRequest.BodyPublishers.ofString(EncodeUtils.encodeFormBody(body)))
        .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject parsed = (JSONObject)JSONValue.parse(response.body());
            System.out.println(body.toString());
            System.out.println(response.body());
            System.out.println(parsed.toJSONString());
        }
        catch(Exception e) {
            System.out.println("Exception! " + e);
        }
        return "";
    }
}