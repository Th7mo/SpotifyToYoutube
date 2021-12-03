package nl.th7mo.spotify.token;

import com.google.gson.Gson;
import nl.th7mo.connection.BadRequestException;
import nl.th7mo.connection.HttpURLConnectionDirector;
import nl.th7mo.connection.ResponseBuilder;
import nl.th7mo.connection.SpotifyTokenConnectionBuilder;

import java.io.*;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;

public class SpotifyTokenDAO {

    private HttpURLConnection connection;

    public SpotifyToken getToken() throws BadRequestException, IOException {
        initializeConnection();
        sendRequest();
        SpotifyTokenDAOExceptionHandler.handleStatusCodes(connection);
        String responseJson = ResponseBuilder.getResponse(connection);

        return getBuildSpotifyToken(responseJson);
    }

    private void initializeConnection() throws IOException {
        SpotifyTokenConnectionBuilder builder = new SpotifyTokenConnectionBuilder();
        HttpURLConnectionDirector director = new HttpURLConnectionDirector(builder);
        director.makeSpotifyTokenConnection();
        connection = builder.getResult();
    }

    private void sendRequest() throws IOException {
        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(getRequestData());
    }

    private byte[] getRequestData() {
        String request = "grant_type=client_credentials&client_id=" +
                SpotifyCredentials.CLIENT_ID +
                "&client_secret=" +
                SpotifyCredentials.CLIENT_SECRET;

        return request.getBytes(StandardCharsets.UTF_8);
    }

    private SpotifyToken getBuildSpotifyToken(String json) {
        return new Gson().fromJson(json, SpotifyToken.class);
    }
}
