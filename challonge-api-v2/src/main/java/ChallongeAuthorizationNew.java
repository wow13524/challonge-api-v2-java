package main.java;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import main.java.Exceptions.UnexpectedTypeException;

final class ChallongeAuthorizationNew extends AbstractMap<String, String> {

    private File file;
    private String client_id, client_secret, redirect_uri, refresh_token;

    public ChallongeAuthorizationNew(File authFile) throws IOException, ParseException, UnexpectedTypeException {
        this.file = authFile;

        JSONObject data = (JSONObject)(new JSONParser()).parse(new FileReader(this.file));

        this.client_id = TypeUtils.requireType(data.get("client_id"), String.class, "client_id");
        this.client_secret = TypeUtils.requireType(data.get("client_secret"), String.class, "client_secret");
        this.redirect_uri = TypeUtils.requireType(data.get("redirect_uri"), String.class, "redirect_uri");
        this.refresh_token = TypeUtils.requireType(data.get("refresh_token"), String.class, "refresh_token");
    }

    public Set<Map.Entry<String, String>> entrySet() {
        return new HashSet<Map.Entry<String, String>>(Arrays.asList(
            new SimpleEntry<String, String>("client_id", this.client_id),
            new SimpleEntry<String, String>("client_secret", this.client_secret),
            new SimpleEntry<String, String>("redirect_uri", this.redirect_uri),
            new SimpleEntry<String, String>("refresh_token", this.refresh_token)
        ));
    }

    public void updateRefreshToken(String refresh_token) throws IOException {
        Objects.requireNonNull(refresh_token, "refresh_token is null");
        this.refresh_token = refresh_token;

        JSONObject.writeJSONString(this, new FileWriter(this.file));
    }
}