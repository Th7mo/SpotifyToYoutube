package dao;

import nl.th7mo.connection.InvalidCredentialsForTokenException;
import nl.th7mo.connection.InvalidRequestTokenPathException;
import nl.th7mo.spotify.SpotifyCredentials;
import nl.th7mo.spotify.SpotifyToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import nl.th7mo.spotify.SpotifyTokenDAO;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class SpotifyTokenDAOTests {

    private SpotifyTokenDAO spotifyTokenDAO;
    private SpotifyToken spotifyToken;
    private SpotifyCredentials credentials;

    @BeforeEach
    public void setUp() throws FileNotFoundException {
        resetAuthenticationOptions();
        spotifyTokenDAO = new SpotifyTokenDAO();
        credentials = new SpotifyCredentials();
    }

    private void resetAuthenticationOptions() {
        credentials.setClientId("4299b9c3763b4311b4cffa528525e61c");
        credentials.setClientSecret("63842318a1944c2eb815a38a6e978730");
        credentials.setTokenUrl("https://accounts.spotify.com/api/token");
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

    @Test()
    public void shouldThrowExceptionWhenClientIdIsInvalid() {
        credentials.setClientId("invalidClientId");

        assertThrows(InvalidCredentialsForTokenException.class, () -> {
            spotifyToken = spotifyTokenDAO.getToken();
        });
    }

    @Test()
    public void shouldThrowExceptionWhenClientSecretIsInvalid() {
        credentials.setClientSecret("invalidClientSecret");

        assertThrows(InvalidCredentialsForTokenException.class, () -> {
            spotifyToken = spotifyTokenDAO.getToken();
        });
    }

    @Test
    public void shouldThrowInvalidRequestTokenPathException() {
        credentials.setTokenUrl("https://google.com");

        assertThrows(InvalidRequestTokenPathException.class, () -> {
            spotifyToken = spotifyTokenDAO.getToken();
        });
    }
}
