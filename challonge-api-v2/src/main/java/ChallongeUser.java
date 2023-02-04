package main.java;

import org.json.simple.JSONObject;

import main.java.Exceptions.ChallongeException;

public final class ChallongeUser extends ChallongeObject {
    private final String imageUrl, email, username;

    ChallongeUser(JSONObject json) throws ChallongeException {
        super(
            TypeUtils.requireType(json, "id", String.class),
            TypeUtils.requireType(json, "type", String.class)
        );

        JSONObject attributes =
        TypeUtils.requireType(json, "attributes", JSONObject.class);

        this.imageUrl =
        TypeUtils.requireType(attributes, "imageUrl", String.class);
        this.email = 
        TypeUtils.requireOptionalType(attributes, "email", String.class);
        this.username = 
        TypeUtils.requireType(attributes, "username", String.class);
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public String getEmail() {
        return this.email;
    }

    public String getUsername() {
        return this.username;
    }

    @Override
    public String toString() {
        return String.format(
            "ChallongeUser[id=%s, username=%s]",
            this.getId(),
            this.getUsername()
        );
    }
}