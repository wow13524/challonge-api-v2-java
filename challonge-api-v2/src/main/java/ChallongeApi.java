package main.java;

import java.io.File;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublisher;
import java.net.URI;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import main.java.Exceptions.UnexpectedTypeException;
import main.java.Exceptions.MissingTokenException;

public class ChallongeApi {
    private static final String API_ENDPOINT = "https://api.challonge.com/v2";

    private final ChallongeAuthorizationNew auth;
    private final HttpClient httpClient;
    private final JSONParser jsonParser;
    
    private String access_token;
    private long token_expires_at;

    public ChallongeApi(File authFile) throws IOException, ParseException, UnexpectedTypeException {
        TypeUtils.requireType(authFile, ChallongeAuthorizationNew.class, "authFile");
        this.auth = new ChallongeAuthorizationNew(authFile);
        this.httpClient = HttpClient.newHttpClient();
        this.jsonParser = new JSONParser();
    }

    private HttpRequest.Builder newRequest() {
        return HttpRequest.newBuilder().header("Authorization", String.format("Bearer %s", this.access_token))
        .header("Authorization-Type", "v2")
        .header("Content-Type", "application/vnd.api+json")
        .header("Accept", "application/json");
    }

    public JSONObject apiGet(URI uri) throws IOException, InterruptedException, ParseException {
        HttpRequest request = newRequest()
        .uri(uri)
        .GET()
        .build();

        HttpResponse<String> response = this.httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return (JSONObject)this.jsonParser.parse(response.body());
    }

    public JSONObject apiPost(URI uri, BodyPublisher body) throws IOException, InterruptedException, ParseException {
        HttpRequest request = newRequest()
        .uri(uri)
        .POST(body)
        .build();

        HttpResponse<String> response = this.httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return (JSONObject)this.jsonParser.parse(response.body());
    }
}