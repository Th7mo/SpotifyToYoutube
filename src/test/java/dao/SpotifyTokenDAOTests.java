package dao;

import model.SpotifyAuthorizationOptions;
import model.SpotifyToken;
import exception.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class SpotifyTokenDAOTests {

	private SpotifyTokenDAO spotifyTokenDAO;
	private SpotifyToken spotifyToken;

	@BeforeEach
	public void setUp() {
		resetAuthenticationOptions();
		spotifyTokenDAO = new SpotifyTokenDAO();
	}

	private void resetAuthenticationOptions() {
		SpotifyAuthorizationOptions.CLIENT_ID = "4299b9c3763b4311b4cffa528525e61c";
		SpotifyAuthorizationOptions.CLIENT_SECRET = "63842318a1944c2eb815a38a6e978730";
		SpotifyAuthorizationOptions.TOKEN_URL = "https://accounts.spotify.com/api/token";
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
		SpotifyAuthorizationOptions.CLIENT_ID = "invalidClientId";

		assertThrows(InvalidCredentialsForTokenException.class, () -> {
			spotifyToken = spotifyTokenDAO.getToken();
		});
	}

	@Test()
	public void shouldThrowExceptionWhenClientSecretIsInvalid() {
		SpotifyAuthorizationOptions.CLIENT_SECRET = "invalidClientSecret";

		assertThrows(InvalidCredentialsForTokenException.class, () -> {
			spotifyToken = spotifyTokenDAO.getToken();
		});
	}

	@Test
	public void shouldThrowInvalidRequestTokenPathException() {
		SpotifyAuthorizationOptions.TOKEN_URL = "https://google.com";

		assertThrows(InvalidRequestTokenPathException.class, () -> {
			spotifyToken = spotifyTokenDAO.getToken();
		});
	}
}
