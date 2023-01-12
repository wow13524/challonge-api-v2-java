package main.java;

public final class ChallongeAuthorization {
    private String clientId, clientSecret, refreshToken;

    public ChallongeAuthorization(String clientId, String clientSecret, String refreshToken) {
        assert clientId != null : "clientId is null";
        assert clientSecret != null : "clientSecret is null";
        assert refreshToken != null : "refreshToken is null";
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.refreshToken = refreshToken;
    }

    public String getClientId() {
        return this.clientId;
    }
    
    public String getClientSecret() {
        return this.clientSecret;
    }
    
    public String getRefreshToken() {
        return this.refreshToken;
    }
}