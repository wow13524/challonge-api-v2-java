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

import main.java.Exceptions.AuthIOException;
import main.java.Exceptions.ChallongeException;

final class ChallongeAuthorization {
    private final File file;
    private final Map.Entry<String, String> refresh_token;
    private final Map<String, String> fileSaveBody;
    public final Map<String, String> refreshRequestBody;

    public ChallongeAuthorization(File authFile) throws ChallongeException {
        this.file = authFile;

        JSONObject data;
        try {
            FileReader reader = new FileReader(this.file);
            data = (JSONObject)(new JSONParser()).parse(reader);
            reader.close();
        }
        catch (IOException | ParseException e) {
            throw new AuthIOException(e);
        }

        Map.Entry<String, String> client_id =
        new AbstractMap.SimpleImmutableEntry<String, String>(
            "client_id",
            TypeUtils.requireType(data, "client_id",String.class)
        );

        Map.Entry<String, String> client_secret =
        new AbstractMap.SimpleImmutableEntry<String, String>(
            "client_secret",
            TypeUtils.requireType(data, "client_secret", String.class)
        );

        Map.Entry<String, String> redirect_uri =
        new AbstractMap.SimpleImmutableEntry<String, String>(
            "redirect_uri",
            TypeUtils.requireType(data, "redirect_uri", String.class)
        );

        this.refresh_token =
        new AbstractMap.SimpleEntry<String, String>(
            "refresh_token",
            TypeUtils.requireType(data, "refresh_token", String.class)
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

    public void updateRefreshToken(String refresh_token) throws ChallongeException {
        TypeUtils.requireType(
            refresh_token,
            String.class,
            "refresh_token"
        );
        this.refresh_token.setValue(refresh_token);

        try {
            FileWriter writer = new FileWriter(this.file);
            JSONObject.writeJSONString(
                this.fileSaveBody,
                writer
            );
            writer.close();
        }
        catch (IOException e) {
            throw new AuthIOException(e);
        }
    }
}