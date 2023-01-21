package main.java;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.util.HashMap;

import javax.lang.model.type.NullType;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import main.java.Exceptions.MalformedAuthException;
import main.java.Exceptions.MissingTokenException;

final class ChallongeAuthorization {
    private final HashMap<String, String> data;
    private final File file;
    private String access_token;

    private void assertField(String field) throws MalformedAuthException {
        Object value = this.data.get(field);
        if (value == null || !(value instanceof String)) {
            Class<?> valueClass = value == null ? NullType.class : value.getClass();
            throw new MalformedAuthException(this.file.getName(), field, String.class, valueClass);
        }
    }

    @SuppressWarnings("unchecked")
    public ChallongeAuthorization(File authFile) throws FileNotFoundException, MalformedAuthException, IOException {
        this.file = authFile;

        FileReader reader = new FileReader(this.file);

        this.data = (HashMap<String, String>)JSONValue.parse(reader);

        this.assertField("client_id");
        this.assertField("client_secret");
        this.assertField("redirect_uri");
        this.assertField("refresh_token");
    }

    private void addAuthorizationHeader(HttpRequest.Builder request) {
        request.header(
            "Authorization",
            String.format("Bearer %s", this.access_token)
        );
    }

    @SuppressWarnings("unchecked")
    private void refreshAccessToken(HttpClient client) throws IOException, InterruptedException, MissingTokenException {
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

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        HashMap<String, String> parsed = (HashMap<String, String>)JSONValue.parse(response.body());

        String accessToken = parsed.get("access_token");
        String refreshToken = parsed.get("refresh_token");

        if (accessToken == null || refreshToken == null) {
            throw new MissingTokenException();
        }

        this.data.put("refresh_token", refreshToken);
        FileWriter writer = new FileWriter(this.file);
        JSONObject.writeJSONString(this.data, writer);
        writer.close();

        this.access_token = accessToken;
    }
}