package main.java;

import java.io.File;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.util.Map;
import java.net.URI;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import main.java.Exceptions.*;

final class ChallongeApi {
    private static final String API_ENDPOINT = "https://api.challonge.com/v2/";
    private static final String REFRESH_TOKEN_ENDPOINT = "https://api.challonge.com/oauth/token/";

    private final ChallongeAuthorization auth;
    private final HttpClient httpClient;
    private final JSONParser jsonParser;
    public final Scopes scopes;

    private String access_token;
    private long token_expires_at;

    public static URI toURI(String... path) {
        return URI.create(
            API_ENDPOINT
            + String.join("/", path)
            + ".json"
        );
    }

    public ChallongeApi(File authFile) throws ChallongeException {
        this.auth = new ChallongeAuthorization(authFile);
        this.httpClient = HttpClient.newHttpClient();
        this.jsonParser = new JSONParser();

        JSONObject rawRequest = this.rawRefreshTokenRequest();
        this.rawParseRefreshTokenResponse(rawRequest);

        this.scopes =
        new Scopes(TypeUtils.requireType(rawRequest,"scope",String.class));
    }

    private JSONObject rawRefreshTokenRequest() throws ChallongeException {
        HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(REFRESH_TOKEN_ENDPOINT))
        .header("Content-Type", "application/x-www-form-urlencoded")
        .POST(
            HttpRequest.BodyPublishers.ofString(
                EncodeUtils.encodeFormBody(this.auth.refreshRequestBody)
            )
        )
        .build();
        
        try {
            HttpResponse<String> response = this.httpClient.send(
                request,
                HttpResponse.BodyHandlers.ofString()
            );

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
            accessToken =
            TypeUtils.requireType(response, "access_token", String.class);

            refreshToken =
            TypeUtils.requireType(response, "refresh_token", String.class);

            expires_in =
            TypeUtils.requireType(response, "expires_in", Long.class);
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

    private HttpResponse<String> sendApiRequest(HttpRequest request) throws ChallongeException {
        try {
            System.out.println(request.uri());
            return this.httpClient.send(
                request,
                HttpResponse.BodyHandlers.ofString()
            );
        }
        catch (IOException | InterruptedException e) {
            throw new FailedRequestException(e);
        }
    }

    @SuppressWarnings("unchecked")
    private JSONObject parseApiResponse(HttpResponse<String> response) throws ChallongeException {
        try {
            JSONObject parsedReponse = TypeUtils.requireType(
                this.jsonParser.parse(response.body()),
                JSONObject.class
            );
            System.out.println(parsedReponse + "\n");

            if (parsedReponse.containsKey("errors")) {
                Object errors = parsedReponse.get("errors");
                if (errors instanceof JSONArray) {
                    throw new ApiException((JSONArray)errors);
                }
                else {
                    throw new ApiException((JSONObject)errors);
                }
            }
            else if (parsedReponse.containsKey("error")) {
                parsedReponse.put("detail", parsedReponse.get("error"));
                throw new ApiException(parsedReponse);
            }

            return parsedReponse;
        }
        catch (ParseException | UnexpectedTypeException e) {
            throw new UnexpectedResponseException(e);
        }
    }

    private JSONObject sendAndParseApiRequest(HttpRequest request) throws ChallongeException {
        return parseApiResponse(sendApiRequest(request));
    }

    public void apiDelete(URI uri) throws ChallongeException {
        HttpRequest request = newRequest()
        .uri(uri)
        .DELETE()
        .build();

        sendApiRequest(request);
    }

    public JSONObject apiGet(URI uri) throws ChallongeException {
        HttpRequest request = newRequest()
        .uri(uri)
        .GET()
        .build();

        return sendAndParseApiRequest(request);
    }

    public JSONObject apiPost(URI uri, Map<String, Object> body) throws ChallongeException {
        HttpRequest request = newRequest()
        .uri(uri)
        .POST(BodyPublishers.ofString(JSONObject.toJSONString(body)))
        .build();

        return sendAndParseApiRequest(request);
    }

    public JSONObject apiPut(URI uri, Map<String, Object> body) throws ChallongeException {
        System.out.println(body);
        HttpRequest request = newRequest()
        .uri(uri)
        .PUT(BodyPublishers.ofString(JSONObject.toJSONString(body)))
        .build();

        return sendAndParseApiRequest(request);
    }
}