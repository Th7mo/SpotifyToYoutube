package nl.th7mo.connection;

import nl.th7mo.spotify.SpotifyCredentials;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class SpotifyTokenConnectionBuilder implements HttpURLConnectionBuilder {

    private HttpURLConnection connection;

    @Override
    public void openConnection() throws IOException {
        URL url = new URL(SpotifyCredentials.TOKEN_URL);
        connection = (HttpURLConnection) url.openConnection();
    }

    @Override
    public void setRequestMethod(String requestMethod) throws IOException {
        connection.setRequestMethod(requestMethod);
    }

    @Override
    public void setOutput(boolean isOutputConnection) {
        connection.setDoOutput(isOutputConnection);
    }

    @Override
    public void setRequestProperty(String key, String value) {
        connection.setRequestProperty(key, value);
    }

    public HttpURLConnection getResult() {
        return connection;
    }
}
