package nl.th7mo.spotify;

import java.io.IOException;

public class SpotifyController {

    private SpotifyPlaylist spotifyPlaylist;
    private final SpotifyPlaylistDAO playlistDAO = new SpotifyPlaylistDAO();
    private final SpotifyTokenDAO tokenDAO = new SpotifyTokenDAO();
    private final SpotifyPlaylistTitleDAO titleDAO = new SpotifyPlaylistTitleDAO();
    private SpotifyToken token;
    private String playlistId;

    public void setSpotifyPlaylist(String playlistId) throws IOException {
        this.playlistId = playlistId;
        token = tokenDAO.getToken();
        String tokenString = token.getAccess_token();
        spotifyPlaylist = playlistDAO.getPlaylist(tokenString, playlistId);
        addTitleToPlaylist();
    }

    private void addTitleToPlaylist() throws IOException {
        String tokenString = token.getAccess_token();
        spotifyPlaylist = titleDAO.getPlaylistWithTitle(spotifyPlaylist,
                tokenString, playlistId);
    }

    public SpotifyPlaylist getSpotifyPlaylist() {
        return spotifyPlaylist;
    }
}
