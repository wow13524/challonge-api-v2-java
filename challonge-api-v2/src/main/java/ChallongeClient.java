package main.java;

import java.io.File;
import java.io.IOException;
import java.net.http.HttpClient;
import java.util.Objects;

import main.java.Exceptions.MalformedAuthException;
import main.java.Exceptions.MissingTokenException;

public class ChallongeClient {
    private ChallongeAuthorization auth;
    private HttpClient client;

    public ChallongeClient(File authFile) throws IOException, InterruptedException, MalformedAuthException, MissingTokenException {
        Objects.requireNonNull(authFile, "auth is null");
        this.auth = new ChallongeAuthorization(authFile);
        this.client = HttpClient.newHttpClient();
    }

    public ChallongeClient(String authFilePath) throws IOException, InterruptedException, MalformedAuthException, MissingTokenException {
        this(new File(authFilePath));
    }
}