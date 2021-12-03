package nl.th7mo.spotify;

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
        String access_token = spotifyToken.getAccess_token();

        assertNotNull(access_token);
    }

    @Test
    public void shouldSayTokenTypeIsNotNullWhenGettingToken()
            throws IOException {
        spotifyToken = spotifyTokenDAO.getToken();
        String token_type = spotifyToken.getToken_type();

        assertNotNull(token_type);
    }

    @Test
    public void shouldSayExpiresInIs3600WhenGettingToken()
            throws IOException {
        int expected_expires_in = 3600;
        spotifyToken = spotifyTokenDAO.getToken();
        int expires_in = spotifyToken.getExpires_in();

        assertEquals(expires_in, expected_expires_in);
    }

    @Test
    public void shouldSayAccessTokenLengthIsEightyThreeWhenGettingToken()
            throws IOException {
        int expectedTokenLength = 83;
        spotifyToken = spotifyTokenDAO.getToken();
        String access_token = spotifyToken.getAccess_token();
        int tokenLength = access_token.length();

        assertEquals(tokenLength, expectedTokenLength);
    }

    @Test
    public void shouldSayTokenTypeIsBearerWhenGettingToken()
            throws IOException {
        String expectedTokenType = "Bearer";
        spotifyToken = spotifyTokenDAO.getToken();
        String token_type = spotifyToken.getToken_type();

        assertEquals(token_type, expectedTokenType);
    }
}
