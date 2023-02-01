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
import main.java.Exceptions.ChallongeException;
import main.java.Exceptions.FailedRequestException;
import main.java.Exceptions.MissingTokenException;
import main.java.Exceptions.UnexpectedResponseException;

final class ChallongeApi {
    private static final String API_ENDPOINT = "https://api.challonge.com/v2";
    private static final String REFRESH_TOKEN_ENDPOINT = "https://api.challonge.com/oauth/token";

    private final ChallongeAuthorization auth;
    private final HttpClient httpClient;
    private final JSONParser jsonParser;
    public final Scope scope;

    private String access_token;
    private long token_expires_at;

    public static URI toURI(String... path) {
        String endpoint = API_ENDPOINT;
        for (String x: path) {
            endpoint += "/" + x;
        }
        return URI.create(endpoint + ".json");
    }

    public ChallongeApi(File authFile) throws ChallongeException {
        TypeUtils.requireType(authFile, File.class, "authFile");
        this.auth = new ChallongeAuthorization(authFile);
        this.httpClient = HttpClient.newHttpClient();
        this.jsonParser = new JSONParser();

        JSONObject rawRequest = this.rawRefreshTokenRequest();
        this.rawParseRefreshTokenResponse(rawRequest);

        this.scope =
        new Scope(
            TypeUtils.requireType(
                rawRequest.get("scope"),
                String.class,
                "scope"
            )
        );
    }

    private JSONObject rawRefreshTokenRequest() throws ChallongeException {
        HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(REFRESH_TOKEN_ENDPOINT))
        .header("Content-Type", "application/x-www-form-urlencoded")
        .POST(HttpRequest.BodyPublishers.ofString(EncodeUtils.encodeFormBody(this.auth.refreshRequestBody)))
        .build();

        try {
            HttpResponse<String> response = this.httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return (JSONObject)this.jsonParser.parse(response.body());
        }
        catch (IOException | InterruptedException e) {
            throw new FailedRequestException(e);
        }
        catch (ParseException e) {
            throw new UnexpectedResponseException(e);
        }
    }

    private void rawParseRefreshTokenResponse(JSONObject response) throws ChallongeException {
        String accessToken, refreshToken;
        long expires_in;

        try {
            accessToken = TypeUtils.requireType(response.get("access_token"), String.class);
            refreshToken = TypeUtils.requireType(response.get("refresh_token"), String.class);
            expires_in = TypeUtils.requireType(response.get("expires_in"), Long.class);
        }
        catch (UnexpectedTypeException e) {
            throw new MissingTokenException(e);
        }

        this.auth.updateRefreshToken(refreshToken);

        this.access_token = accessToken;
        this.token_expires_at = System.currentTimeMillis() / 1000 + expires_in;
    }

    private void refreshToken() throws ChallongeException {
        this.rawParseRefreshTokenResponse(this.rawRefreshTokenRequest());
    }

    private HttpRequest.Builder newRequest() throws ChallongeException {
        if (System.currentTimeMillis() / 1000 > this.token_expires_at) {
            this.refreshToken();
        }

        return HttpRequest.newBuilder()
        .header("Authorization", String.format("Bearer %s", this.access_token))
        .header("Authorization-Type", "v2")
        .header("Content-Type", "application/vnd.api+json")
        .header("Accept", "application/json");
    }

    private JSONObject sendAndParseApiRequest(HttpRequest request) throws ChallongeException {
        try {
            HttpResponse<String> response = this.httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return 
            TypeUtils.requireType(
                this.jsonParser.parse(response.body()),
                JSONObject.class
            );
        }
        catch (IOException | InterruptedException e) {
            throw new FailedRequestException(e);
        }
        catch (ParseException | UnexpectedTypeException e) {
            throw new UnexpectedResponseException(e);
        }
    }

    public JSONObject apiGet(URI uri) throws ChallongeException {
        HttpRequest request = newRequest()
        .uri(uri)
        .GET()
        .build();

        return sendAndParseApiRequest(request);
    }

    public JSONObject apiPost(URI uri, BodyPublisher body) throws ChallongeException {
        HttpRequest request = newRequest()
        .uri(uri)
        .POST(body)
        .build();

        return sendAndParseApiRequest(request);
    }
}