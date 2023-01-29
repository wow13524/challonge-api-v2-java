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
    private static final String REFRESH_TOKEN_ENDPOINT = "https://api.challonge.com/oauth/token";

    private final ChallongeAuthorizationNew auth;
    private final HttpClient httpClient;
    private final JSONParser jsonParser;
    public final Scope scope;

    private String access_token;
    private long token_expires_at;

    public ChallongeApi(File authFile) throws IOException, MissingTokenException, ParseException, UnexpectedTypeException {
        TypeUtils.requireType(authFile, ChallongeAuthorizationNew.class, "authFile");
        this.auth = new ChallongeAuthorizationNew(authFile);
        this.httpClient = HttpClient.newHttpClient();
        this.jsonParser = new JSONParser();

        JSONObject rawRequest = this.rawRefreshTokenRequest();
        this.rawParseRefreshTokenResponse(rawRequest);
        
        this.scope = new Scope(TypeUtils.requireType(rawRequest.get("scope"), String.class, "scope"));
    }

    private JSONObject rawRefreshTokenRequest() throws MissingTokenException {
        HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(REFRESH_TOKEN_ENDPOINT))
        .header("Content-Type", "application/x-www-form-urlencoded")
        .POST(HttpRequest.BodyPublishers.ofString(EncodeUtils.encodeFormBody(this.auth.refreshRequestBody)))
        .build();

        try {
            HttpResponse<String> response = this.httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return (JSONObject)this.jsonParser.parse(response.body());
        }
        catch (IOException | InterruptedException | ParseException e) {
            throw new MissingTokenException();
        }
    }

    private void rawParseRefreshTokenResponse(JSONObject response) throws IOException, MissingTokenException {
        try {
            String accessToken = TypeUtils.requireType(response.get("access_token"), String.class);
            String refreshToken = TypeUtils.requireType(response.get("refresh_token"), String.class);
            long expires_in = TypeUtils.requireType(response.get("expires_in"), Long.class);

            this.auth.updateRefreshToken(refreshToken);

            this.access_token = accessToken;
            this.token_expires_at = System.currentTimeMillis() / 1000 + expires_in;
        }
        catch (UnexpectedTypeException e) {
            throw new MissingTokenException();
        }
    }

    private void refreshToken() throws IOException, InterruptedException, MissingTokenException {
        this.rawParseRefreshTokenResponse(this.rawRefreshTokenRequest());
    }

    private HttpRequest.Builder newRequest() throws IOException, InterruptedException, MissingTokenException {
        if (System.currentTimeMillis() / 1000 > this.token_expires_at) {
            this.refreshToken();
        }
        return HttpRequest.newBuilder().header("Authorization", String.format("Bearer %s", this.access_token))
        .header("Authorization-Type", "v2")
        .header("Content-Type", "application/vnd.api+json")
        .header("Accept", "application/json");
    }

    public JSONObject apiGet(URI uri) throws IOException, InterruptedException, MissingTokenException, ParseException {
        HttpRequest request = newRequest()
        .uri(uri)
        .GET()
        .build();

        HttpResponse<String> response = this.httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return (JSONObject)this.jsonParser.parse(response.body());
    }

    public JSONObject apiPost(URI uri, BodyPublisher body) throws IOException, InterruptedException, MissingTokenException, ParseException {
        HttpRequest request = newRequest()
        .uri(uri)
        .POST(body)
        .build();

        HttpResponse<String> response = this.httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return (JSONObject)this.jsonParser.parse(response.body());
    }
}