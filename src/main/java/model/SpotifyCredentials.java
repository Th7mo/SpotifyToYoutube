package model;

import com.google.gson.Gson;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

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

    public void setCredentials() {
        String fileName = "spotify_credentials.json";
        InputStream inputStream = getFileAsInputStream(fileName);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader reader = new BufferedReader(inputStreamReader);
        String json = reader.lines().collect(Collectors.joining("\n"));
        SpotifyCredentials credentials = new Gson().fromJson(json, SpotifyCredentials.class);
        clientId = credentials.getClientId();
        clientSecret = credentials.getClientSecret();
        tokenUrl = credentials.getTokenUrl();
    }

    private InputStream getFileAsInputStream(final String fileName) {
        return this.getClass().getClassLoader().getResourceAsStream(fileName);
    }
}
