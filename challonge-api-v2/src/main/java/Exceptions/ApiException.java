package main.java.Exceptions;

import org.json.simple.JSONObject;

public class ApiException extends ChallongeException {
    public ApiException(JSONObject error) {
        super(String.format(
            "API responded with status code %s: %s",
            error.get("status"),
            error.get("detail")
        ));
    }
}