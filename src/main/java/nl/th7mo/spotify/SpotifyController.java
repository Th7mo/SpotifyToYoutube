package nl.th7mo.spotify;

import nl.th7mo.spotify.playlist.SpotifyPlaylist;
import nl.th7mo.spotify.playlist.SpotifyPlaylistDAO;
import nl.th7mo.spotify.playlist.SpotifyPlaylistTitleDAO;
import nl.th7mo.spotify.token.SpotifyToken;
import nl.th7mo.spotify.token.SpotifyTokenDAO;

import java.io.IOException;

public class SpotifyController {

    private SpotifyPlaylist spotifyPlaylist;
    private final SpotifyPlaylistTitleDAO titleDAO = new SpotifyPlaylistTitleDAO();
    private SpotifyToken token;
    private String playlistId;

    public void setSpotifyPlaylist(String playlistId) throws IOException {
        this.playlistId = playlistId;
        token = new SpotifyTokenDAO().getToken();
        String tokenString = token.getAccess_token();
        spotifyPlaylist = new SpotifyPlaylistDAO().getPlaylist(tokenString, playlistId);
        addTitleToPlaylist();
    }

    private void addTitleToPlaylist() throws IOException {
        String tokenString = token.getAccess_token();
        spotifyPlaylist = titleDAO.getPlaylistWithTitle(
                spotifyPlaylist,
                tokenString,
                playlistId
        );
    }

    public SpotifyPlaylist getSpotifyPlaylist() {
        return spotifyPlaylist;
    }
}
