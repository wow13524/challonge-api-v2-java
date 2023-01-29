package main.java;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.util.HashMap;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import main.java.Exceptions.UnexpectedTypeException;
import main.java.Exceptions.MissingTokenException;

final class ChallongeAuthorizationOld {
    private final HttpClient client;
    private final HashMap<String, String> data;
    private final File file;
    private String access_token;
    private long token_expires_at;

    @SuppressWarnings("unchecked")
    public ChallongeAuthorizationOld(File authFile) throws IOException, ParseException, UnexpectedTypeException {
        this.client = HttpClient.newHttpClient();
        this.file = authFile;
        this.access_token = null;
        this.token_expires_at = -1;

        FileReader reader = new FileReader(this.file);

        this.data = (HashMap<String, String>)(new JSONParser()).parse(reader);

        TypeUtils.requireType(this.data.get("client_id"), String.class, "client_id");
        TypeUtils.requireType(this.data.get("client_secret"), String.class, "client_secret");
        TypeUtils.requireType(this.data.get("redirect_uri"), String.class, "redirect_uri");
        TypeUtils.requireType(this.data.get("refresh_token"), String.class, "refresh_token");
    }

    private HttpRequest.Builder newRequest() throws IOException, InterruptedException, MissingTokenException {
        if (System.currentTimeMillis() / 1000 > this.token_expires_at) {
            refreshAccessToken();
        }
        return HttpRequest.newBuilder().header("Authorization", String.format("Bearer %s", this.access_token))
        .header("Authorization-Type", "v2")
        .header("Content-Type", "application/vnd.api+json")
        .header("Accept", "application/json");
    }

    public HttpResponse<String> apiGet(URI uri) throws IOException, InterruptedException, MissingTokenException {
        HttpRequest request = newRequest()
        .uri(uri)
        .build();

        return this.client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    private void refreshAccessToken() throws IOException, InterruptedException, MissingTokenException {
        HashMap<String, String> body = new HashMap<String, String>();
        body.put("refresh_token", this.data.get("refresh_token"));
        body.put("client_id", this.data.get("client_id"));
        body.put("grant_type", "refresh_token");
        body.put("redirect_uri", this.data.get("redirect_uri"));

        HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create("https://api.challonge.com/oauth/token"))
        .header("Content-Type", "application/x-www-form-urlencoded")
        .POST(HttpRequest.BodyPublishers.ofString(EncodeUtils.encodeFormBody(body)))
        .build();

        HttpResponse<String> response = this.client.send(request, HttpResponse.BodyHandlers.ofString());

        try {
            JSONObject parsed = (JSONObject)(new JSONParser()).parse(response.body());

            System.out.println(new Scope((String)parsed.get("scope")));

            String accessToken = TypeUtils.requireType(parsed.get("access_token"), String.class);
            String refreshToken = TypeUtils.requireType(parsed.get("refresh_token"), String.class);
            long expires_in = TypeUtils.requireType(parsed.get("expires_in"), Long.class);
    
            this.data.put("refresh_token", refreshToken);
            FileWriter writer = new FileWriter(this.file);
            JSONObject.writeJSONString(this.data, writer);
            writer.close();
    
            this.access_token = accessToken;
            this.token_expires_at = System.currentTimeMillis() / 1000 + expires_in;
        }
        catch (ParseException | UnexpectedTypeException e) {
            throw new MissingTokenException();
        }
    }
}