package nl.th7mo.spotify;

import com.google.gson.Gson;
import nl.th7mo.connection.BadRequestException;
import nl.th7mo.connection.HttpURLConnectionDirector;
import nl.th7mo.connection.SpotifyTokenConnectionBuilder;

import java.io.*;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class SpotifyTokenDAO implements TokenDAO {

    private HttpURLConnection connection;

    @Override
    public SpotifyToken getToken() throws BadRequestException, IOException {
        initializeConnection();
        sendRequest();
        SpotifyTokenDAOExceptionHandler.handleStatusCodes(connection);
        String responseJson = getResponse();

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

    private String getResponse() throws IOException {
        BufferedReader reader = getBufferedReader();

        return buildResponseString(reader);
    }

    private BufferedReader getBufferedReader() throws IOException {
        InputStream inputStream = connection.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

        return new BufferedReader(inputStreamReader);
    }

    private String buildResponseString(BufferedReader reader) {
        return reader.lines().collect(Collectors.joining());
    }

    private SpotifyToken getBuildSpotifyToken(String json) {
        return new Gson().fromJson(json, SpotifyToken.class);
    }
}
