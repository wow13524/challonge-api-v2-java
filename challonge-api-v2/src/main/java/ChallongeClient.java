package main.java;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;

import main.java.Exceptions.UnexpectedTypeException;
import main.java.Exceptions.MissingTokenException;

public class ChallongeClient {
    private static final String API_ENDPOINT = "https://api.challonge.com/v2";

    private ChallongeAuthorization auth;
    private HttpClient client;

    private static URI apiUri(String... path) {
        String endpoint = API_ENDPOINT;
        for (String x: path) {
            endpoint += "/" + x;
        }
        return URI.create(endpoint + ".json");
    }

    public ChallongeClient(File authFile) throws IOException, InterruptedException, UnexpectedTypeException, MissingTokenException {
        Objects.requireNonNull(authFile, "auth is null");
        this.auth = new ChallongeAuthorization(authFile);
        this.client = HttpClient.newHttpClient();
    }

    public ChallongeClient(String authFilePath) throws IOException, InterruptedException, UnexpectedTypeException, MissingTokenException {
        this(new File(authFilePath));
    }

    public void tournaments() throws IOException, InterruptedException, MissingTokenException {
        HttpRequest request = this.auth.addAuthorizationHeader(HttpRequest.newBuilder())
        .uri(apiUri("tournaments"))
        .build();

        HttpResponse<String> response = this.client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
    }
}