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
import main.java.Exceptions.UnexpectedTypeException;
import main.java.Exceptions.MissingTokenException;

final class ChallongeAuthorization {
    private final HttpClient client;
    private final HashMap<String, String> data;
    private final File file;
    private String access_token;
    private long token_expires_at;

    @SuppressWarnings("unchecked")
    public ChallongeAuthorization(File authFile) throws FileNotFoundException, UnexpectedTypeException, IOException {
        this.client = HttpClient.newHttpClient();
        this.file = authFile;
        this.access_token = null;
        this.token_expires_at = -1;

        FileReader reader = new FileReader(this.file);

        this.data = (HashMap<String, String>)JSONValue.parse(reader);

        TypeUtils.requireType(this.data.get("client_id"), String.class);
        TypeUtils.requireType(this.data.get("client_secret"), String.class);
        TypeUtils.requireType(this.data.get("redirect_uri"), String.class);
        TypeUtils.requireType(this.data.get("refresh_token"), String.class);
    }

    public void addAuthorizationHeader(HttpRequest.Builder request) throws IOException, InterruptedException, MissingTokenException, UnexpectedTypeException {
        if (System.currentTimeMillis() / 1000 > this.token_expires_at) {
            refreshAccessToken();
        }
        request.header(
            "Authorization",
            String.format("Bearer %s", this.access_token)
        );
    }

    private void refreshAccessToken() throws IOException, InterruptedException, MissingTokenException, UnexpectedTypeException {
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
        JSONObject parsed = (JSONObject)JSONValue.parse(response.body());

        String accessToken = TypeUtils.requireType(parsed.get("access_token"), String.class);
        String refreshToken = TypeUtils.requireType(parsed.get("refresh_token"), String.class);
        int expires_in = TypeUtils.requireType(parsed.get("expires_in"), Integer.class);

        if (accessToken == null || refreshToken == null) {
            throw new MissingTokenException();
        }

        this.data.put("refresh_token", refreshToken);
        FileWriter writer = new FileWriter(this.file);
        JSONObject.writeJSONString(this.data, writer);
        writer.close();

        this.access_token = accessToken;
        this.token_expires_at = System.currentTimeMillis() / 1000 + expires_in;
    }
}