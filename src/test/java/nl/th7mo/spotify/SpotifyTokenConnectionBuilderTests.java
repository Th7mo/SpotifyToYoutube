package nl.th7mo.spotify;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import nl.th7mo.connection.SpotifyTokenConnectionBuilder;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class SpotifyTokenConnectionBuilderTests {

    private HttpURLConnection connection;

    @BeforeEach
    public void setUp() throws IOException {
        setUpExpectedConnection();
    }

    private void setUpExpectedConnection() throws IOException {
        URL url = new URL(SpotifyCredentials.TOKEN_URL);
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type",
                "application/x-www-form-urlencoded");
    }

    @Test
    public void shouldMatchManuallyBuiltConnection() throws IOException {
        SpotifyTokenConnectionBuilder builder = new SpotifyTokenConnectionBuilder();
        builder.openConnection();
        builder.setRequestMethod("POST");
        builder.setOutput(true);
        builder.setRequestProperty("Content-Type",
                "application/x-www-form-urlencoded");

        assertEquals(connection.toString(), builder.getResult().toString());
    }
}
