package dao;

import model.SpotifyPlaylist;
import model.Token;
import model.Track;
import exception.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class SpotifyPlaylistDAOTests {

	private final String playlistId = "37i9dQZEVXbMDoHDwVN2tF";

	private SpotifyPlaylistDAO spotifyPlaylistDAO;
	private SpotifyPlaylist spotifyPlaylist;
	private String accessToken;

	@BeforeEach
	public void setUp() throws IOException {
		spotifyPlaylistDAO = new SpotifyPlaylistDAO();
		SpotifyTokenDAO spotifyTokenDAO = new SpotifyTokenDAO();
		Token spotifyToken = spotifyTokenDAO.getToken();
		accessToken = spotifyToken.getAccess_token();
	}

	@Test
	public void shouldSayPlaylistSizeIsEqualTo50() throws IOException {
		int expectedPlaylistSize = 50;
		spotifyPlaylist = spotifyPlaylistDAO.getPlaylist(accessToken, playlistId);
		int playlistSize = spotifyPlaylist.size();

		assertEquals(expectedPlaylistSize, playlistSize);
	}

	@Test
	public void shouldSayFirstSongNameIsNotEmpty() throws IOException {
		spotifyPlaylist = spotifyPlaylistDAO.getPlaylist(accessToken, playlistId);
		Track firstTrack = spotifyPlaylist.getItems().get(0).getTrack();
		int songNameLength = firstTrack.getName().length();

		assertTrue(songNameLength > 0);
	}

	@Test
	public void shouldSayFirstSongArtistIsNotEmpty() throws IOException {
		spotifyPlaylist = spotifyPlaylistDAO.getPlaylist(accessToken, playlistId);
		Track firstTrack = spotifyPlaylist.getItems().get(0).getTrack();
		int artistNameLength = firstTrack.getArtist(0).getName().length();

		assertTrue(artistNameLength > 0);
	}

	@Test
	public void shouldRaiseExceptionWhenAccessTokenIsInvalid() {
		String wrongAccessToken = "wrongAccessToken";

		assertThrows(InvalidAccessTokenException.class, () -> {
			spotifyPlaylist = spotifyPlaylistDAO.getPlaylist(wrongAccessToken, playlistId);
		});
	}

	@Test
	public void shouldRaiseExceptionWhenPlaylistIdIsInvalid() {
		String wrongPlaylistId = "wrongPlaylistId";

		assertThrows(InvalidPlaylistIdException.class, () -> {
			spotifyPlaylist = spotifyPlaylistDAO.getPlaylist(accessToken, wrongPlaylistId);
		});
	}
}
