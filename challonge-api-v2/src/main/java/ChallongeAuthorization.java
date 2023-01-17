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
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public final class ChallongeAuthorization {
    private final HashMap<String, String> data;
    private final File file;

    private void assertField(String field) throws IllegalArgumentException {
        if (this.data.get(field) == null) {
            System.out.println(field + " " + this.data.get(field));
            throw new IllegalArgumentException(String.format(
                "%s: field '%s' must be a string",
                this.file.getName(),
                field
            ));
        }
    }

    @SuppressWarnings("unchecked")
    public ChallongeAuthorization(File authFile) throws FileNotFoundException, IllegalArgumentException, IOException {
        this.file = authFile;

        FileReader reader = new FileReader(this.file);

        this.data = (HashMap<String, String>)JSONValue.parse(reader);

        this.assertField("client_id");
        this.assertField("client_secret");
        this.assertField("redirect_uri");
        this.assertField("refresh_token");
    }

    public ChallongeAuthorization(String authFilePath) throws FileNotFoundException, IOException {
        this(new File(authFilePath));
    }

    @SuppressWarnings("unchecked")
    public String getAccessToken(HttpClient client) throws IOException, InterruptedException {
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
        assert accessToken != null : "Failed to retrieve access token";
        assert refreshToken != null : "Failed to retrieve refresh token";

        this.data.put("refresh_token", refreshToken);
        FileWriter writer = new FileWriter(this.file);
        JSONObject.writeJSONString(this.data, writer);
        writer.close();

        return accessToken;
    }
}