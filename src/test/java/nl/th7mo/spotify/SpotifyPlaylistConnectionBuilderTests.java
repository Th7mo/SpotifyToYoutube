package nl.th7mo.spotify;

import nl.th7mo.connection.SpotifyPlaylistConnectionBuilder;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class SpotifyPlaylistConnectionBuilderTests {

    private SpotifyToken token;
    private final SpotifyTokenDAO tokenDAO = new SpotifyTokenDAO();
    private HttpURLConnection connection;

    @BeforeEach
    public void setUp() throws IOException {
        token = tokenDAO.getToken();
        setUpExpectedConnection();
    }

    private void setUpExpectedConnection() throws IOException {
        String path = "https://api.spotify.com/v1/playlists/37i9dQZEVXbMDoHDwVN2tF/tracks?offset=0";
        URL url = new URL(path);
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setDoOutput(true);
        connection.setRequestProperty("Authorization", "Bearer " + token.getAccess_token());
        connection.setRequestProperty("Content-Type", "application/json");
    }

    @Test
    public void shouldMatchManuallyBuiltConnection() throws IOException {
        SpotifyPlaylistConnectionBuilder builder = new SpotifyPlaylistConnectionBuilder();
        builder.setPlaylistURL("37i9dQZEVXbMDoHDwVN2tF", 0);
        builder.openConnection();
        builder.setRequestMethod("GET");
        builder.setOutput(true);
        builder.setRequestProperty("Authorization", "Bearer " + token.getAccess_token());
        builder.setRequestProperty("Content-Type", "application/json");

        assertEquals(connection.toString(), builder.getResult().toString());
    }
}
