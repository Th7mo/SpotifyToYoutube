package nl.th7mo.connection;

import io.github.cdimascio.dotenv.Dotenv;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

public class SpotifyTokenConnectionBuilder implements HttpURLConnectionBuilder {

    private HttpURLConnection connection;

    @Override
    public void openConnection() throws IOException {
        Dotenv dotenv = Dotenv.load();
        URL url = new URL(Objects.requireNonNull(dotenv.get("SPOTIFY_TOKEN_URL")));
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
