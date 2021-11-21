package model;

import com.google.gson.Gson;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SpotifyCredentials {

    private String clientId;
    private String clientSecret;
    private String tokenUrl;

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

    public void setCredentials() throws IOException {
        String pathToJson = "src/main/resources/spotify_credentials.json";
        String json = new String(Files.readAllBytes(Paths.get(pathToJson)));
        SpotifyCredentials credentials = new Gson().fromJson(json, SpotifyCredentials.class);
        clientId = credentials.getClientId();
        clientSecret = credentials.getClientSecret();
        tokenUrl = credentials.getTokenUrl();
    }
}
