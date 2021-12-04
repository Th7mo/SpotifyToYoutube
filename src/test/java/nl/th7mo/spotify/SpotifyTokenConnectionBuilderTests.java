package nl.th7mo.spotify;

import static org.junit.jupiter.api.Assertions.*;

import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import nl.th7mo.connection.SpotifyTokenConnectionBuilder;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

public class SpotifyTokenConnectionBuilderTests {

    private HttpURLConnection connection;

    @BeforeEach
    public void setUp() throws IOException {
        setUpExpectedConnection();
    }

    private void setUpExpectedConnection() throws IOException {
        Dotenv dotenv = Dotenv.load();
        URL url = new URL(Objects.requireNonNull(dotenv.get("SPOTIFY_TOKEN_URL")));
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
