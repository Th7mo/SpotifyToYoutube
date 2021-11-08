package DAO;

import Model.SpotifyToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

public class SpotifyTokenDAOTests {

	private SpotifyToken spotifyToken;

	@BeforeEach
	public void setUp() throws IOException {
		SpotifyTokenDAO spotifyTokenDAO = new SpotifyTokenDAO();
		spotifyToken = spotifyTokenDAO.getToken();
	}

	@Test
	public void Should_SayAccessTokenIsNotNullWhenGettingToken() {
		String access_token = spotifyToken.getAccess_token();

		assertNotNull(access_token);
	}

	@Test
	public void Should_SayTokenTypeIsNotNullWhenGettingToken() {
		String token_type = spotifyToken.getToken_type();

		assertNotNull(token_type);
	}

	@Test
	public void Should_SayExpiresInIs3600WhenGettingToken() {
		int expected_expires_in = 3600;
		int expires_in = spotifyToken.getExpires_in();

		assertEquals(expires_in, expected_expires_in);
	}

	@Test
	public void Should_SayAccessTokenLengthIsEightyThreeWhenGettingToken() {
		int expectedTokenLength = 83;
		String access_token = spotifyToken.getAccess_token();
		int tokenLength = access_token.length();

		assertEquals(tokenLength, expectedTokenLength);
	}

	@Test
	public void Should_SayTokenTypeIsBearerWhenGettingToken() {
		String expectedTokenType = "Bearer";
		String token_type = spotifyToken.getToken_type();

		assertEquals(token_type, expectedTokenType);
	}

	@Test
	public void Should_NotCrashWhenCredentialsAreInvalid() {

	}
}
