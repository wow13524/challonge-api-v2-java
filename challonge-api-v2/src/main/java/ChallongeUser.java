package main.java;

import org.json.simple.JSONObject;

import main.java.Exceptions.ChallongeException;

public final class ChallongeUser extends ChallongeObject {
    public final String imageUrl, email, username;

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

    @Override
    public String toString() {
        return String.format(
            "ChallongeUser[id=%s, imageUrl=%s, email=%s, username=%s]",
            this.id,
            this.imageUrl,
            this.email,
            this.username
        );
    }
}