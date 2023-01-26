package main.java;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Objects;

import org.json.simple.parser.ParseException;

import main.java.Exceptions.UnexpectedTypeException;
import main.java.Exceptions.MissingTokenException;

public class ChallongeClient {
    private static final String API_ENDPOINT = "https://api.challonge.com/v2";

    private ChallongeAuthorization auth;

    private static URI apiUri(String... path) {
        String endpoint = API_ENDPOINT;
        for (String x: path) {
            endpoint += "/" + x;
        }
        return URI.create(endpoint + ".json");
    }

    public ChallongeClient(File authFile) throws IOException, ParseException, UnexpectedTypeException {
        Objects.requireNonNull(authFile, "auth is null");
        this.auth = new ChallongeAuthorization(authFile);
    }

    public ChallongeClient(String authFilePath) throws IOException, ParseException, UnexpectedTypeException {
        this(new File(authFilePath));
    }

    public void tournaments() throws IOException, InterruptedException, MissingTokenException {
        System.out.println(this.auth.apiGet(apiUri("tournaments")).body());
    }
}