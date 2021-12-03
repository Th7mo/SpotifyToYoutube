package nl.th7mo.spotify;

import nl.th7mo.spotify.SpotifyPlaylist;
import nl.th7mo.spotify.SpotifyPlaylistTitleDAO;
import nl.th7mo.spotify.SpotifyToken;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import nl.th7mo.spotify.SpotifyTokenDAO;

import java.io.IOException;

public class SpotifyPlaylistTitleDAOTests {

    private final SpotifyPlaylistTitleDAO titleDAO = new SpotifyPlaylistTitleDAO();
    private final SpotifyTokenDAO tokenDAO = new SpotifyTokenDAO();
    private SpotifyToken token;

    @BeforeEach
    public void setUp() throws IOException {
        token = tokenDAO.getToken();
    }

    @Test
    public void shouldSayTitleOfPlaylistIsTopFiftyGlobal() throws IOException {
        String expectedTitle = "Top 50 - Global";
        String playlistId = "37i9dQZEVXbMDoHDwVN2tF";
        String tokenString = token.getAccess_token();
        SpotifyPlaylist emptyPlaylist = new SpotifyPlaylist();
        SpotifyPlaylist playlist = titleDAO.getPlaylistWithTitle(emptyPlaylist, tokenString, playlistId);
        String title = playlist.getName();

        assertEquals(expectedTitle, title);
    }
}