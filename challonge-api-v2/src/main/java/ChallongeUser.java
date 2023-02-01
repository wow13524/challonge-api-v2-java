package main.java;

import org.json.simple.JSONObject;

import main.java.Exceptions.ChallongeException;

public class ChallongeUser extends ChallongeObject {
    public final String imageUrl, email, username;

    ChallongeUser(JSONObject json) throws ChallongeException {
        super(
            TypeUtils.requireType(
                json.get("id"),
                String.class,
                "id"
            ),
            TypeUtils.requireType(
                json.get("type"),
                String.class,
                "type"
            )
        );
        JSONObject attributes = TypeUtils.requireType(
            json.get("attributes"),
            JSONObject.class,
            "attributes"
        );
        this.imageUrl = TypeUtils.requireType(
            attributes.get("imageUrl"),
            String.class,
            "imageUrl"
        );
        this.email = TypeUtils.requireOptionalType(
            attributes.get("email"),
            String.class,
            "email"
        );
        this.username = TypeUtils.requireType(
            attributes.get("username"),
            String.class,
            "username"
        );
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
