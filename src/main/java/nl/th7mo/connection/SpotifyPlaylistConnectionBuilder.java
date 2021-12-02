package nl.th7mo.connection;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class SpotifyPlaylistConnectionBuilder implements HttpURLConnectionBuilder {

    private HttpURLConnection connection;
    private String playlistURL;
    private int offset = 0;

    public void setPlaylistURL(String playlistId, int offset) {
        this.offset = offset;
        String playlistBaseURL = "https://api.spotify.com/v1/playlists/";
        playlistURL = playlistBaseURL + playlistId + "/tracks?offset=";
    }

    @Override
    public void openConnection() throws IOException {
        URL url = new URL(playlistURL + offset);
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
