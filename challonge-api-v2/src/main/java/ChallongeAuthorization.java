package main.java;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.Map;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import main.java.Exceptions.UnexpectedTypeException;

final class ChallongeAuthorization {
    private final File file;
    private final Map.Entry<String, String> refresh_token;
    private final Map<String, String> fileSaveBody;
    public final Map<String, String> refreshRequestBody;

    public ChallongeAuthorization(File authFile) throws IOException, ParseException, UnexpectedTypeException {
        this.file = authFile;

        JSONObject data = (JSONObject)(new JSONParser()).parse(new FileReader(this.file));

        Map.Entry<String, String> client_id =
        new AbstractMap.SimpleImmutableEntry<String, String>(
            "client_id",
            TypeUtils.requireType(
                data.get("client_id"),
                String.class,
                "client_id"
            )
        );
        Map.Entry<String, String> client_secret =
        new AbstractMap.SimpleImmutableEntry<String, String>(
            "client_secret",
            TypeUtils.requireType(
                data.get("client_secret"),
                String.class,
                "client_secret"
            )
        );
        Map.Entry<String, String> redirect_uri =
        new AbstractMap.SimpleImmutableEntry<String, String>(
            "redirect_uri",
            TypeUtils.requireType(
                data.get("redirect_uri"),
                String.class,
                "redirect_uri"
            )
        );
        this.refresh_token =
        new AbstractMap.SimpleEntry<String, String>(
            "refresh_token",
            TypeUtils.requireType(
                data.get("refresh_token"),
                String.class,
                "refresh_token"
            )
        );

        this.fileSaveBody = new ImmutableMap<String, String>(
            client_id,
            client_secret,
            redirect_uri,
            this.refresh_token
        );
        this.refreshRequestBody = new ImmutableMap<String, String>(
            client_id,
            redirect_uri,
            this.refresh_token,
            new AbstractMap.SimpleImmutableEntry<String, String>(
            "grant_type",
                "refresh_token"
            )
        );
    }

    public void updateRefreshToken(String refresh_token) throws IOException, UnexpectedTypeException {
        TypeUtils.requireType(
            refresh_token,
            String.class,
            "refresh_token"
        );
        this.refresh_token.setValue(refresh_token);

        FileWriter writer = new FileWriter(this.file);
        JSONObject.writeJSONString(
            this.fileSaveBody,
            writer
        );
        writer.close();
    }
}