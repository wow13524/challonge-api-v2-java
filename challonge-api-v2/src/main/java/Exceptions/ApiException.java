package main.java.Exceptions;

import java.util.Map.Entry;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class ApiException extends ChallongeException {
    @SuppressWarnings("unchecked")
    private static String detailToString(Object detail) {
        String reason = "unknown";

        if (detail instanceof JSONObject) {
            reason = "";
            for (Object e : ((JSONObject)detail).entrySet()) {
                Entry<String, Object> entry = (Entry<String, Object>)e;
                reason += String.format(
                    "\n\t%s -> %s",
                    entry.getKey(),
                    detailToString(entry.getValue())
                );
            }
        }
        else if (detail instanceof JSONArray) {
            reason = String.join(", ", (JSONArray)detail);
        }
        else if (detail instanceof String) {
            reason = (String)detail;
        }
        return reason;
    }

    private static String errorToString(JSONObject error) {
        int status = (int)(long)(Long)error.get("status");
        Object detail = error.get("detail");
        Object source = error.get("source");
        String reason = detailToString(detail);
        
        if (source instanceof JSONObject) {
            Object pointer = ((JSONObject)source).get("pointer");
            reason = String.format(
                "(%s) %s",
                pointer,
                reason
            );
        }

        return String.format(
            "API responded with status code %d: %s",
            status,
            reason
        );
    }

    private static String errorsToString(JSONArray errors) {
        String[] strings = new String[errors.size()];

        for (int i = 0; i < errors.size(); i++) {
            strings[i] = errorToString((JSONObject)errors.get(i));
        }
        
        return String.join("\n", strings);
    }

    public ApiException(JSONObject error) {
        super(errorToString(error));
    }

    public ApiException(JSONArray errors) {
        super(errorsToString(errors));
    }
}