package model;

import util.JsonService;

import java.io.FileNotFoundException;

public class SpotifyCredentials {

    private String clientId;
    private String clientSecret;
    private String tokenUrl;

    public SpotifyCredentials() throws FileNotFoundException {
        setCredentials();
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getTokenUrl() {
        return tokenUrl;
    }

    public void setTokenUrl(String tokenUrl) {
        this.tokenUrl = tokenUrl;
    }

    private void setCredentials() throws FileNotFoundException {
        String json = JsonService.getJsonFromResource("spotify_credentials.json");
        clientId = JsonService.getValueOfKey("clientId", json);
        clientSecret = JsonService.getValueOfKey("clientSecret", json);
        tokenUrl = JsonService.getValueOfKey("tokenUrl", json);
    }
}
