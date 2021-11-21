package controller;

import dao.SpotifyPlaylistDAO;
import dao.SpotifyPlaylistTitleDAO;
import dao.SpotifyTokenDAO;
import model.SpotifyPlaylist;
import model.SpotifyToken;
import view.SpotifyPlaylistIdView;

import java.io.IOException;

public class SpotifyController {

    private SpotifyPlaylist spotifyPlaylist;
    private final SpotifyPlaylistDAO playlistDAO = new SpotifyPlaylistDAO();
    private final SpotifyTokenDAO tokenDAO = new SpotifyTokenDAO();
    private final SpotifyPlaylistTitleDAO titleDAO = new SpotifyPlaylistTitleDAO();
    private SpotifyToken token;
    private String playlistId;

    public void setSpotifyPlaylist() throws IOException {
        token = tokenDAO.getToken();
        String tokenString = token.getAccess_token();
        getPlaylistId();
        spotifyPlaylist = playlistDAO.getPlaylist(tokenString, playlistId);
        addTitleToPlaylist();
    }

    private void addTitleToPlaylist() throws IOException {
        String tokenString = token.getAccess_token();
        spotifyPlaylist = titleDAO.getPlaylistWithTitle(spotifyPlaylist,
                tokenString, playlistId);
    }

    private void getPlaylistId() {
        playlistId = SpotifyPlaylistIdView.getUserInput();
    }

    public SpotifyPlaylist getSpotifyPlaylist() {
        return spotifyPlaylist;
    }
}
