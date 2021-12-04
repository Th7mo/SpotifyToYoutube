package nl.th7mo.spotify;

import nl.th7mo.spotify.token.SpotifyToken;
import nl.th7mo.spotify.token.SpotifyTokenDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class SpotifyTokenDAOTests {

    private SpotifyTokenDAO spotifyTokenDAO;
    private SpotifyToken spotifyToken;

    @BeforeEach
    public void setUp() {
        spotifyTokenDAO = new SpotifyTokenDAO();
    }

    @Test
    public void shouldSayAccessTokenIsNotNullWhenGettingToken()
            throws IOException {
        spotifyToken = spotifyTokenDAO.getToken();
        String access_token = spotifyToken.accessToken;

        assertNotNull(access_token);
    }

    @Test
    public void shouldSayTokenTypeIsNotNullWhenGettingToken()
            throws IOException {
        spotifyToken = spotifyTokenDAO.getToken();
        String token_type = spotifyToken.tokenType;

        assertNotNull(token_type);
    }

    @Test
    public void shouldSayExpiresInIs3600WhenGettingToken()
            throws IOException {
        int expected_expires_in = 3600;
        spotifyToken = spotifyTokenDAO.getToken();
        int expires_in = spotifyToken.expiresIn;

        assertEquals(expires_in, expected_expires_in);
    }

    @Test
    public void shouldSayAccessTokenLengthIsEightyThreeWhenGettingToken()
            throws IOException {
        int expectedTokenLength = 83;
        spotifyToken = spotifyTokenDAO.getToken();
        String access_token = spotifyToken.accessToken;
        int tokenLength = access_token.length();

        assertEquals(tokenLength, expectedTokenLength);
    }

    @Test
    public void shouldSayTokenTypeIsBearerWhenGettingToken()
            throws IOException {
        String expectedTokenType = "Bearer";
        spotifyToken = spotifyTokenDAO.getToken();
        String token_type = spotifyToken.tokenType;

        assertEquals(token_type, expectedTokenType);
    }
}
