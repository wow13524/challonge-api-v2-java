package main.java;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

class EncodeUtils {
    private static String percentEncodeString(String raw) {
        String output = "";
        for (char c : raw.toCharArray()) {
            if (c == ' ') {
                output += "+";
            }
            else if ((c >= 32 && c != 34 && c != 45 && c != 46 && c <= 47)
                || (c >= 58 && c != 60 && c != 62 && c <= 64)
                || c == 91 || c == 93) {
                output += "%" + Integer.toHexString(c).toUpperCase();
            }
            else {
                output += c;
            }
        }
        return output;
    }

    public static String encodeFormBody(HashMap<String, String> body) {
        ArrayList<String> keyValuePairs = new ArrayList<String>(body.size());
        for (Entry<String, String> kv : body.entrySet()) {
            keyValuePairs.add(
                percentEncodeString(kv.getKey())
                + "="
                + percentEncodeString(kv.getValue())
            );
        }
        return String.join("&", keyValuePairs);
    }
}