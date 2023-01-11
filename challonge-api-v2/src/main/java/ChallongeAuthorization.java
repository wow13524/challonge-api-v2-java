package main.java;

public final class ChallongeAuthorization {
    private String clientId, clientSecret, refreshToken;

    public ChallongeAuthorization(String clientId, String clientSecret, String refreshToken) {
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